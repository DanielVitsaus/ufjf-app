package br.ufjf.app.ui.adapter;

import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import br.ufjf.app.model.Date;
import br.ufjf.dcc.pesquisa.R;

/**
 * Created by Jorge Augusto da Silva Moreira on 25/07/2014.
 */
public class MonthAdapter extends RecyclerView.Adapter<MonthAdapter.DayViewHolder> {
    private final int mDayOfWeek;
    private final int mDaysInMonth;
    private final int mTodayPosition;
    private final HashMap<Integer, Date> mDates;
    private final OnDayClickListener mListener;

    public interface OnDayClickListener {
        void onDayClick(Date date);
    }

    public MonthAdapter(int month, List<Date> dates, OnDayClickListener listener) {
        mListener = listener;

        Calendar calendar = Calendar.getInstance();
        if (calendar.get(Calendar.MONTH) == month) {
            mDayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
            mTodayPosition = getPositionFromDay(calendar.get(Calendar.DAY_OF_MONTH));
        } else {
            calendar.set(Calendar.MONTH, month);
            mTodayPosition = -1;
            mDayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        }

        mDaysInMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);

        mDates = new HashMap<>();
        if (dates != null)
            for (Date date : dates)
                mDates.put(getPositionFromDay(date.getDayStart()), date);
    }

    @Override
    public MonthAdapter.DayViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MonthAdapter.DayViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_calendar_day, parent, false));
    }

    @Override
    public void onBindViewHolder(MonthAdapter.DayViewHolder holder, final int position) {
        TextView dayView = (TextView) holder.itemView;

        int day = getDayFromPosition(position);
        if (day > 0) {
            dayView.setText(day + "");

            if (mTodayPosition == position)
                dayView.setTypeface(null, Typeface.BOLD);
            else
                dayView.setTypeface(null, Typeface.NORMAL);

            Date date = mDates.get(position);
            if (date != null) {
                dayView.setTextColor(dayView.getContext().getResources().getColor(R.color.primary));
                dayView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mListener.onDayClick(mDates.get(position));
                    }
                });
            } else {
                dayView.setTextColor(dayView.getContext().getResources().getColor(R.color.text_secondary));
                dayView.setOnClickListener(null);
            }
        } else
            dayView.setText("");
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return mDaysInMonth + mDayOfWeek - 1;
    }

    private int getPositionFromDay(int day) {
        return day + mDayOfWeek - 2;
    }

    private int getDayFromPosition(int position) {
        return position - mDayOfWeek + 2;
    }

    protected class DayViewHolder extends RecyclerView.ViewHolder {

        public DayViewHolder(View itemView) {
            super(itemView);
        }
    }
}
