package br.ufjf.app.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import br.ufjf.app.async.GetSurveysTask;
import br.ufjf.app.model.survey.Survey;
import br.ufjf.app.ui.adapter.SurveysAdapter;
import br.ufjf.dcc.pesquisa.R;

/**
 * Created by Jorge Augusto da Silva Moreira on 20/05/2015.
 */
public class SurveysExplorerFragment extends ToolbarFragment {

    private RecyclerView mRecyclerView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_surveys_exp, container, false);

        initializeToolbar(view, R.string.surveys);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.list_surveys);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());

        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(layoutManager);

        new GetSurveysTask(new GetSurveysTask.Callback() {
            @Override
            public void onFinish(Survey[] surveys) {
                mRecyclerView.setAdapter(new SurveysAdapter(surveys, new SurveysAdapter.OnSurveyClickListener() {
                    @Override
                    public void onSurveyClick(Survey survey) {
                        startActivity(new Intent(getActivity(), SurveyActivity.class)
                                .putExtra(SurveyActivity.ARG_SURVEY, survey.getId()));
                    }
                }));
            }
        }).execute();
    }

    @Override
    protected void inflateMenu(Toolbar toolbar) {

    }

    @Override
    protected void onToolbarMenuItemClick(MenuItem menuItem) {

    }
}
