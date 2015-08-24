package br.ufjf.app.ui.pergunta;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import br.ufjf.app.model.questionario.Pergunta;
import br.ufjf.app.model.questionario.PerguntaOpcao;
import br.ufjf.app.model.questionario.Resposta;
import br.ufjf.app.model.questionario.RespostaOpcao;
import br.ufjf.app.ui.adapter.OpcoesAdapter;
import br.ufjf.dcc.pesquisa.R;

/**
 * Exibe uma pergunta de múltipla escolha
 * Created by Jorge Augusto da Silva Moreira on 22/05/2015.
 */
public class MultiplaEscolhaFragment extends br.ufjf.app.ui.pergunta.PerguntaFragment {
    private PerguntaOpcao pergunta;
    private RespostaOpcao resposta;
    private RecyclerView recyclerView;
    private OpcoesAdapter adapter;

    public static MultiplaEscolhaFragment obterNovo(int questionIndex) {
        MultiplaEscolhaFragment fragment = new MultiplaEscolhaFragment();
        obterNovo(fragment, questionIndex);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pergunta = (PerguntaOpcao) listener.getPergunta(getIndicePergunta());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        recyclerView = (RecyclerView) view.findViewById(R.id.opcoes);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        adapter = new OpcoesAdapter(pergunta.isEscolhaUnica(), pergunta.getOpcoes(), new OpcoesAdapter.OnSelectionChangedListener() {
            @Override
            public void onSelectionChanged(boolean[] selected) {
                if (resposta == null)
                    resposta = new RespostaOpcao(selected);
                else
                    resposta.setOpcoes(selected);
            }
        });

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_q_multi_esc;
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
        adapter.notifySelectionChanged(((RespostaOpcao) resposta).getOpcoes());
    }
}
