package br.ufjf.app.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import br.ufjf.app.ui.QuestionFragment;

/**
 * Created by Jorge Augusto da Silva Moreira on 20/05/2015.
 */
public class SurveyAdapter extends FragmentPagerAdapter {

    private int mQuestionsCount;

    public SurveyAdapter(FragmentManager fragmentManager, int questionsCount) {
        super(fragmentManager);
        mQuestionsCount = questionsCount;
    }

    @Override
    public Fragment getItem(int position) {
        return QuestionFragment.newInstance(position);
    }

    @Override
    public int getCount() {
        return mQuestionsCount;
    }
}
