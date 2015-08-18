package br.ufjf.app.ui.pergunta;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import br.ufjf.app.model.questionario.Pergunta;
import br.ufjf.app.model.questionario.Resposta;
import br.ufjf.dcc.pesquisa.R;

/**
 * Fragmento base para exibir uma pergunta
 * Created by Jorge Augusto da Silva Moreira on 20/05/2015.
 */
public abstract class PerguntaFragment extends Fragment {
    private static final String ARG_QUESTION_INDEX = "question";
    private static final String TAG = "PerguntaFragment";

    protected Listener listener;
    private int indicePergunta;

    public PerguntaFragment() {
        indicePergunta = -1;
    }

    protected static void obterNovo(PerguntaFragment perguntaFragment, int questionIndex) {
        Bundle args = new Bundle();
        args.putInt(ARG_QUESTION_INDEX, questionIndex);
        perguntaFragment.setArguments(args);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(ARG_QUESTION_INDEX, indicePergunta);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            listener = (Listener) activity;
        } catch (ClassCastException e) {
            Log.v(TAG, "Activity must implement PerguntaFragment.Listener");
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null)
            indicePergunta = savedInstanceState.getInt(ARG_QUESTION_INDEX);
        else
            indicePergunta = getQuestionIndex();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(getLayoutRes(), container, false);

        ((TextView) view.findViewById(R.id.titulo)).setText(getPergunta().getTitulo());

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        Resposta resposta = listener.getResposta(indicePergunta);
        if (resposta != null)
            atualizarUI(resposta);
    }

    @Override
    public void onPause() {
        super.onPause();
        listener.registrarResposta(indicePergunta, getResposta());
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }

    protected int getQuestionIndex() {
        if (indicePergunta == -1)
            indicePergunta = getArguments().getInt(ARG_QUESTION_INDEX);
        return indicePergunta;
    }

    protected abstract int getLayoutRes();

    protected abstract Pergunta getPergunta();

    protected abstract Resposta getResposta();

    protected abstract void atualizarUI(Resposta resposta);

    public interface Listener {
        Pergunta getPergunta(int indice);

        Resposta getResposta(int indice);

        void registrarResposta(int perguntaIndice, Resposta resposta);
    }
}
