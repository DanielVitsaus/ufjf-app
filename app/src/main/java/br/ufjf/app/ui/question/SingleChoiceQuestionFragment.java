package br.ufjf.app.ui.question;

import android.os.Bundle;

import br.ufjf.app.model.survey.Question;
import br.ufjf.app.model.survey.SingleChoiceQuestion;
import br.ufjf.dcc.pesquisa.R;

/**
 * Created by Jorge Augusto da Silva Moreira on 22/05/2015.
 */
public class SingleChoiceQuestionFragment extends QuestionFragment {
    SingleChoiceQuestion mQuestion;

    public static SingleChoiceQuestionFragment newInstance(int questionIndex){
        SingleChoiceQuestionFragment fragment = new SingleChoiceQuestionFragment();
        setupNewInstance(fragment, questionIndex);
        return fragment;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mQuestion = (SingleChoiceQuestion) mListener.getQuestion(getQuestionIndex());
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
