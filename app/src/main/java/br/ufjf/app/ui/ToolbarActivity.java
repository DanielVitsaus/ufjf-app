package br.ufjf.app.ui;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import br.ufjf.dcc.pesquisa.R;

/**
 * Classe que serve de base para outras Activities que possuem uma Toolbar
 * Created by Jorge Augusto da Silva Moreira on 12/06/2015.
 */
public class ToolbarActivity extends AppCompatActivity {

    private static final String TAG = "ToolbarActivity";

    private Toolbar toolbar;

    private boolean toolbarInicializada;

    public Toolbar getToolbar() {
        return toolbar;
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (!toolbarInicializada)
            throw new IllegalStateException("Você deve chamar super.inicializarToolbar no " +
                    "final de onCreate");

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Se o botão de voltar foi pressionado na Toolbar
        if (item != null && item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void setTitle(CharSequence title) {
        super.setTitle(title);
        toolbar.setTitle(title);
    }

    @Override
    public void setTitle(int titleId) {
        super.setTitle(titleId);
        toolbar.setTitle(titleId);
    }

    protected void inicializarToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar == null)
            throw new IllegalStateException("Layout is required to include a Toolbar with id " +
                    "'toolbar'");

        setSupportActionBar(toolbar);

        toolbarInicializada = true;
    }
}
