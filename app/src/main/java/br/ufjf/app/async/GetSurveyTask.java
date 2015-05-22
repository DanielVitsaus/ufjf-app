package br.ufjf.app.async;

import android.os.AsyncTask;

import org.json.JSONException;

import java.io.IOException;

import br.ufjf.app.model.survey.Survey;
import br.ufjf.app.util.WebHelper;

/**
 * Created by Jorge Augusto da Silva Moreira on 20/05/2015.
 */
public class GetSurveyTask extends AsyncTask<String, Void, Survey> {

    private final Callback mCallback;

    public GetSurveyTask(Callback callback) {
        mCallback = callback;
    }

    @Override
    protected Survey doInBackground(String... surveyIds) {
        try {
            return WebHelper.getSurvey(surveyIds[0]);
        } catch (JSONException | IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected void onPostExecute(Survey survey) {
        mCallback.onFinish(survey);
    }

    public interface Callback {
        void onFinish(Survey survey);
    }
}
