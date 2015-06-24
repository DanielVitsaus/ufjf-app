package br.ufjf.app.ui;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;

import br.ufjf.app.ui.adapter.MonthsAdapter;
import br.ufjf.dcc.pesquisa.R;

/**
 * Created by Jorge Augusto da Silva Moreira on 24/06/2015.
 */
public class CalendarActivity extends DrawerActivity {

    private ViewPager mViewPager;
    private MonthsAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);

        mAdapter = new MonthsAdapter(getSupportFragmentManager());

        mViewPager = (ViewPager) findViewById(R.id.calendar_pager);
        mViewPager.setAdapter(mAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

        initializeToolbar();
    }
}
