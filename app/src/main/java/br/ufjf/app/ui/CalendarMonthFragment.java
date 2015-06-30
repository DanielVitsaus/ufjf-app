package br.ufjf.app.ui;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import br.ufjf.app.model.Date;
import br.ufjf.app.ui.adapter.MonthAdapter;
import br.ufjf.dcc.pesquisa.R;

/**
 * Created by Jorge Augusto da Silva Moreira on 24/06/2015.
 */
public class CalendarMonthFragment extends Fragment {
    private static final String ARG_MONTH = "month";

    private RecyclerView mRecyclerView;
    private Listener mListener;

    public static CalendarMonthFragment newInstance(int month) {
        CalendarMonthFragment fragment = new CalendarMonthFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_MONTH, month);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mListener = (Listener) activity;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_calendar_month, container, false);

        mRecyclerView = (RecyclerView) view;

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        int month = getArguments().getInt(ARG_MONTH);

        mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 7));
        mRecyclerView.setAdapter(new MonthAdapter(month, mListener.getDates(month)));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mListener = null;
    }

    public interface Listener {
        List<Date> getDates(int month);
    }
}
