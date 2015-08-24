package br.ufjf.app.ui;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import br.ufjf.app.async.EnviarRespostasTask;
import br.ufjf.app.async.ObterQuestionarioTask;
import br.ufjf.app.model.questionario.Pergunta;
import br.ufjf.app.model.questionario.Resposta;
import br.ufjf.app.model.questionario.RespostaQuestionario;
import br.ufjf.app.ui.adapter.PerguntasAdapter;
import br.ufjf.app.ui.pergunta.PerguntaFragment;
import br.ufjf.app.util.AutenticacaoHelper;
import br.ufjf.dcc.pesquisa.R;

/**
 * Exibe as perguntas de um questionario
 * Created by Jorge Augusto da mustSilva Moreira on 20/05/2015.
 */
public class QuestionarioActivity extends AppCompatActivity implements PerguntaFragment.Listener, EnviaQuestionarioFragment.Listener {
    public static final String ARG_QUESTIONARIO = "questionario";

    private ViewPager viewPager;
    private PerguntasAdapter adapter;
    private RespostaQuestionario respostaQuestionario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quest);

        viewPager = (ViewPager) findViewById(R.id.pager);

        // Carrega o questionário a partir do id passado via Intent
        new ObterQuestionarioTask(new ObterQuestionarioTask.Callback() {

            @Override
            public void onFinish(br.ufjf.app.model.questionario.Questionario questionario) {
                if (questionario != null) {
                    // Questionario carregado
                    Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
                    toolbar.setTitle(questionario.getTitulo());
                    toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);
                    toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            finish();
                        }
                    });

                    try {
                        respostaQuestionario = new RespostaQuestionario(AutenticacaoHelper.obterAluno(QuestionarioActivity.this).getId(), questionario.getId(), questionario.getPerguntas().length);
                        adapter = new PerguntasAdapter(getSupportFragmentManager(), questionario.getPerguntas());
                        viewPager.setAdapter(adapter);
                    } catch (AutenticacaoHelper.AlunoNaoAutenticado alunoNaoAutenticado) {
                        //todo se estudante não estiver logado, tratar aqui
                        alunoNaoAutenticado.printStackTrace();
                    }
                }
            }
        }).execute(getIntent().getStringExtra(ARG_QUESTIONARIO));
    }

    @Override
    public Pergunta getPergunta(int indice) {
        return adapter.getPergunta(indice);
    }

    @Override
    public Resposta getResposta(int indice) {
        return respostaQuestionario.getRespostas()[indice];
    }

    /**
     * Navega ate a pergunta anterior
     * @param view Botao
     */
    public void anterior(View view) {
        viewPager.setCurrentItem(viewPager.getCurrentItem() - 1);
    }

    /**
     * Navega ate a proxima pergunta
     * @param view Botao
     */
    public void proxima(View view) {
        viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
    }

    @Override
    public void registrarResposta(int perguntaIndice, Resposta resposta) {
        respostaQuestionario.getRespostas()[perguntaIndice] = resposta;
    }

    @Override
    public boolean prontoParaEnviar() {
        for (Resposta resposta : respostaQuestionario.getRespostas())
            if (resposta == null)
                return false;
        return true;
    }

    @Override
    public void enviarRespostas() {
        new EnviarRespostasTask(new EnviarRespostasTask.Callback() {
            @Override
            public void onFinish(boolean success) {
                if (success) {
                    Toast.makeText(QuestionarioActivity.this, R.string.answer_sent, Toast.LENGTH_SHORT).show();
                    finish();
                } else
                    Toast.makeText(QuestionarioActivity.this, R.string.answer_not_sent, Toast.LENGTH_LONG).show();
            }
        }).execute(respostaQuestionario);
    }
}
