package br.ufjf.app.ui;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;

import br.ufjf.app.ui.DrawerActivity;
import br.ufjf.app.ui.adapter.InfoAdapter;
import br.ufjf.dcc.pesquisa.R;

/**
 * Exibe a lista de questionários disponìveis para o estudante responder
 * Created by Jorge Augusto da Silva Moreira on 20/05/2015.
 */
public class InfoActivity extends DrawerActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_abas);
        inicializarToolbar();

        // Títulos das páginas
        String[] titulos = new String[4];
        titulos[0] = getString(R.string.pro_reitorias);
        titulos[1] = getString(R.string.coordenacoes);
        titulos[2] = getString(R.string.unidades);
        titulos[3] = getString(R.string.outros);

        // Adiciona as páginas
        ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        viewPager.setAdapter(new InfoAdapter(getSupportFragmentManager(), titulos));

        // Adiciona as abas
        TabLayout tabLayout = (TabLayout) findViewById(R.id.abas);
        tabLayout.setupWithViewPager(viewPager);
    }
}
