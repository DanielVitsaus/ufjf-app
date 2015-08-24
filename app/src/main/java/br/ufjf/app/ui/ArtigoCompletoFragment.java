package br.ufjf.app.ui;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.TextView;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import br.ufjf.app.util.WebHelper;
import br.ufjf.dcc.pesquisa.R;

/**
 * Exibe o texto completo de uma notícia, lendo o HTML da página e retirando apenas o texto principal.
 */
public class ArtigoCompletoFragment extends Fragment {

    private static final String ARG_URL = "url";
    private static final String ARG_TITULO = "titulo";

    private WebView webView;
    private Listener listener;

    /**
     * Prepara um novo fragmento para a url informada
     *
     * @param url    Endereço da pagina da noticia
     * @param titulo Titulo da noticia
     * @return
     */
    public static ArtigoCompletoFragment obterNovo(String url, String titulo) {
        Bundle args = new Bundle();
        args.putString(ARG_URL, url);
        args.putString(ARG_TITULO, titulo);

        ArtigoCompletoFragment articleFragment = new ArtigoCompletoFragment();
        articleFragment.setArguments(args);

        return articleFragment;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        listener = (Listener) activity;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_art_completo, container, false);
        webView = ((WebView) view.findViewById(R.id.web_view));
        ((TextView) view.findViewById(R.id.titulo)).setText(getArguments().getString(ARG_TITULO));
        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.full_article, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.open_in_browser) {
            // Abre a página no navegador
            getActivity().startActivity(
                    new Intent(Intent.ACTION_VIEW, Uri.parse(webView.getUrl())));
            return true;
        } else return false;
    }

    @Override
    public void onStart() {
        super.onStart();
        listener.onArtigoCompletoFragmentStart(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        listener.onArtigoCompletoFragmentStop();
    }

    public boolean voltar() {
        // Volta no historico se possível
        if (webView.canGoBack()) {
            webView.goBack();
            return true;
        } else
            return false;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        String url = getArguments().getString(ARG_URL);
        new LeitorNoticiaTask().execute(url);
    }

    /**
     * Activity "pai" deve implementar
     */
    public interface Listener {
        /**
         * Chamado quando o ArtigoCompletoFragment e iniciado
         * @param fragment ArtigoCompletoFragment atual
         */
        void onArtigoCompletoFragmentStart(ArtigoCompletoFragment fragment);

        /**
         * Chamado quando o ArtigoCompletoFragment e parado
         */
        void onArtigoCompletoFragmentStop();
    }

    /**
     * Efetua o carregamento assincrono do texto de uma noticia
     */
    private class LeitorNoticiaTask extends AsyncTask<String, Void, String> {
        private String url;
        private ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(getActivity());
            progressDialog.setMessage(getString(R.string.loading_));
            progressDialog.setIndeterminate(true);
            progressDialog.setCancelable(false);
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... urls) {
            url = urls[0];

            // Lê a página
            String html = WebHelper.obterConteudo(url);

            if (html != null) {
                // Extrai texto principal
                String contentRegex = "<div class=\"entry-content\">(.*?)<\\/div><!-- .entry-content -->";
                String sociableRegex = "<!-- Start Sociable -->(.*?)<!-- End Sociable -->";
                Pattern p = Pattern.compile(contentRegex, Pattern.MULTILINE);

                Matcher m = p.matcher(html);
                if (m.find()) {
                    return m.group(1).replaceFirst(sociableRegex, "");
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(String data) {
            progressDialog.hide();

            // Exibe o conteúdo
            webView.loadDataWithBaseURL("http://www.ufjf.br/secom", data, "text / html", "UTF-8", url);
        }
    }
}