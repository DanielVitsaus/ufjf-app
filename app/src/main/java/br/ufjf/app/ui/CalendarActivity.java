package br.ufjf.app.ui;

import android.content.Intent;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import br.ufjf.app.async.GetCalendarTask;
import br.ufjf.app.model.AcademicCalendar;
import br.ufjf.app.model.Date;
import br.ufjf.app.ui.adapter.DatesAdapter;
import br.ufjf.app.ui.adapter.MonthsAdapter;
import br.ufjf.dcc.pesquisa.R;

/**
 * Created by Jorge Augusto da Silva Moreira on 24/06/2015.
 */
public class CalendarActivity extends DrawerActivity implements CalendarMonthFragment.Listener {

    private static final int REQ_REMINDER_CODE = 15487;

    private ViewPager mViewPager;
    private MonthsAdapter mMonthsAdapter;
    private AcademicCalendar mAcademicCalendar;
    private RecyclerView recyclerView;
    private DatesAdapter mDatesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);

        initializeToolbar();

        new GetCalendarTask(new GetCalendarTask.Callback() {
            @Override
            public void onFinish(AcademicCalendar academicCalendar) {
                if (academicCalendar != null) {
                    mAcademicCalendar = academicCalendar;

                    java.util.Calendar cal = java.util.Calendar.getInstance();
                    List<String> monthNames = new ArrayList<>();
                    for (int i = 0; i < 12; i++) {
                        cal.set(java.util.Calendar.MONTH, i);
                        String monthName = cal.getDisplayName(java.util.Calendar.MONTH, java.util.Calendar.LONG, getResources().getConfiguration().locale);
                        monthName = Character.toUpperCase(monthName.charAt(0)) + monthName.substring(1);
                        monthNames.add(monthName);
                    }

                    mMonthsAdapter = new MonthsAdapter(
                            getSupportFragmentManager(),
                            monthNames);

                    mViewPager = (ViewPager) findViewById(R.id.calendar_pager);
                    mViewPager.setAdapter(mMonthsAdapter);
                    mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                        @Override
                        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                        }

                        @Override
                        public void onPageSelected(int position) {
                            int monthPosition = mDatesAdapter.getMonthPosition(position);
                            if (monthPosition >= 0)
                                recyclerView.smoothScrollToPosition(monthPosition);
                        }

                        @Override
                        public void onPageScrollStateChanged(int state) {

                        }
                    });

                    TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
                    tabLayout.setupWithViewPager(mViewPager);

                    mDatesAdapter = new DatesAdapter(academicCalendar, monthNames, new DatesAdapter.OnDateClickListener() {
                        @Override
                        public void onDateSelected(View overflowView, final Date date) {
                            final PopupMenu menu = new PopupMenu(CalendarActivity.this, overflowView);
                            menu.inflate(R.menu.date_item);
                            menu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                                @Override
                                public boolean onMenuItemClick(MenuItem menuItem) {
                                    if (menuItem.getItemId() == R.id.create_reminder) {
                                        Intent intent = new Intent(Intent.ACTION_EDIT)
                                                .setData(CalendarContract.Events.CONTENT_URI)
                                                .putExtra(CalendarContract.EXTRA_EVENT_ALL_DAY, true)
                                                .putExtra(CalendarContract.Events.TITLE, date.getTitle());

                                        Calendar cal = Calendar.getInstance();
                                        cal.set(Calendar.DAY_OF_MONTH, date.getDayStart());
                                        cal.set(Calendar.MONTH, date.getMonth());

                                        intent.putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, cal.getTimeInMillis());

                                        if (date.getDayEnd() != -1)
                                            cal.set(Calendar.DAY_OF_MONTH, date.getDayEnd());
                                        intent.putExtra(CalendarContract.EXTRA_EVENT_END_TIME, cal.getTimeInMillis());

                                        startActivityForResult(intent, REQ_REMINDER_CODE);
                                    }
                                    return true;
                                }
                            });
                            menu.show();
                        }
                    });

                    LinearLayoutManager layoutManager = new LinearLayoutManager(CalendarActivity.this);
                    recyclerView = (RecyclerView) findViewById(R.id.dates_list);
                    recyclerView.setLayoutManager(layoutManager);
                    recyclerView.setAdapter(mDatesAdapter);
                } else {
                    //todo mostrar mensagem de erro
                }
            }
        }).execute(2015);
    }

    @Override
    public List<Date> getDates(int month) {
        if (mAcademicCalendar == null)
            return null;
        return mAcademicCalendar.getDatesByMonth(month);
    }

    @Override
    public void onDayClick(Date date) {
        recyclerView.smoothScrollToPosition(mDatesAdapter.getDatePosition(date));
    }
}
