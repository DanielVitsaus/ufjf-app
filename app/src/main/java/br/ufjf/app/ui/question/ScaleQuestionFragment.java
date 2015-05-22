package br.ufjf.app.ui.question;

import android.app.Activity;

import br.ufjf.app.model.survey.Question;
import br.ufjf.app.model.survey.ScaleQuestion;
import br.ufjf.dcc.pesquisa.R;

/**
 * Created by Jorge Augusto da Silva Moreira on 22/05/2015.
 */
public class ScaleQuestionFragment extends QuestionFragment {
    ScaleQuestion mQuestion;

    public static ScaleQuestionFragment newInstance(int questionIndex){
        ScaleQuestionFragment fragment = new ScaleQuestionFragment();
        setupNewInstance(fragment, questionIndex);
        return fragment;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mQuestion = (ScaleQuestion) mListener.getQuestion(getQuestionIndex());
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_q_scale;
    }

    @Override
    protected Question getQuestion() {
        return mQuestion;
    }
}
