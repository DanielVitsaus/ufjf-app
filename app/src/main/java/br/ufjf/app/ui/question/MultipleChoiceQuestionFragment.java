package br.ufjf.app.ui.question;

import android.os.Bundle;

import br.ufjf.app.model.survey.MultipleChoiceQuestion;
import br.ufjf.app.model.survey.Question;
import br.ufjf.dcc.pesquisa.R;

/**
 * Created by Jorge Augusto da Silva Moreira on 22/05/2015.
 */
public class MultipleChoiceQuestionFragment extends QuestionFragment {
    MultipleChoiceQuestion mQuestion;

    public static MultipleChoiceQuestionFragment newInstance(int questionIndex){
        MultipleChoiceQuestionFragment fragment = new MultipleChoiceQuestionFragment();
        setupNewInstance(fragment, questionIndex);
        return fragment;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mQuestion = (MultipleChoiceQuestion) mListener.getQuestion(getQuestionIndex());
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_q_choice;
    }

    @Override
    protected Question getQuestion() {
        return mQuestion;
    }
}
