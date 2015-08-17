package br.ufjf.app.async;

import android.os.AsyncTask;

import org.json.JSONException;

import java.io.IOException;

import br.ufjf.app.model.questionario.RespostaQuestionario;
import br.ufjf.app.util.WebHelper;

/**
 * Created by Jorge Augusto da Silva Moreira on 19/06/2015.
 */
public class EnviarRespostasTask extends AsyncTask<RespostaQuestionario, Void, Boolean> {

    private final Callback callback;

    public EnviarRespostasTask(Callback callback) {
        this.callback = callback;
    }

    @Override
    protected Boolean doInBackground(RespostaQuestionario... answer) {
        try {
            return WebHelper.enviarResposta(answer[0]);
        } catch (JSONException | IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    protected void onPostExecute(Boolean success) {
        callback.onFinish(success);
    }

    public interface Callback {
        void onFinish(boolean success);
    }
}
