package br.ufjf.app.ui.question;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import br.ufjf.app.model.survey.Question;
import br.ufjf.app.model.survey.TextQuestion;
import br.ufjf.dcc.pesquisa.R;

/**
 * Created by Jorge Augusto da Silva Moreira on 22/05/2015.
 */
public class TextQuestionFragment extends QuestionFragment {
    private TextQuestion mQuestion;

    public static TextQuestionFragment newInstance(int questionIndex) {
        TextQuestionFragment fragment = new TextQuestionFragment();
        setupNewInstance(fragment, questionIndex);
        return fragment;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mQuestion = (TextQuestion) mListener.getQuestion(getQuestionIndex());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);

        EditText answerEditText = (EditText) view.findViewById(R.id.question_answer);
        if (mQuestion.isSingleLine())
            answerEditText.setSingleLine();
        else {
            answerEditText.setHorizontalScrollBarEnabled(false);
            answerEditText.setLines(7);
            answerEditText.setMaxLines(7);
            answerEditText.setEms(10);
        }

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_q_text;
    }

    @Override
    protected Question getQuestion() {
        return mQuestion;
    }
}
