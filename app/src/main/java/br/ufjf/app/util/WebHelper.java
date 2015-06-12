package br.ufjf.app.util;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParserFactory;

import br.ufjf.app.model.ServerDB;
import br.ufjf.app.model.Student;
import br.ufjf.app.model.survey.Survey;

public class WebHelper {
    private static final String BASE_URL = "http://200.131.219.208:4712";
    private static final String STUDENTS_URL = BASE_URL + "/students";
    private static final String SURVEYS_URL = BASE_URL + "/surveys";

    public static FeedHandler readFeed(String url) throws IOException, SAXException, ParserConfigurationException {
        InputStream inputXml = null;
        try {
            inputXml = new URL(url).openConnection().getInputStream();

            Reader reader = new InputStreamReader(inputXml, "UTF-8");

            InputSource is = new InputSource(reader);
            is.setEncoding("UTF-8");

            FeedHandler parser = new FeedHandler();
            SAXParserFactory.newInstance().newSAXParser().parse(is, parser);
            return parser;
        } finally {
            try {
                if (inputXml != null)
                    inputXml.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static Student requestRegistration(Context context, String email, String password) throws JSONException, IOException {
        URL url = new URL(STUDENTS_URL + "/register");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("PUT");
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setRequestProperty("Accept", "application/json");
        connection.setDoInput(true);

        JSONObject data = new JSONObject();
        data.put(ServerDB.Student.EMAIL, email);
        data.put(ServerDB.Student.PASSWORD, password);

        OutputStreamWriter wr = new OutputStreamWriter(connection.getOutputStream());
        wr.write(data.toString());
        wr.flush();

        connection.connect();

        InputStream inputStream = connection.getInputStream();

        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null)
            sb.append(line).append("\n");

        Student student = new Student(new JSONObject(sb.toString()));
        AuthHelper.registerLogin(context, student);
        return student;
    }

    public static Survey getSurvey(String id) throws JSONException, IOException {
        URL url = new URL(SURVEYS_URL + "/" + id);
        BufferedReader reader = new BufferedReader(new InputStreamReader(
                url.openStream(), "UTF-8"));
        StringBuilder buffer = new StringBuilder();
        int read;
        char[] chars = new char[1024];
        while ((read = reader.read(chars)) != -1)
            buffer.append(chars, 0, read);

        return new Survey(new JSONObject(buffer.toString()).getJSONObject("survey"));
    }

    public static Survey[] getSurveys() throws JSONException, IOException {
        URL url = new URL(SURVEYS_URL);
        BufferedReader reader = new BufferedReader(new InputStreamReader(
                url.openStream(), "UTF-8"));
        StringBuilder buffer = new StringBuilder();
        int read;
        char[] chars = new char[1024];
        while ((read = reader.read(chars)) != -1)
            buffer.append(chars, 0, read);

        JSONArray jsonArray = new JSONObject(buffer.toString()).getJSONArray("surveys");
        int length = jsonArray.length();
        Survey[] surveys = new Survey[length];
        for (int i = 0; i < length; i++)
            surveys[i] = new Survey(jsonArray.getJSONObject(i));

        return surveys;
    }

}
