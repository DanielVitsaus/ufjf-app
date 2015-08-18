package br.ufjf.app.ui.pergunta;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import br.ufjf.app.model.questionario.Pergunta;
import br.ufjf.app.model.questionario.PerguntaOpcao;
import br.ufjf.app.model.questionario.Resposta;
import br.ufjf.app.model.questionario.RespostaValor;
import br.ufjf.dcc.pesquisa.R;

/** Exibe uma pergunta de única escolha
 * Created by Jorge Augusto da Silva Moreira on 22/05/2015.
 */
public class PerguntaUnicaEscolhaFragment extends PerguntaFragment {
    private PerguntaOpcao pergunta;
    private RespostaValor resposta;
    private RadioGroup radioGroup;

    public static PerguntaUnicaEscolhaFragment obterNovo(int questionIndex) {
        PerguntaUnicaEscolhaFragment fragment = new PerguntaUnicaEscolhaFragment();
        obterNovo(fragment, questionIndex);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pergunta = (PerguntaOpcao) listener.getPergunta(getQuestionIndex());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);

        radioGroup = (RadioGroup) view.findViewById(R.id.opcoes);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int id) {
                //todo salvar posição, não id do View
                if (resposta == null)
                    resposta = new RespostaValor(id, false);
                else
                    resposta.setValor(id);
            }
        });

        for (String option : pergunta.getOpcoes()) {
            RadioButton radioButton = new RadioButton(radioGroup.getContext());
            radioButton.setText(option);
            radioGroup.addView(radioButton);
        }

        return view;
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_q_esc_unica;
    }

    @Override
    protected Pergunta getPergunta() {
        return pergunta;
    }

    @Override
    protected Resposta getResposta() {
        return resposta;
    }

    @Override
    protected void atualizarUI(Resposta resposta) {
        int indice = (((RespostaValor) resposta).getValor());
        RadioButton radioButton = (RadioButton) radioGroup.getChildAt(indice);
        if (radioButton != null) //todo retirar if após corrigir indices
            radioButton.setChecked(true);
    }
}
