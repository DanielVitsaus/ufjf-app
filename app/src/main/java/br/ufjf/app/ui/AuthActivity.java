package br.ufjf.app.ui;

import android.os.Bundle;

import br.ufjf.dcc.pesquisa.R;

/**
 * Created by Jorge Augusto da Silva Moreira on 12/06/2015.
 */
public class AuthActivity extends ToolbarActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);
        initializeToolbar();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}
