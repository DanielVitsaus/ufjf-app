package br.ufjf.app.async;

import android.os.AsyncTask;

import org.json.JSONException;

import java.io.IOException;

import br.ufjf.app.model.survey.SurveyAnswer;
import br.ufjf.app.util.WebHelper;

/**
 * Created by Jorge Augusto da Silva Moreira on 19/06/2015.
 */
public class SubmitAnswerTask extends AsyncTask<SurveyAnswer, Void, Boolean> {

    private final Callback mCallback;

    public SubmitAnswerTask(Callback callback) {
        mCallback = callback;
    }

    @Override
    protected Boolean doInBackground(SurveyAnswer... answer) {
        try {
            return WebHelper.sendAnswer(answer[0]);
        } catch (JSONException | IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    protected void onPostExecute(Boolean success) {
        mCallback.onFinish(success);
    }

    public interface Callback {
        void onFinish(boolean success);
    }
}
