package br.ufjf.app.async;

import android.os.AsyncTask;

import org.json.JSONException;

import java.io.IOException;

import br.ufjf.app.model.questionario.Questionario;
import br.ufjf.app.util.WebHelper;

/**
 * Carrega um questionário específico
 * Created by Jorge Augusto da Silva Moreira on 20/05/2015.
 */
public class ObterQuestionarioTask extends AsyncTask<String, Void, Questionario> {

    private final Callback callback;

    public ObterQuestionarioTask(Callback callback) {
        this.callback = callback;
    }

    @Override
    protected Questionario doInBackground(String... surveyIds) {
        try {
            return WebHelper.obterQuestionario(surveyIds[0]);
        } catch (JSONException | IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected void onPostExecute(Questionario questionario) {
        callback.onFinish(questionario);
    }

    public interface Callback {
        void onFinish(Questionario questionario);
    }
}
