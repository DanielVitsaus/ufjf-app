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

import br.ufjf.app.model.Data;
import br.ufjf.app.ui.adapter.MesAdapter;
import br.ufjf.dcc.pesquisa.R;

/**
 * Exibe a grade de um mês
 * Created by Jorge Augusto da Silva Moreira on 24/06/2015.
 */
public class MesFragment extends Fragment {
    private static final String ARG_MES = "mes";

    private RecyclerView recyclerView;
    private Listener listener;

    /**
     * Prepara um novo fragmento para o mês informado
     * @param mes indice do mês
     * @return
     */
    public static MesFragment obterNovo(int mes) {
        MesFragment fragment = new MesFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_MES, mes);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        listener = (Listener) activity;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_calendar_month, container, false);

        recyclerView = (RecyclerView) view;

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        int month = getArguments().getInt(ARG_MES);

        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 7));
        recyclerView.setAdapter(new MesAdapter(month, listener.getDatas(month), new MesAdapter.OnDiaClickListener() {
            @Override
            public void onDiaClick(Data data) {
                listener.onDiaClick(data);
            }
        }));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        listener = null;
    }

    /**
     * Interface que a Activity "pai" deve implementar
     */
    public interface Listener {
        List<Data> getDatas(int month);

        void onDiaClick(Data data);
    }
}
