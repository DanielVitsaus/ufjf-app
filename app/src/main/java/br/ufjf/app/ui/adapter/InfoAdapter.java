package br.ufjf.app.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import br.ufjf.app.ui.ListaContatosFragment;

/**
 * Adapter para o ViewPager de meses (calendário)
 * Created by Jorge Augusto da Silva Moreira on 20/05/2015.
 */
public class InfoAdapter extends FragmentPagerAdapter {
    private String[] titulos;

    public InfoAdapter(FragmentManager fragmentManager, String[] titulos) {
        super(fragmentManager);
        this.titulos = titulos;
    }

    @Override
    public Fragment getItem(int position) {
        return ListaContatosFragment.obterNovo(position);
    }

    @Override
    public int getCount() {
        return 4;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titulos[position];
    }
}
