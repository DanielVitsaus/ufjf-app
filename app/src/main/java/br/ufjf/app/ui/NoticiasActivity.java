package br.ufjf.app.ui;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;

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
    private Feed feed;
    // Caso != null indica se o texto completo de uma noticia esta sendo exibido
    private ArtigoCompletoFragment artigoCompletoFragment;
    private AsyncTask<Void, Void, Void> feedTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawer);
        inicializarToolbar();
        setTitle(R.string.news);

        // Carrega o feed
        feedTask = new FeedTask().execute();
    }

    @Override
    protected void onStop() {
        super.onStop();
        feedTask.cancel(true);
    }

    @Override
    public void onArtigoSelecionado(Artigo artigo) {
        // ABre oresumo da noticia
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
            try {
                feed = WebHelper.obterFeed("http://www.ufjf.br/secom/feed/").getFeed();
            } catch (IOException | SAXException | ParserConfigurationException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            // Mostra o fragmento com a lista
            if (feed != null)
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.container, new NoticiasFragment())
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                        .commit();
        }
    }
}
