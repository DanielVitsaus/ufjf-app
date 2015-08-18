package br.ufjf.app.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import br.ufjf.app.model.CalendarioAcademico;
import br.ufjf.app.model.Data;
import br.ufjf.dcc.pesquisa.R;

/**
 * Adapter para a lista de datas importantes do calendário acadêmico
 */
public class DatasAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TIPO_CABECALHO = 0;
    private static final int TIPO_DATA = 1;

    private final List<Data> datas;
    // Posições que são cabeçalhos
    private final Map<Integer, Integer> posicoesCabecalhos;
    // Nomes dos cabeçalhos
    private final HashMap<Integer, String> nomesCabecalhos;
    private final OnDataSelecionadaListener listener;

    public DatasAdapter(CalendarioAcademico calendarioAcademico, List<String> monthNames, OnDataSelecionadaListener listener) {
        this.listener = listener;
        datas = new ArrayList<>();
        posicoesCabecalhos = new TreeMap<>();
        nomesCabecalhos = new HashMap<>();

        // Verifica quais posições serão cabeçalhos, com base no mês
        for (int i = 0; i < 12; i++) {
            List<Data> datas = calendarioAcademico.getDatasPorMes(i);
            if (datas != null) {
                posicoesCabecalhos.put(this.datas.size() + nomesCabecalhos.size(), i);
                nomesCabecalhos.put(this.datas.size() + nomesCabecalhos.size(), monthNames.get(i));
                this.datas.addAll(datas);
            }
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case TIPO_CABECALHO:
                return new RecyclerView.ViewHolder(LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.list_header, parent, false)) {
                };
            case TIPO_DATA:
                return new ItemHolder(LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.list_item_data, parent, false));
            default:
                return null;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ItemHolder) {
            ItemHolder itemHolder = (ItemHolder) holder;
            Data data = datas.get(getDateIndex(position));
            if (data.getDiaTermino() > 0)
                itemHolder.numero.setText(itemHolder.numero.getContext().getString(R.string.date_to_date, data.getDiaInicio(), data.getDiaTermino()));
            else
                itemHolder.numero.setText(data.getDiaInicio() + "");
            itemHolder.titulo.setText(data.getTitulo());
        } else
            ((TextView) holder.itemView).setText(nomesCabecalhos.get(position));
    }

    public int getPosicaoData(Data data) {
        //todo melhorar (não iterar na lista toda)
        for (int i = 0; i < datas.size(); i++)
            if (data.getDiaInicio() == datas.get(i).getDiaInicio()
                    && data.getMes() == datas.get(i).getMes()) {
                for (Integer posicaoCabecalho : posicoesCabecalhos.keySet()) {
                    if (posicaoCabecalho > i)
                        break;
                    i++;
                }
                return i;
            }
        return -1;
    }

    public int getMonthPosition(int month) {
        for (Integer posicaoCabecalho : posicoesCabecalhos.keySet())
            if (posicoesCabecalhos.get(posicaoCabecalho) == month)
                return posicaoCabecalho;
        return -1;
    }

    private int getDateIndex(int posicao) {
        int indice = posicao;
        for (Integer posicaoCabecalho : posicoesCabecalhos.keySet())
            if (posicaoCabecalho >= posicao)
                break;
            else indice--;
        return indice;
    }

    @Override
    public int getItemCount() {
        return datas.size() + posicoesCabecalhos.size();
    }

    @Override
    public int getItemViewType(int position) {
        return posicoesCabecalhos.containsKey(position)
                ? TIPO_CABECALHO : TIPO_DATA;
    }

    public interface OnDataSelecionadaListener {
        void onDataSelecionada(View overflowView, Data data);
    }

    protected class ItemHolder extends RecyclerView.ViewHolder {
        TextView titulo, numero;

        public ItemHolder(final View itemView) {
            super(itemView);

            numero = (TextView) itemView.findViewById(R.id.numero_dia);
            titulo = (TextView) itemView.findViewById(R.id.titulo);
            final View menu = itemView.findViewById(R.id.menu);

            menu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onDataSelecionada(menu, datas.get(getDateIndex(getAdapterPosition())));
                }
            });
        }
    }
}
