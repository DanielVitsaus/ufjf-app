package br.ufjf.app.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import br.ufjf.app.model.news.Article;
import br.ufjf.dcc.pesquisa.R;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ItemHolder> {

    private final ArrayList<Article> mArticles;
    private final OnArticleSelectedListener mListener;

    public interface OnArticleSelectedListener {
        void onArticleSelected(Article article);
    }

    public NewsAdapter(ArrayList<Article> articles, OnArticleSelectedListener listener){
        this.mArticles = articles;
        this.mListener = listener;
    }

    @Override
    public ItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_noticia, parent, false);
        return new ItemHolder(view);
    }

    @Override
    public void onBindViewHolder(ItemHolder holder, int position) {
        ((TextView) holder.itemView).setText(mArticles.get(position).getTitle());
        holder.position = position;
    }

    @Override
    public int getItemCount() {
        return mArticles.size();
    }

    protected class ItemHolder extends RecyclerView.ViewHolder {
        int position;

        public ItemHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mListener.onArticleSelected(mArticles.get(position));
                }
            });
        }
    }
}
