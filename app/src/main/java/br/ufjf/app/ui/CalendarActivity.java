package br.ufjf.app.ui;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.List;

import br.ufjf.app.async.GetCalendarTask;
import br.ufjf.app.model.Calendar;
import br.ufjf.app.model.Date;
import br.ufjf.app.ui.adapter.DatesAdapter;
import br.ufjf.app.ui.adapter.MonthsAdapter;
import br.ufjf.dcc.pesquisa.R;

/**
 * Created by Jorge Augusto da Silva Moreira on 24/06/2015.
 */
public class CalendarActivity extends DrawerActivity implements CalendarMonthFragment.Listener {

    private ViewPager mViewPager;
    private MonthsAdapter mAdapter;
    private Calendar mCalendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);

        initializeToolbar();

        new GetCalendarTask(new GetCalendarTask.Callback() {
            @Override
            public void onFinish(Calendar calendar) {
                if(calendar != null) {
                    mCalendar = calendar;

                    mAdapter = new MonthsAdapter(getSupportFragmentManager());

                    mViewPager = (ViewPager) findViewById(R.id.calendar_pager);
                    mViewPager.setAdapter(mAdapter);

                    TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
                    tabLayout.setupWithViewPager(mViewPager);

                    LinearLayoutManager layoutManager = new LinearLayoutManager(CalendarActivity.this);
                    RecyclerView recyclerView = (RecyclerView) findViewById(R.id.dates_list);
                    recyclerView.setLayoutManager(layoutManager);
                    recyclerView.setAdapter(new DatesAdapter(calendar, new DatesAdapter.OnDateClickListener() {
                        @Override
                        public void onDateSelected(Date date) {

                        }
                    }));
                } else {
                    //todo
                }
            }
        }).execute(2015);
    }

    @Override
    public List<Date> getDates(int month) {
        if (mCalendar == null)
            return null;
        return mCalendar.getDatesByMonth(month);
    }
}
