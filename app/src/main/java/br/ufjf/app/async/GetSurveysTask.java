package br.ufjf.app.async;

import android.os.AsyncTask;

import org.json.JSONException;

import java.io.IOException;

import br.ufjf.app.model.survey.Survey;
import br.ufjf.app.util.WebHelper;

/**
 * Created by Jorge Augusto da Silva Moreira on 20/05/2015.
 */
public class GetSurveysTask extends AsyncTask<Void, Void, Survey[]> {

    private final Callback mCallback;

    public GetSurveysTask(Callback callback) {
        mCallback = callback;
    }

    @Override
    protected Survey[] doInBackground(Void... ignored) {
        try {
            return WebHelper.getSurveys();
        } catch (JSONException | IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected void onPostExecute(Survey[] surveys) {
        mCallback.onFinish(surveys);
    }

    public interface Callback {
        void onFinish(Survey[] survey);
    }
}
