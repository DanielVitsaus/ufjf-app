package br.ufjf.app.ui.pergunta;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import br.ufjf.app.model.questionario.Pergunta;
import br.ufjf.app.model.questionario.PerguntaTexto;
import br.ufjf.app.model.questionario.Resposta;
import br.ufjf.app.model.questionario.RespostaTexto;
import br.ufjf.dcc.pesquisa.R;

/**
 * Exibe uma pergunta de texto
 * Created by Jorge Augusto da Silva Moreira on 22/05/2015.
 */
public class PerguntaTextoFragment extends PerguntaFragment {
    private PerguntaTexto pergunta;
    private RespostaTexto resposta;
    private EditText campoResposta;

    public static PerguntaTextoFragment obterNovo(int questionIndex) {
        PerguntaTextoFragment fragment = new PerguntaTextoFragment();
        obterNovo(fragment, questionIndex);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pergunta = (PerguntaTexto) listener.getPergunta(getQuestionIndex());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);

        campoResposta = (EditText) view.findViewById(R.id.resposta);
        if (pergunta.isUmaLinha())
            campoResposta.setSingleLine();
        else {
            campoResposta.setHorizontalScrollBarEnabled(false);
            campoResposta.setLines(7);
            campoResposta.setMaxLines(7);
            campoResposta.setEms(10);
        }

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_q_texto;
    }

    @Override
    protected Pergunta getPergunta() {
        return pergunta;
    }

    @Override
    protected Resposta getResposta() {
        String text = campoResposta.getText().toString();
        if (resposta == null)
            resposta = new RespostaTexto(text);
        else
            resposta.setTexto(text);
        return resposta;
    }

    @Override
    protected void atualizarUI(Resposta resposta) {
        campoResposta.setText(((RespostaTexto) resposta).getTexto());
    }
}
