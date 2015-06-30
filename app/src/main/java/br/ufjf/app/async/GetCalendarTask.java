package br.ufjf.app.async;

import android.os.AsyncTask;

import org.json.JSONException;

import java.io.IOException;

import br.ufjf.app.model.Calendar;
import br.ufjf.app.util.WebHelper;

/**
 * Created by Jorge Augusto da Silva Moreira on 20/05/2015.
 */
public class GetCalendarTask extends AsyncTask<Integer, Void, Calendar> {

    private final Callback mCallback;

    public GetCalendarTask(Callback callback) {
        mCallback = callback;
    }

    @Override
    protected Calendar doInBackground(Integer... year) {
        try {
            return WebHelper.getCalendar(year[0]);
        } catch (JSONException | IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected void onPostExecute(Calendar calendar) {
        mCallback.onFinish(calendar);
    }

    public interface Callback {
        void onFinish(Calendar calendar);
    }
}
