package br.ufjf.app.async;

import android.os.AsyncTask;

import org.json.JSONException;

import java.io.IOException;

import br.ufjf.app.model.questionario.Questionario;
import br.ufjf.app.util.WebHelper;

/**
 * Carrega os questionários disponíveis
 * Created by Jorge Augusto da Silva Moreira on 20/05/2015.
 */
public class ObterQuestionariosTask extends AsyncTask<Void, Void, Questionario[]> {

    private final Callback callback;

    public ObterQuestionariosTask(Callback callback) {
        this.callback = callback;
    }

    @Override
    protected Questionario[] doInBackground(Void... ignored) {
        try {
            return WebHelper.obterQuestionarios();
        } catch (JSONException | IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected void onPostExecute(Questionario[] questionarios) {
        callback.onFinish(questionarios);
    }

    public interface Callback {
        void onFinish(Questionario[] questionario);
    }
}
