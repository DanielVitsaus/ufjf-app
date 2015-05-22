package br.ufjf.app.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

import br.ufjf.app.model.survey.ChoiceQuestion;
import br.ufjf.app.model.survey.Question;
import br.ufjf.app.model.survey.ScaleQuestion;
import br.ufjf.app.model.survey.TextQuestion;
import br.ufjf.app.ui.question.ChoiceQuestionFragment;
import br.ufjf.app.ui.question.ScaleQuestionFragment;
import br.ufjf.app.ui.question.TextQuestionFragment;

/**
 * Created by Jorge Augusto da Silva Moreira on 20/05/2015.
 */
public class SurveyAdapter extends FragmentPagerAdapter {

    private List<Question> mQuestions;

    public SurveyAdapter(FragmentManager fragmentManager, List<Question> questions) {
        super(fragmentManager);
        mQuestions = questions;
    }

    @Override
    public Fragment getItem(int position) {
        Question question = mQuestions.get(position);

        if (question instanceof TextQuestion)
            return TextQuestionFragment.newInstance(position);
        else if (question instanceof ScaleQuestion)
            return ScaleQuestionFragment.newInstance(position);
        else if (question instanceof ChoiceQuestion)
            return ChoiceQuestionFragment.newInstance(position);
        else return null;
    }

    @Override
    public int getCount() {
        return mQuestions.size();
    }

    public Question getQuestion(int position) {
        return mQuestions.get(position);
    }
}
