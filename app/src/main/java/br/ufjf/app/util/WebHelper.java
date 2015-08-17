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

import br.ufjf.app.model.CalendarioAcademico;
import br.ufjf.app.model.DBRemoto;
import br.ufjf.app.model.Estudante;
import br.ufjf.app.model.questionario.Questionario;
import br.ufjf.app.model.questionario.RespostaQuestionario;

/**
 * Intermediário para operações HTTP
 */
public class WebHelper {
    private static final String URL_BASE_API = "http://200.131.219.208:4712/";
    private static final String URL_ESTUDANTES = URL_BASE_API + "students/";
    private static final String URL_QUESTIONARIOS = URL_BASE_API + "surveys/";
    private static final String URL_RESPOSTAS = URL_BASE_API + "answers/";
    private static final String URL_DATAS = URL_BASE_API + "dates/";

    public static FeedHandler obterFeed(String url) throws IOException, SAXException, ParserConfigurationException {
        InputStream inputStream = null;
        try {
            inputStream = new URL(url).openConnection().getInputStream();

            Reader reader = new InputStreamReader(inputStream, "UTF-8");

            InputSource is = new InputSource(reader);
            is.setEncoding("UTF-8");

            FeedHandler parser = new FeedHandler();
            SAXParserFactory.newInstance().newSAXParser().parse(is, parser);
            return parser;
        } finally {
            try {
                if (inputStream != null)
                    inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static String obterConteudo(String url) {
        InputStream inputStream = null;
        try {
            inputStream = new URL(url).openConnection().getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
            StringBuilder builder = new StringBuilder();

            String line;
            while ((line = br.readLine()) != null) {
                builder.append(line);
            }
            return builder.toString();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (inputStream != null)
                    inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static Estudante entrar(Context context, String email, String senha) throws JSONException, IOException {
        URL url = new URL(URL_ESTUDANTES + "login");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setRequestProperty("Accept", "application/json");
        connection.setDoInput(true);

        JSONObject input = new JSONObject();
        input.put(DBRemoto.Estudante.EMAIL, email);
        input.put(DBRemoto.Estudante.SENHA, senha);

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
            br.ufjf.app.model.Estudante estudante = new br.ufjf.app.model.Estudante(data.getJSONObject("estudante"));
            AutenticacaoHelper.registerLogin(context, estudante);
            return estudante;
        } else
            return null;
    }

    public static Questionario obterQuestionario(String id) throws JSONException, IOException {
        URL url = new URL(URL_QUESTIONARIOS + id);
        BufferedReader reader = new BufferedReader(new InputStreamReader(
                url.openStream(), "UTF-8"));
        StringBuilder sb = new StringBuilder();
        int read;
        char[] chars = new char[1024];
        while ((read = reader.read(chars)) != -1)
            sb.append(chars, 0, read);

        JSONObject data = new JSONObject(sb.toString());
        if (data.getBoolean("success"))
            return new Questionario(data.getJSONObject("survey"));
        else
            return null;
    }

    public static Questionario[] obterQuestionarios() throws JSONException, IOException {
        URL url = new URL(URL_QUESTIONARIOS);
        BufferedReader reader = new BufferedReader(new InputStreamReader(
                url.openStream(), "UTF-8"));
        StringBuilder sb = new StringBuilder();
        int read;
        char[] chars = new char[1024];
        while ((read = reader.read(chars)) != -1)
            sb.append(chars, 0, read);

        JSONObject data = new JSONObject(sb.toString());
        if (data.getBoolean("success")) {
            JSONArray jsonArray = data.getJSONArray("questionarios");
            int length = jsonArray.length();
            Questionario[] questionarios = new Questionario[length];
            for (int i = 0; i < length; i++)
                questionarios[i] = new Questionario(jsonArray.getJSONObject(i));

            return questionarios;
        } else
            return null;
    }

    public static boolean enviarResposta(RespostaQuestionario resposta) throws JSONException, IOException {
        URL url = new URL(URL_RESPOSTAS);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setRequestProperty("Accept", "application/json");
        connection.setDoInput(true);

        OutputStreamWriter wr = new OutputStreamWriter(connection.getOutputStream());
        wr.write(resposta.toJSON().toString());
        wr.flush();

        connection.connect();

        InputStream inputStream = connection.getInputStream();

        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null)
            sb.append(line).append("\n");

        return new JSONObject(sb.toString()).getBoolean("success");
    }

    public static CalendarioAcademico obterCalendario(int ano) throws IOException, JSONException {
        URL url = new URL(URL_DATAS);
        BufferedReader reader = new BufferedReader(new InputStreamReader(
                url.openStream(), "UTF-8"));
        StringBuilder sb = new StringBuilder();
        int read;
        char[] chars = new char[1024];
        while ((read = reader.read(chars)) != -1)
            sb.append(chars, 0, read);

        JSONObject data = new JSONObject(sb.toString());
        if (data.getBoolean("success"))
            return new CalendarioAcademico(ano, data.getJSONArray("dates"));
        else
            return null;
    }
}
