package br.ufjf.app.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import br.ufjf.app.model.noticias.Noticia;
import br.ufjf.app.ui.OnItemSelecionadoListener;
import br.ufjf.dcc.pesquisa.R;

/**
 * Adapter para a lista de notícias
 */
public class NoticiasAdapter extends RecyclerView.Adapter<NoticiasAdapter.ItemHolder> {

    private final List<Noticia> noticias;
    private final OnItemSelecionadoListener<Noticia> listener;

    public NoticiasAdapter(List<Noticia> noticias, OnItemSelecionadoListener<Noticia> listener) {
        this.noticias = noticias;
        this.listener = listener;
    }

    @Override
    public ItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_noticia, parent, false);
        return new ItemHolder(view);
    }

    @Override
    public void onBindViewHolder(ItemHolder holder, int position) {
        holder.titulo.setText(noticias.get(position).getTitulo());
    }

    @Override
    public int getItemCount() {
        return noticias.size();
    }

    protected class ItemHolder extends RecyclerView.ViewHolder {
        TextView titulo;

        public ItemHolder(View itemView) {
            super(itemView);
            titulo = (TextView) itemView.findViewById(R.id.titulo);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemSelecionado(noticias.get(getAdapterPosition()));
                }
            });
        }
    }
}
