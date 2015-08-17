package br.ufjf.app.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import br.ufjf.dcc.pesquisa.R;

/**
 * Adapter para a lista de opções de uma pergunta
 * Created by Jorge Augusto da Silva Moreira on 22/05/2015.
 */
public class OpcoesAdapter extends RecyclerView.Adapter<OpcoesAdapter.OpcaoHolder> {

    private final boolean escolhaUnica;
    private final String[] opcoes;
    private final OnSelectionChangedListener listener;
    private boolean[] selecionadas;

    public OpcoesAdapter(boolean singleChoice, String[] options, OnSelectionChangedListener listener) {
        escolhaUnica = singleChoice;
        opcoes = options;
        this.listener = listener;
        selecionadas = new boolean[opcoes.length];
    }

    public void notifySelectionChanged(boolean[] selected) {
        selecionadas = selected;
        notifyDataSetChanged();
    }

    @Override
    public OpcaoHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        int layout;
        if (escolhaUnica)
            layout = R.layout.list_item_single_choice;
        else
            layout = R.layout.list_item_multiple_choice;

        return new OpcaoHolder(LayoutInflater.from(parent.getContext()).inflate(layout, parent, false));
    }

    @Override
    public int getItemCount() {
        return opcoes.length;
    }

    @Override
    public void onBindViewHolder(OpcaoHolder holder, int position) {
        holder.opcao.setText(opcoes[position]);
        holder.opcao.setChecked(selecionadas[position]);
    }

    public interface OnSelectionChangedListener {
        void onSelectionChanged(boolean[] selected);
    }

    protected class OpcaoHolder extends RecyclerView.ViewHolder {
        CompoundButton opcao;

        public OpcaoHolder(View itemView) {
            super(itemView);
            opcao = (CompoundButton) itemView.findViewById(R.id.choice);

            opcao.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean checked) {
                    selecionadas[getAdapterPosition()] = checked;
                    listener.onSelectionChanged(selecionadas);
                }
            });
        }
    }

}
