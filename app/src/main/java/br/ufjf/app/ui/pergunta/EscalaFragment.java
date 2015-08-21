package br.ufjf.app.ui.pergunta;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;

import br.ufjf.app.model.questionario.Pergunta;
import br.ufjf.app.model.questionario.PerguntaEscala;
import br.ufjf.app.model.questionario.Resposta;
import br.ufjf.app.model.questionario.RespostaValor;
import br.ufjf.dcc.pesquisa.R;

/**
 * Exibe uma pergunta de escala
 * Created by Jorge Augusto da Silva Moreira on 22/05/2015.
 */
public class EscalaFragment extends PerguntaFragment {
    private PerguntaEscala pergunta;
    private RespostaValor resposta;
    private Spinner spinner;

    public static EscalaFragment obterNovo(int questionIndex) {
        EscalaFragment fragment = new EscalaFragment();
        obterNovo(fragment, questionIndex);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pergunta = (PerguntaEscala) listener.getPergunta(getIndicePergunta());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);

        spinner = (Spinner) view.findViewById(R.id.escala);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (resposta == null)
                    resposta = new RespostaValor(i, true);
                else
                    resposta.setValor(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        Integer[] values = new Integer[pergunta.getMax() - pergunta.getMin()];
        for (int i = 0; i < values.length; i++)
            values[i] = i + pergunta.getMin();

        SpinnerAdapter spinnerAdapter = new ArrayAdapter<>(spinner.getContext(), android.R.layout.simple_spinner_dropdown_item, values);
        spinner.setAdapter(spinnerAdapter);

        return view;
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_q_escala;
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
        spinner.setSelection(((RespostaValor) resposta).getValor());
    }
}
