package br.ufjf.app.async;

import android.os.AsyncTask;

import org.json.JSONException;

import java.io.IOException;

import br.ufjf.app.model.CalendarioAcademico;
import br.ufjf.app.util.WebHelper;

/**
 * Carrega o calendário acadêmico de um ano
 * Created by Jorge Augusto da Silva Moreira on 20/05/2015.
 */
public class ObterCalendarioTask extends AsyncTask<Integer, Void, CalendarioAcademico> {

    private final Callback callback;

    public ObterCalendarioTask(Callback callback) {
        this.callback = callback;
    }

    @Override
    protected CalendarioAcademico doInBackground(Integer... year) {
        try {
            return WebHelper.obterCalendario(year[0]);
        } catch (JSONException | IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected void onPostExecute(CalendarioAcademico calendarioAcademico) {
        callback.onFinish(calendarioAcademico);
    }

    public interface Callback {
        void onFinish(CalendarioAcademico calendarioAcademico);
    }
}
