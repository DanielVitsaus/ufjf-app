package br.ufjf.app.async;

import android.content.Context;
import android.os.AsyncTask;

import org.json.JSONException;

import java.io.IOException;

import br.ufjf.app.model.Aluno;
import br.ufjf.app.util.WebHelper;

/**
 * Efetua a autenticação de um estudante
 * Created by Jorge Augusto da Silva Moreira on 20/05/2015.
 */
public class SignInTask extends AsyncTask<String, Void, Aluno> {

    private final Callback callback;
    private final Context context;

    public SignInTask(Context context, Callback callback) {
        this.callback = callback;
        this.context = context;
    }

    @Override
    protected Aluno doInBackground(String... credenciais) {
        try {
            return WebHelper.entrar(context, credenciais[0], credenciais[1]);
        } catch (JSONException | IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected void onPostExecute(Aluno aluno) {
        callback.onFinish(aluno);
    }

    public interface Callback {
        void onFinish(Aluno aluno);
    }
}
