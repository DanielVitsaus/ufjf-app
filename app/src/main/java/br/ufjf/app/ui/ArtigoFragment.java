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

import br.ufjf.app.model.noticias.Artigo;
import br.ufjf.dcc.pesquisa.R;

/**
 * Exibe o resumo de uma notícia
 */
public class ArtigoFragment extends Fragment {
    private static final String ARG_ARTIGO = "artigo";

    private Artigo artigo;
    private OnArtigoCompletoClickListener listener;

    /** Prepara um novo fragmento com o artigo informado **/
    public static ArtigoFragment obterNovo(Artigo artigo) {
        Bundle args = new Bundle();
        args.putParcelable(ARG_ARTIGO, artigo);

        ArtigoFragment artigoFragment = new ArtigoFragment();
        artigoFragment.setArguments(args);

        return artigoFragment;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        listener = (OnArtigoCompletoClickListener) activity;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        artigo = getArguments().getParcelable(ARG_ARTIGO);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_artigo, container, false);

        TextView title = ((TextView) view.findViewById(R.id.titulo));
        TextView content = ((TextView) view.findViewById(R.id.texto));
        Button fullArticleButton = (Button) view.findViewById(R.id.btn_art_compl);

        title.setText(artigo.getTitle());
        content.setText(Html.fromHtml(artigo.getContent()));
        content.setMovementMethod(LinkMovementMethod.getInstance());
        fullArticleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.abrirArtigoCompleto(artigo.getLink());
            }
        });

        return view;
    }

    public interface OnArtigoCompletoClickListener {
        /**
         * Solicita a abertura de uma notícia
         * @param url endereço da notícia
         */
        void abrirArtigoCompleto(String url);
    }
}