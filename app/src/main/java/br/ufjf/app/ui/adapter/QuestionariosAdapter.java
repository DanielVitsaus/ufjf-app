package br.ufjf.app.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Set;
import java.util.TreeSet;

import br.ufjf.app.model.DBRemoto;
import br.ufjf.app.model.questionario.Questionario;
import br.ufjf.dcc.pesquisa.R;

/**
 * Adapter para a lista de questionários
 * Created by Jorge Augusto da Silva Moreira on 22/05/2015.
 */
public class QuestionariosAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TIPO_CABECALHO = 0;
    private static final int TIPO_QUESTIONARIO = 1;

    private final Questionario[] questionarios;

    // Posições que são cabeçalhos
    private final Set<Integer> posicoesQuestionarios;

    private final OnQuestionarioClickListener listener;

    public QuestionariosAdapter(Questionario[] questionarios, OnQuestionarioClickListener listener) {
        this.questionarios = questionarios;
        this.listener = listener;

        // Verifica quais posições serão cabeçalhos, com base na visibilidade dos questionários
        posicoesQuestionarios = new TreeSet<>();
        posicoesQuestionarios.add(0);
        for (int i = 1; i < this.questionarios.length; i++)
            if (this.questionarios[i - 1].getVisibilidade() != this.questionarios[i].getVisibilidade())
                posicoesQuestionarios.add(i + posicoesQuestionarios.size());
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TIPO_CABECALHO)
            return new RecyclerView.ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.list_header, parent, false)) {
            };
        else
            return new QuestionarioHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_two_lines, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof QuestionarioHolder) {
            int indiceQuestionario = getIndiceQuestionario(position);
            QuestionarioHolder questionarioHolder = (QuestionarioHolder) holder;
            questionarioHolder.titulo.setText(questionarios[indiceQuestionario].getTitulo());
            questionarioHolder.descricao.setText(questionarios[indiceQuestionario].getDescricao());
        } else {
            int visibility = questionarios[getIndiceQuestionario(position + 1)].getVisibilidade();
            String header;
            switch (visibility) {
                case DBRemoto.VisibilidadeQuestionario.CLASSE:
                    header = "Turma";
                    break;
                case DBRemoto.VisibilidadeQuestionario.ASSUNTO:
                    header = "Disciplina";
                    break;
                case DBRemoto.VisibilidadeQuestionario.CURSO:
                    header = "Curso";
                    break;
                case DBRemoto.VisibilidadeQuestionario.DEPARTAMENTO:
                    header = "Departamento";
                    break;
                default:
                    header = "Público";
            }
            ((TextView) holder.itemView).setText(header);
        }
    }

    private int getIndiceQuestionario(int position) {
        int indice = position;
        for (Integer posicaoCabecalho : posicoesQuestionarios)
            if (posicaoCabecalho >= position)
                break;
            else indice--;
        return indice;
    }

    @Override
    public int getItemCount() {
        if (questionarios != null && questionarios.length > 0)
            return posicoesQuestionarios.size() + questionarios.length;
        else return 0;
    }

    @Override
    public int getItemViewType(int position) {
        return posicoesQuestionarios.contains(position) ? TIPO_CABECALHO : TIPO_QUESTIONARIO;
    }

    public interface OnQuestionarioClickListener {
        void onQuestionarioClick(Questionario questionario);
    }

    protected class QuestionarioHolder extends RecyclerView.ViewHolder {
        TextView titulo, descricao;

        public QuestionarioHolder(View itemView) {
            super(itemView);
            titulo = (TextView) itemView.findViewById(R.id.title);
            descricao = (TextView) itemView.findViewById(R.id.description);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onQuestionarioClick(questionarios[getIndiceQuestionario(getAdapterPosition())]);
                }
            });
        }
    }
}
