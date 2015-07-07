package br.ufjf.app.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import br.ufjf.app.model.AcademicCalendar;
import br.ufjf.app.model.Date;
import br.ufjf.dcc.pesquisa.R;

public class DatesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TYPE_HEADER = 0;
    private static final int TYPE_DATE = 1;

    private final List<Date> mDates;
    private final Set<Integer> mHeadersPositions;
    private final HashMap<Integer, String> mHeadersNames;
    private final OnDateClickListener mListener;

    public interface OnDateClickListener {
        void onDateSelected(View overflowView, Date date);
    }

    public DatesAdapter(AcademicCalendar academicCalendar, List<String> monthNames, OnDateClickListener listener) {
        this.mListener = listener;
        mDates = new ArrayList<>();
        mHeadersPositions = new HashSet<>();
        mHeadersNames = new HashMap<>();

        for (int i = 0; i < 12; i++) {
            List<Date> dates = academicCalendar.getDatesByMonth(i);
            if (dates != null) {
                mHeadersPositions.add(mDates.size());
                mHeadersNames.put(mDates.size(), monthNames.get(i));
                mDates.addAll(dates);
            }
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case TYPE_HEADER:
                return new RecyclerView.ViewHolder(LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.list_header, parent, false)) {
                };
            case TYPE_DATE:
                return new ItemHolder(LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.list_item_date, parent, false));
            default:
                return null;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ItemHolder) {
            ItemHolder itemHolder = (ItemHolder) holder;
            Date date = mDates.get(getDateIndex(position));
            itemHolder.numberView.setText(date.getDay() + "");
            itemHolder.titleView.setText(date.getTitle());
        } else
            ((TextView) holder.itemView).setText(mHeadersNames.get(position));
    }

    private int getDateIndex(int position) {
        int index = position;
        for (Integer headerPosition : mHeadersPositions)
            if (headerPosition >= position)
                break;
            else index--;
        return index;
    }

    @Override
    public int getItemCount() {
        return mDates.size() + mHeadersPositions.size();
    }

    @Override
    public int getItemViewType(int position) {
        return mHeadersPositions.contains(position)
                ? TYPE_HEADER : TYPE_DATE;
    }

    protected class ItemHolder extends RecyclerView.ViewHolder {
        TextView titleView, numberView;

        public ItemHolder(final View itemView) {
            super(itemView);

            numberView = (TextView) itemView.findViewById(R.id.day_number);
            titleView = (TextView) itemView.findViewById(R.id.title);
            final View overflowView = itemView.findViewById(R.id.overflow);

            overflowView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mListener.onDateSelected(overflowView, mDates.get(getDateIndex(getAdapterPosition())));
                }
            });
        }
    }
}
