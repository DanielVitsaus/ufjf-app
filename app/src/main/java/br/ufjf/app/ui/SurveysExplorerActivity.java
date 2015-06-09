package br.ufjf.app.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import br.ufjf.app.async.GetSurveysTask;
import br.ufjf.app.model.survey.Survey;
import br.ufjf.app.ui.adapter.SurveysAdapter;
import br.ufjf.dcc.pesquisa.R;

/**
 * Created by Jorge Augusto da Silva Moreira on 20/05/2015.
 */
public class SurveysExplorerActivity extends BaseActivity {

    private static final String TAG_FRAG = "main";

    private RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_surveys_exp);
        initializeToolbar();

        mRecyclerView = (RecyclerView) findViewById(R.id.list_surveys);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);

        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(layoutManager);

        new GetSurveysTask(new GetSurveysTask.Callback() {
            @Override
            public void onFinish(Survey[] surveys) {
                mRecyclerView.setAdapter(new SurveysAdapter(surveys, new SurveysAdapter.OnSurveyClickListener() {
                    @Override
                    public void onSurveyClick(Survey survey) {
                        startActivity(new Intent(SurveysExplorerActivity.this, SurveyActivity.class)
                                .putExtra(SurveyActivity.ARG_SURVEY, survey.getId()));
                    }
                }));
            }
        }).execute();
    }
}
