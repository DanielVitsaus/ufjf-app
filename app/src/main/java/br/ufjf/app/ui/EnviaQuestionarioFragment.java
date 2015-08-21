package br.ufjf.app.ui;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import br.ufjf.dcc.pesquisa.R;

/**
 * Fragmento exibido ao final do qeustionário para enviar as respostas
 * Created by Jorge Augusto da Silva Moreira on 17/06/2015.
 */
public class EnviaQuestionarioFragment extends Fragment {
    private Listener listener;
    private TextView viewMensagem;
    private Button botaoEnviar;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        listener = (Listener) activity;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_enviar_quest, container, false);

        viewMensagem = (TextView) view.findViewById(R.id.texto);
        botaoEnviar = (Button) view.findViewById(R.id.enviar);

        botaoEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.enviarRespostas();
            }
        });

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        if (listener.prontoParaEnviar()) {
            viewMensagem.setText(R.string.all_questions_answered);
            botaoEnviar.setEnabled(true);
        } else {
            viewMensagem.setText(R.string.not_all_questions_answered);
            botaoEnviar.setEnabled(false);
        }
    }

    /**
     * Activity "pai" deve implementar
     */
    public interface Listener {
        /**
         * Verifica se o aluno ja pode enviar suas respostas
         * @return
         */
        boolean prontoParaEnviar();

        /**
         * Inicia o envio das respostas
         */
        void enviarRespostas();
    }
}
