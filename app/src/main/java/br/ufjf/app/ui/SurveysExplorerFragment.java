package br.ufjf.app.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import br.ufjf.dcc.pesquisa.R;

/**
 * Created by Jorge Augusto da Silva Moreira on 20/05/2015.
 */
public class SurveysExplorerFragment extends ToolbarFragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_surveys_exp, container, false);

        initializeToolbar(view, R.string.surveys);

        view.findViewById(R.id.test).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), SurveyActivity.class).putExtra(SurveyActivity.ARG_SURVEY, "555f773aea2a33de5f0757e2"));
            }
        });

        return view;
    }

    @Override
    protected void inflateMenu(Toolbar toolbar) {

    }

    @Override
    protected void onToolbarMenuItemClick(MenuItem menuItem) {

    }
}
