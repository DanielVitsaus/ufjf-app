package br.ufjf.app.ui;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import br.ufjf.app.async.GetSurveyTask;
import br.ufjf.app.async.SubmitAnswerTask;
import br.ufjf.app.model.survey.Answer;
import br.ufjf.app.model.survey.Question;
import br.ufjf.app.model.survey.Survey;
import br.ufjf.app.model.survey.SurveyAnswer;
import br.ufjf.app.ui.adapter.SurveyAdapter;
import br.ufjf.app.ui.question.QuestionFragment;
import br.ufjf.app.util.AuthHelper;
import br.ufjf.dcc.pesquisa.R;

/**
 * Created by Jorge Augusto da Silva Moreira on 20/05/2015.
 */
public class SurveyActivity extends AppCompatActivity implements QuestionFragment.Listener, SurveySubmitFragment.Listener {
    public static final String ARG_SURVEY = "survey";

    private ViewPager mViewPager;
    private SurveyAdapter mAdapter;
    private SurveyAnswer mSurveyAnswer;

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

                    try {
                        mSurveyAnswer = new SurveyAnswer(AuthHelper.getStudent(SurveyActivity.this).getId(), survey.getId(), survey.getQuestions().length);
                        mAdapter = new SurveyAdapter(getSupportFragmentManager(), survey.getQuestions());
                        mViewPager.setAdapter(mAdapter);
                    } catch (AuthHelper.StudentNotLoggedIn studentNotLoggedIn) {
                        //todo
                        studentNotLoggedIn.printStackTrace();
                    }
                }
            }
        }).execute(getIntent().getStringExtra(ARG_SURVEY));
    }

    @Override
    public Question getQuestion(int index) {
        return mAdapter.getQuestion(index);
    }

    @Override
    public Answer getAnswer(int index) {
        return mSurveyAnswer.getAnswers()[index];
    }

    public void previous(View view) {
        mViewPager.setCurrentItem(mViewPager.getCurrentItem() - 1);
    }

    public void next(View view) {
        mViewPager.setCurrentItem(mViewPager.getCurrentItem() + 1);
    }

    @Override
    public void registerAnswer(int questionIndex, Answer answer) {
        mSurveyAnswer.getAnswers()[questionIndex] = answer;
    }

    @Override
    public boolean isReadyToSubmit() {
        for (Answer answer : mSurveyAnswer.getAnswers())
            if (answer == null)
                return false;
        return true;
    }

    @Override
    public void submitAnswers() {
        new SubmitAnswerTask(new SubmitAnswerTask.Callback() {
            @Override
            public void onFinish(boolean success) {
                if (success) {
                    Toast.makeText(SurveyActivity.this, R.string.answer_sent, Toast.LENGTH_SHORT).show();
                    finish();
                } else
                    Toast.makeText(SurveyActivity.this, R.string.answer_not_sent, Toast.LENGTH_LONG).show();
            }
        }).execute(mSurveyAnswer);
    }
}
