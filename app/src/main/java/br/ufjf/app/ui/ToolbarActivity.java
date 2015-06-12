package br.ufjf.app.ui;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import br.ufjf.dcc.pesquisa.R;

/**
 * Created by Jorge Augusto da Silva Moreira on 12/06/2015.
 */
public class ToolbarActivity extends AppCompatActivity {

    private static final String TAG = "ToolbarActivity";

    private Toolbar mToolbar;

    private boolean mToolbarInitialized;

    public Toolbar getToolbar(){
        return mToolbar;
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (!mToolbarInitialized)
            throw new IllegalStateException("You must run super.initializeToolbar at " +
                    "the end of your onCreate method");

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Home needs to be handled by returning to previous
        if (item != null && item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void setTitle(CharSequence title) {
        super.setTitle(title);
        mToolbar.setTitle(title);
    }

    @Override
    public void setTitle(int titleId) {
        super.setTitle(titleId);
        mToolbar.setTitle(titleId);
    }

    protected void initializeToolbar() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        if (mToolbar == null)
            throw new IllegalStateException("Layout is required to include a Toolbar with id " +
                    "'toolbar'");

        mToolbar.inflateMenu(R.menu.main);
        setSupportActionBar(mToolbar);

        mToolbarInitialized = true;
    }
}
