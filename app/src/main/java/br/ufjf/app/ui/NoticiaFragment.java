package br.ufjf.app.ui;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import br.ufjf.app.model.noticias.Noticia;
import br.ufjf.dcc.pesquisa.R;

/**
 * Exibe o resumo de uma notícia
 */
public class NoticiaFragment extends Fragment {
    private static final String ARG_ARTIGO = "noticia";

    /**
     * Noticia exibida
     */
    private Noticia noticia;
    /**
     * Objeto responsavel por lidar com o botao de texto completo
     */
    private OnArtigoCompletoClickListener listener;

    /**
     * Prepara um novo fragmento
     * @param noticia Noticia a ser exibida
     **/
    public static NoticiaFragment obterNovo(Noticia noticia) {
        Bundle args = new Bundle();
        args.putParcelable(ARG_ARTIGO, noticia);

        NoticiaFragment noticiaFragment = new NoticiaFragment();
        noticiaFragment.setArguments(args);

        return noticiaFragment;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        listener = (OnArtigoCompletoClickListener) activity;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        noticia = getArguments().getParcelable(ARG_ARTIGO);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_artigo, container, false);

        TextView title = ((TextView) view.findViewById(R.id.titulo));
        TextView content = ((TextView) view.findViewById(R.id.texto));
        Button fullArticleButton = (Button) view.findViewById(R.id.btn_art_compl);

        title.setText(noticia.getTitulo());
        content.setText(Html.fromHtml(noticia.getConteudo()));
        content.setMovementMethod(LinkMovementMethod.getInstance());
        fullArticleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.abrirArtigoCompleto(noticia);
            }
        });

        return view;
    }

    /**
     * Objeto responsavel por lidar com os eventos de clique do botao para abrir o texto completo
     */
    public interface OnArtigoCompletoClickListener {
        /**
         * Solicita a abertura do texto completo de uma notícia
         * @param noticia noticia
         */
        void abrirArtigoCompleto(Noticia noticia);
    }
}