package br.ufjf.app.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import br.ufjf.app.model.noticias.Artigo;
import br.ufjf.dcc.pesquisa.R;

/**
 * Adapter para a lista de notícias
 */
public class NoticiasAdapter extends RecyclerView.Adapter<NoticiasAdapter.ItemHolder> {

    private final List<Artigo> artigos;
    private final OnArtigoSelecionadoListener listener;

    public NoticiasAdapter(List<Artigo> artigos, OnArtigoSelecionadoListener listener) {
        this.artigos = artigos;
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
        holder.titulo.setText(artigos.get(position).getTitulo());
    }

    @Override
    public int getItemCount() {
        return artigos.size();
    }

    /**
     * Responde aos cliques da lista
     */
    public interface OnArtigoSelecionadoListener {
        void onArtigoSelecionado(Artigo artigo);
    }

    protected class ItemHolder extends RecyclerView.ViewHolder {
        TextView titulo;

        public ItemHolder(View itemView) {
            super(itemView);
            titulo = (TextView) itemView.findViewById(R.id.titulo);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onArtigoSelecionado(artigos.get(getAdapterPosition()));
                }
            });
        }
    }
}
