package br.ufjf.app.ui.adapter;

import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Calendar;
import java.util.List;

import br.ufjf.app.model.Date;
import br.ufjf.dcc.pesquisa.R;

/**
 * Created by Jorge Augusto da Silva Moreira on 25/07/2014.
 */
public class MonthAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final int DayOfWeek;
    private final int mDaysInMonth;
    private final int mToday;
    private final List<Date> mDates;

    public MonthAdapter(int month, List<Date> dates) {
        mDates = dates;
        Calendar calendar = Calendar.getInstance();

        if (calendar.get(Calendar.MONTH) == month)
            mToday = calendar.get(Calendar.DAY_OF_MONTH);
        else
            mToday = 0;

        calendar.set(Calendar.MONTH, month);

        DayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        mDaysInMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new RecyclerView.ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_calendar_day, parent, false)) {
        };
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        TextView dayView = (TextView) holder.itemView;

        if (position + 1 >= DayOfWeek) {
            dayView.setText(position - DayOfWeek + 2 + "");

            if (mToday > 0 && position - DayOfWeek + 2 == mToday)
                dayView.setTypeface(null, Typeface.BOLD);
            else
                dayView.setTypeface(null, Typeface.NORMAL);
            // todo melhorar
            if (mDates != null)
                for (Date date : mDates)
                    if (date.getDay() == position - DayOfWeek + 2)
                        dayView.setTextColor(dayView.getContext().getResources().getColor(R.color.primary));
        } else
            dayView.setText("");
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return mDaysInMonth + DayOfWeek - 1;
    }
}
