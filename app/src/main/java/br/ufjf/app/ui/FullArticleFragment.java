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

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import br.ufjf.app.util.WebHelper;
import br.ufjf.dcc.pesquisa.R;

public class FullArticleFragment extends Fragment {
    private static final String ARG_URL = "url";
    private WebView mWebView;
    private Listener mListener;

    public interface Listener {
        void onFullArticleFragmentStart(FullArticleFragment fragment);

        void onFullArticleFragmentStop();
    }

    public static FullArticleFragment newInstance(String url) {
        Bundle args = new Bundle();
        args.putString(ARG_URL, url);

        FullArticleFragment articleFragment = new FullArticleFragment();
        articleFragment.setArguments(args);

        return articleFragment;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mListener = (Listener) activity;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_full_article, container, false);
        mWebView = ((WebView) view.findViewById(R.id.web_view));
        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.full_article, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.open_in_browser) {
            getActivity().startActivity(
                    new Intent(Intent.ACTION_VIEW, Uri.parse(mWebView.getUrl())));
            return true;
        } else return false;
    }

    @Override
    public void onStart() {
        super.onStart();
        mListener.onFullArticleFragmentStart(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        mListener.onFullArticleFragmentStop();
    }

    public boolean goBack() {
        if (mWebView.canGoBack()) {
            mWebView.goBack();
            return true;
        } else
            return false;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        String url = getArguments().getString(ARG_URL);
        new ArticleParserTask().execute(url);
    }

    private class ArticleParserTask extends AsyncTask<String, Void, String> {
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
            String html = WebHelper.getContent(url);

            if (html != null) {
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
            mWebView.loadDataWithBaseURL("http://www.ufjf.br/secom", data, "text / html", "UTF-8", url);
        }
    }
}