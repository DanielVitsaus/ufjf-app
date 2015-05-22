package br.ufjf.app.ui;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import org.xml.sax.SAXException;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import br.ufjf.app.model.news.Feed;
import br.ufjf.app.ui.adapter.NewsAdapter;
import br.ufjf.app.util.WebHelper;
import br.ufjf.dcc.pesquisa.R;

public class NewsFragment extends ToolbarFragment {

    private RecyclerView recyclerView;
    private Feed feed;
    private NewsAdapter.OnArticleClickListener mListener;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mListener = (NewsAdapter.OnArticleClickListener) activity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_news, container, false);

        initializeToolbar(view, R.string.news);

        recyclerView = (RecyclerView) view.findViewById(R.id.list);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        new FeedTask().execute();
    }

    @Override
    protected void inflateMenu(Toolbar toolbar) {

    }

    @Override
    protected void onToolbarMenuItemClick(MenuItem menuItem) {

    }

    private class FeedTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            try {
                feed = WebHelper.readFeed("http://www.ufjf.br/secom/rss").getFeed();
            } catch (IOException | SAXException | ParserConfigurationException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            recyclerView.setAdapter(new NewsAdapter(feed.getArticles(), mListener));
        }
    }
}