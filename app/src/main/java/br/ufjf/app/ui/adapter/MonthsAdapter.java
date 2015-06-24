package br.ufjf.app.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import br.ufjf.app.ui.CalendarMonthFragment;

/**
 * Created by Jorge Augusto da Silva Moreira on 20/05/2015.
 */
public class MonthsAdapter extends FragmentPagerAdapter {
    private List<String> mMonthNames;

    public MonthsAdapter(FragmentManager fragmentManager) {
        super(fragmentManager);

        Calendar calendar = Calendar.getInstance();
        mMonthNames = new ArrayList<>();
        for (int i = 0; i < 12; i++) {
            calendar.set(Calendar.MONTH, i);
            mMonthNames.add(calendar.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.US));
        }
    }

    @Override
    public Fragment getItem(int position) {
        return CalendarMonthFragment.newInstance(position);
    }

    @Override
    public int getCount() {
        return 12;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mMonthNames.get(position);
    }
}
