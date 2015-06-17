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

    public static Student requestSignIn(Context context, String email, String password) throws JSONException, IOException {
        URL url = new URL(STUDENTS_URL + "/login");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setRequestProperty("Accept", "application/json");
        connection.setDoInput(true);

        JSONObject input = new JSONObject();
        input.put(ServerDB.Student.EMAIL, email);
        input.put(ServerDB.Student.PASSWORD, password);

        OutputStreamWriter wr = new OutputStreamWriter(connection.getOutputStream());
        wr.write(input.toString());
        wr.flush();

        connection.connect();

        InputStream inputStream = connection.getInputStream();

        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null)
            sb.append(line).append("\n");

        JSONObject data = new JSONObject(sb.toString());
        if (data.getBoolean("success")) {
            Student student = new Student(data.getJSONObject("student"));
            AuthHelper.registerLogin(context, student);
            return student;
        } else
            return null;
    }

    public static Survey getSurvey(String id) throws JSONException, IOException {
        URL url = new URL(SURVEYS_URL + "/" + id);
        BufferedReader reader = new BufferedReader(new InputStreamReader(
                url.openStream(), "UTF-8"));
        StringBuilder sb = new StringBuilder();
        int read;
        char[] chars = new char[1024];
        while ((read = reader.read(chars)) != -1)
            sb.append(chars, 0, read);

        JSONObject data = new JSONObject(sb.toString());
        if (data.getBoolean("success"))
            return new Survey(data.getJSONObject("survey"));
        else
            return null;
    }

    public static Survey[] getSurveys() throws JSONException, IOException {
        URL url = new URL(SURVEYS_URL);
        BufferedReader reader = new BufferedReader(new InputStreamReader(
                url.openStream(), "UTF-8"));
        StringBuilder sb = new StringBuilder();
        int read;
        char[] chars = new char[1024];
        while ((read = reader.read(chars)) != -1)
            sb.append(chars, 0, read);

        JSONObject data = new JSONObject(sb.toString());
        if (data.getBoolean("success")) {
            JSONArray jsonArray = data.getJSONArray("surveys");
            int length = jsonArray.length();
            Survey[] surveys = new Survey[length];
            for (int i = 0; i < length; i++)
                surveys[i] = new Survey(jsonArray.getJSONObject(i));

            return surveys;
        } else
            return null;
    }

}
