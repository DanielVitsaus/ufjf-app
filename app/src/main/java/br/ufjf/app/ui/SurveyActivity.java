package br.ufjf.app.ui;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import br.ufjf.app.async.GetSurveyTask;
import br.ufjf.app.model.survey.Question;
import br.ufjf.app.model.survey.Survey;
import br.ufjf.app.ui.adapter.SurveyAdapter;
import br.ufjf.app.ui.question.QuestionFragment;
import br.ufjf.dcc.pesquisa.R;

/**
 * Created by Jorge Augusto da Silva Moreira on 20/05/2015.
 */
public class SurveyActivity extends AppCompatActivity implements QuestionFragment.Listener{
    public static final String ARG_SURVEY = "survey";

    private ViewPager mViewPager;
    private SurveyAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey);

        mViewPager = (ViewPager) findViewById(R.id.survey_pager);

        new GetSurveyTask(new GetSurveyTask.Callback() {

            @Override
            public void onFinish(Survey survey) {
                if (survey != null) {
                    // Survey loaded
                    Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
                    toolbar.setTitle(survey.getTitle());
                    toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);
                    toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            finish();
                        }
                    });

                    mAdapter = new SurveyAdapter(getSupportFragmentManager(), survey.getQuestions());
                    mViewPager.setAdapter(mAdapter);
                }
            }
        }).execute(getIntent().getStringExtra(ARG_SURVEY));
    }

    @Override
    public Question getQuestion(int index) {
        return mAdapter.getQuestion(index);
    }

    public void previous(View view){
        mViewPager.setCurrentItem(mViewPager.getCurrentItem() - 1);
    }

    public void next(View view){
        mViewPager.setCurrentItem(mViewPager.getCurrentItem() + 1);
    }
}
