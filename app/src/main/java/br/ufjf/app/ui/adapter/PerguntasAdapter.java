package br.ufjf.app.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import br.ufjf.app.model.questionario.Pergunta;
import br.ufjf.app.model.questionario.PerguntaEscala;
import br.ufjf.app.model.questionario.PerguntaOpcao;
import br.ufjf.app.model.questionario.PerguntaTexto;
import br.ufjf.app.ui.EnviaQuestionarioFragment;
import br.ufjf.app.ui.pergunta.EscalaFragment;
import br.ufjf.app.ui.pergunta.MultiplaEscolhaFragment;
import br.ufjf.app.ui.pergunta.PerguntaTextoFragment;
import br.ufjf.app.ui.pergunta.PerguntaUnicaEscolhaFragment;

/**
 * Adapter para o ViewPager de perguntas
 * Created by Jorge Augusto da Silva Moreira on 20/05/2015.
 */
public class PerguntasAdapter extends FragmentPagerAdapter {

    private Pergunta[] perguntas;

    public PerguntasAdapter(FragmentManager fragmentManager, Pergunta[] perguntas) {
        super(fragmentManager);
        this.perguntas = perguntas;
    }

    @Override
    public Fragment getItem(int position) {
        if (position >= perguntas.length)
            return new EnviaQuestionarioFragment();
        else {
            Pergunta pergunta = perguntas[position];

            // Decide qual tipo de fragmento sera utilizado
            if (pergunta instanceof PerguntaTexto)
                return PerguntaTextoFragment.obterNovo(position);
            else if (pergunta instanceof PerguntaEscala)
                return EscalaFragment.obterNovo(position);
            else if (((PerguntaOpcao) pergunta).isEscolhaUnica())
                return PerguntaUnicaEscolhaFragment.obterNovo(position);
            else
                return MultiplaEscolhaFragment.obterNovo(position);
        }
    }

    @Override
    public int getCount() {
        return perguntas.length + 1;
    }

    public Pergunta getPergunta(int posicao) {
        return perguntas[posicao];
    }
}
