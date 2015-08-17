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

    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_surveys_exp);
        inicializarToolbar();

        recyclerView = (RecyclerView) findViewById(R.id.list_surveys);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);

        new ObterQuestionariosTask(new ObterQuestionariosTask.Callback() {
            @Override
            public void onFinish(Questionario[] questionarios) {
                recyclerView.setAdapter(new QuestionariosAdapter(questionarios, new QuestionariosAdapter.OnQuestionarioClickListener() {
                    @Override
                    public void onQuestionarioClick(Questionario questionario) {
                        startActivity(new Intent(ListaQuestionariosActivity.this, QuestionarioActivity.class)
                                .putExtra(QuestionarioActivity.ARG_QUESTIONARIO, questionario.getId()));
                    }
                }));
            }
        }).execute();
    }
}
