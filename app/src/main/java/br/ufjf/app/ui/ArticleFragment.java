package br.ufjf.app.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import br.ufjf.app.model.news.Article;
import br.ufjf.dcc.pesquisa.R;

public class ArticleFragment extends Fragment {
    private static final String ARG_ARTICLE = "article";

    private Article article;

    public static ArticleFragment newInstance(Article article){
        Bundle args = new Bundle();
        args.putParcelable(ARG_ARTICLE, article);

        ArticleFragment articleFragment = new ArticleFragment();
        articleFragment.setArguments(args);

        return articleFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        article = getArguments().getParcelable(ARG_ARTICLE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_article, container, false);

        TextView title = ((TextView) view.findViewById(R.id.title));
        TextView content = ((TextView) view.findViewById(R.id.content));

        title.setText(article.getTitle());
        content.setText(Html.fromHtml(article.getContent()));
        content.setMovementMethod(LinkMovementMethod.getInstance());

        return view;
    }
}