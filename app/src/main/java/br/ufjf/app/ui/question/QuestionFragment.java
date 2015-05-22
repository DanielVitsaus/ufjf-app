package br.ufjf.app.ui.question;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import br.ufjf.app.model.survey.Question;
import br.ufjf.dcc.pesquisa.R;

/**
 * Created by Jorge Augusto da Silva Moreira on 20/05/2015.
 */
public abstract class QuestionFragment extends Fragment {
    private static final String ARG_QUESTION_INDEX = "question";
    private static final String TAG = "QuestionFragment";

    protected Listener mListener;

    protected static void setupNewInstance(QuestionFragment questionFragment, int questionIndex){
        Bundle args = new Bundle();
        args.putInt(ARG_QUESTION_INDEX, questionIndex);
        questionFragment.setArguments(args);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (Listener) activity;
        } catch (ClassCastException e) {
            Log.v(TAG, "Activity must implement QuestionFragment.Listener");
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(getLayoutRes(), container, false);

        ((TextView) view.findViewById(R.id.question_title)).setText(getQuestion().getTitle());

        return view;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    protected int getQuestionIndex(){
        return getArguments().getInt(ARG_QUESTION_INDEX);
    }

    protected abstract int getLayoutRes();

    protected abstract Question getQuestion();

    public interface Listener {
        Question getQuestion(int index);
    }
}
