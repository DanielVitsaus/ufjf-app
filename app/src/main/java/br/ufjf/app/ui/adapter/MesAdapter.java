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

import br.ufjf.app.model.Data;
import br.ufjf.dcc.pesquisa.R;

/**
 * Adapter apara a grade de dias de um mês
 * Created by Jorge Augusto da Silva Moreira on 25/07/2014.
 */
public class MesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    // Dia da semana correspondente ao primeiro dia do mês
    private final int diaSemana;
    // Quantidade de dias no mês
    private final int diasNoMes;
    // Posição do dia atual na grade
    private final int posicaoHoje;
    // Datas importantes do calendário a serem destacadas
    private final HashMap<Integer, Data> datas;

    private final OnDiaClickListener listener;

    public MesAdapter(int month, List<Data> datas, OnDiaClickListener listener) {
        this.listener = listener;

        Calendar calendar = Calendar.getInstance();
        if (calendar.get(Calendar.MONTH) == month) {
            diaSemana = calendar.get(Calendar.DAY_OF_WEEK);
            posicaoHoje = getPosicaoDoDia(calendar.get(Calendar.DAY_OF_MONTH));
        } else {
            calendar.set(Calendar.MONTH, month);
            posicaoHoje = -1;
            diaSemana = calendar.get(Calendar.DAY_OF_WEEK);
        }

        diasNoMes = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);

        this.datas = new HashMap<>();
        if (datas != null)
            for (Data data : datas)
                this.datas.put(getPosicaoDoDia(data.getDiaInicio()), data);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new RecyclerView.ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_calendar_day, parent, false)){};
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        TextView viewDia = (TextView) holder.itemView;

        int dia = getDiaDaPosicao(position);
        if (dia > 0) {
            viewDia.setText(dia + "");

            // Negrito, se for o dia atual
            if (posicaoHoje == position)
                viewDia.setTypeface(null, Typeface.BOLD);
            else
                viewDia.setTypeface(null, Typeface.NORMAL);

            // Destaca, caso seja uma data importante
            Data data = datas.get(position);
            if (data != null) {
                viewDia.setTextColor(viewDia.getContext().getResources().getColor(R.color.primary));
                viewDia.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        listener.onDiaClick(datas.get(position));
                    }
                });
            } else {
                viewDia.setTextColor(viewDia.getContext().getResources().getColor(R.color.text_secondary));
                viewDia.setOnClickListener(null);
            }
        } else
            viewDia.setText("");
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return diasNoMes + diaSemana - 1;
    }

    private int getPosicaoDoDia(int dia) {
        return dia + diaSemana - 2;
    }

    private int getDiaDaPosicao(int posicao) {
        return posicao - diaSemana + 2;
    }

    public interface OnDiaClickListener {
        void onDiaClick(Data data);
    }
}
