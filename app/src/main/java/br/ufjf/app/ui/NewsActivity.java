package br.ufjf.app.ui;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;

import org.xml.sax.SAXException;

import java.io.IOException;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import br.ufjf.app.model.news.Article;
import br.ufjf.app.model.news.Feed;
import br.ufjf.app.ui.FullArticleFragment.Listener;
import br.ufjf.app.util.WebHelper;
import br.ufjf.dcc.pesquisa.R;

/**
 * Created by Jorge Augusto da Silva Moreira on 20/05/2015.
 */
public class NewsActivity extends DrawerActivity implements NewsFragment.Listener, ArticleFragment.OnFullArticleClickListener, Listener {
    private Feed mFeed;
    private FullArticleFragment mCurrentFullArticleFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawer);
        initializeToolbar();
        setTitle(R.string.news);

        new FeedTask().execute();
    }

    @Override
    public void onArticleSelected(Article article) {
        ArticleFragment fragment = ArticleFragment.newInstance(article);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, fragment)
                .addToBackStack(null)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .commit();
    }

    @Override
    public void onBackPressed() {
        if (mCurrentFullArticleFragment == null || !mCurrentFullArticleFragment.goBack())
            super.onBackPressed();
    }

    @Override
    public List<Article> getArticles() {
        return mFeed.getArticles();
    }

    @Override
    public void onFullArticleClick(String url) {
        FullArticleFragment fragment = FullArticleFragment.newInstance(url);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, fragment)
                .addToBackStack(null)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .commit();
    }

    @Override
    public void onFullArticleFragmentStart(FullArticleFragment fragment) {
        mCurrentFullArticleFragment = fragment;
    }

    @Override
    public void onFullArticleFragmentStop() {
        mCurrentFullArticleFragment = null;
    }

    private class FeedTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            try {
                mFeed = WebHelper.readFeed("http://www.ufjf.br/secom/rss").getFeed();
            } catch (IOException | SAXException | ParserConfigurationException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if (mFeed != null)
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.container, new NewsFragment())
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                        .commit();
        }
    }
}
