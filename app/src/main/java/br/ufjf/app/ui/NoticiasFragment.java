package br.ufjf.app.ui;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import br.ufjf.app.model.noticias.Noticia;
import br.ufjf.app.ui.adapter.NoticiasAdapter;
import br.ufjf.dcc.pesquisa.R;

/**
 * Fragmento com a lista de notícias do feed
 */
public class NoticiasFragment extends Fragment {

    private RecyclerView recyclerView;
    /**
     * Referencia a Activity pai
     **/
    private Listener listener;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        listener = (Listener) activity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_lista, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.lista);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // Configura a lista
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(new NoticiasAdapter(listener.getNoticias(), listener));
    }

    /**
     * Interface que a Activity "pai" deve implementar para que o fragmento obtenho as notícias
     */
    public interface Listener extends OnItemSelecionadoListener<Noticia> {
        /**
         * Obtem as noticias do feed
         *
         * @return lista de artigos
         */
        List<Noticia> getNoticias();
    }
}