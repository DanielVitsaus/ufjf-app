package br.ufjf.app.ui;

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
import br.ufjf.app.model.survey.SimpleQuestion;
import br.ufjf.dcc.pesquisa.R;

/**
 * Created by Jorge Augusto da Silva Moreira on 20/05/2015.
 */
public class QuestionFragment extends Fragment {
    private static final String ARG_QUESTION_INDEX = "question";
    private static final String TAG = "QuestionFragment";

    private Question mQuestion;
    private Listener mListener;

    public interface Listener {
        Question getQuestion(int index);
    }

    public static QuestionFragment newInstance(int questionIndex) {
        Bundle args = new Bundle();
        args.putInt(ARG_QUESTION_INDEX, questionIndex);

        QuestionFragment questionFragment = new QuestionFragment();
        questionFragment.setArguments(args);

        return questionFragment;
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

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mQuestion = mListener.getQuestion(getArguments().getInt(ARG_QUESTION_INDEX));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = null;

        if (mQuestion instanceof SimpleQuestion) {
            view = inflater.inflate(R.layout.fragment_q_simple, container, false);

            ((TextView) view.findViewById(R.id.question_title)).setText(mQuestion.getTitle());
            //((TextView) view.findViewById(R.id.)).setText(mQuestion.get);
        }

        return view;
    }
}
