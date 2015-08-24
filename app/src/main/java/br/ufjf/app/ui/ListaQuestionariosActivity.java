package br.ufjf.app.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import br.ufjf.app.async.ObterQuestionariosTask;
import br.ufjf.app.model.questionario.Questionario;
import br.ufjf.app.ui.adapter.QuestionariosAdapter;
import br.ufjf.dcc.pesquisa.R;

/**
 * Exibe a lista de questionários disponìveis para o estudante responder
 * Created by Jorge Augusto da Silva Moreira on 20/05/2015.
 */
public class ListaQuestionariosActivity extends DrawerActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_drawer_list);
        inicializarToolbar();

        final RecyclerView recyclerView = (RecyclerView) findViewById(R.id.lista);

        // COnfigura a lista
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);

        // Carrega a lista de quesionarios
        new ObterQuestionariosTask(new ObterQuestionariosTask.Callback() {
            @Override
            public void onFinish(Questionario[] questionarios) {
                recyclerView.setAdapter(new QuestionariosAdapter(questionarios, new OnItemSelecionadoListener<Questionario>() {
                    @Override
                    public void onItemSelecionado(Questionario questionario) {
                        startActivity(new Intent(ListaQuestionariosActivity.this, QuestionarioActivity.class)
                                .putExtra(QuestionarioActivity.ARG_QUESTIONARIO, questionario.getId()));
                    }
                }));
            }
        }).execute();
    }
}
