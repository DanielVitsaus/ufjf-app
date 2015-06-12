package br.ufjf.app.async;

import android.content.Context;
import android.os.AsyncTask;

import org.json.JSONException;

import java.io.IOException;

import br.ufjf.app.model.Student;
import br.ufjf.app.util.WebHelper;

/**
 * Created by Jorge Augusto da Silva Moreira on 20/05/2015.
 */
public class RegistrationTask extends AsyncTask<String, Void, Student> {

    private final Callback mCallback;
    private final Context mContext;

    public RegistrationTask(Context context, Callback callback) {
        mCallback = callback;
        mContext = context;
    }

    @Override
    protected Student doInBackground(String... credentials) {
        try {
            return WebHelper.requestRegistration(mContext, credentials[0], credentials[1]);
        } catch (JSONException | IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected void onPostExecute(Student student) {
        mCallback.onFinish(student);
    }

    public interface Callback {
        void onFinish(Student student);
    }
}
