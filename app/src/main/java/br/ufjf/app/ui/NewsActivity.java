package br.ufjf.app.ui;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;

import br.ufjf.app.model.news.Article;
import br.ufjf.app.ui.adapter.NewsAdapter;
import br.ufjf.dcc.pesquisa.R;

/**
 * Created by Jorge Augusto da Silva Moreira on 20/05/2015.
 */
public class NewsActivity extends DrawerActivity implements NewsAdapter.OnArticleClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawer);
        initializeToolbar();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, new NewsFragment())
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .commit();
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
}
