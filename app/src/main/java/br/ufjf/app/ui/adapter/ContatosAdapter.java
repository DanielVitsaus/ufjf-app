package br.ufjf.app.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import br.ufjf.app.model.Contato;
import br.ufjf.app.ui.OnItemSelecionadoListener;
import br.ufjf.dcc.pesquisa.R;

/**
 * Created by cgco on 18/08/15.
 */
public class ContatosAdapter extends RecyclerView.Adapter<ContatosAdapter.ItemHolder> {
    private final List<Contato> contatos;
    private final OnItemSelecionadoListener<Contato> listener;

    public ContatosAdapter(List<Contato> contatos, OnItemSelecionadoListener<Contato> listener) {
        this.contatos = contatos;
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
        holder.titulo.setText(contatos.get(position).getNome());
    }

    @Override
    public int getItemCount() {
        return contatos.size();
    }

    protected class ItemHolder extends RecyclerView.ViewHolder {
        TextView titulo;

        public ItemHolder(View itemView) {
            super(itemView);
            titulo = (TextView) itemView.findViewById(R.id.titulo);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemSelecionado(contatos.get(getAdapterPosition()));
                }
            });
        }
    }
}
