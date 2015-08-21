package br.ufjf.app.ui;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;

import org.xml.sax.SAXException;

import java.io.IOException;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import br.ufjf.app.model.noticias.Artigo;
import br.ufjf.app.model.noticias.Feed;
import br.ufjf.app.ui.ArtigoCompletoFragment.Listener;
import br.ufjf.app.util.WebHelper;
import br.ufjf.dcc.pesquisa.R;

/**
 * Gerencia a navegação das notícias
 * Created by Jorge Augusto da Silva Moreira on 20/05/2015.
 */
public class NoticiasActivity extends DrawerActivity implements NoticiasFragment.Listener, ArtigoFragment.OnArtigoCompletoClickListener, Listener {
    private static final String TAG = "NoticiasActivity";
    private Feed feed;
    // Caso != null indica se o texto completo de uma noticia esta sendo exibido
    private ArtigoCompletoFragment artigoCompletoFragment;
    private AsyncTask<Void, Void, Void> feedTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.v(TAG, "onCreate");
        setContentView(R.layout.activity_drawer);
        inicializarToolbar();
        setTitle(R.string.news);
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.v(TAG, "onResume");
        if (feedTask == null || feedTask.isCancelled())
            feedTask = new FeedTask().execute();
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.v(TAG, "onPause");
        if (feedTask != null)
            feedTask.cancel(true);
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.v(TAG, "onStop");
        if (feedTask != null)
            feedTask.cancel(true);
    }

    @Override
    public void onArtigoSelecionado(Artigo artigo) {
        // ABre oresumo da noticia
        Log.v(TAG, "Abrindo noticia");
        ArtigoFragment fragment = ArtigoFragment.obterNovo(artigo);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, fragment)
                .addToBackStack(null)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .commit();
    }

    @Override
    public void onBackPressed() {
        if (artigoCompletoFragment == null || !artigoCompletoFragment.voltar())
            super.onBackPressed();
    }

    @Override
    public List<Artigo> getArtigos() {
        if (feed != null)
            return feed.getArtigos();
        else return null;
    }

    @Override
    public void abrirArtigoCompleto(Artigo artigo) {
        // Mostra o fragmento com o texto completo
        Log.v(TAG, "Abrindo texto completo");
        ArtigoCompletoFragment fragment = ArtigoCompletoFragment.obterNovo(artigo.getLink(), artigo.getTitulo());
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, fragment)
                .addToBackStack(null)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .commit();
    }

    @Override
    public void onArtigoCompletoFragmentStart(ArtigoCompletoFragment fragment) {
        artigoCompletoFragment = fragment;
    }

    @Override
    public void onArtigoCompletoFragmentStop() {
        artigoCompletoFragment = null;
    }

    private class FeedTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            Log.v(TAG, "Iniciando leitura do feed");
            try {
                // todo substituir pelo feed da UFJF
                feed = WebHelper.obterFeed("http://revistagalileu.globo.com/rss/ultimas/feed.xml").getFeed();
            } catch (IOException | SAXException | ParserConfigurationException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            // Mostra o fragmento com a lista
            if (feed != null) {
                Log.v(TAG, "Feed carregado");
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.container, new NoticiasFragment())
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                        .commit();
            } else {
                Log.v(TAG, "Feed não carregado");
            }
        }
    }
}
