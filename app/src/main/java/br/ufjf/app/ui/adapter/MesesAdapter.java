package br.ufjf.app.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

import br.ufjf.app.ui.MesFragment;

/**
 * Adapter para o ViewPager de meses (calendário)
 * Created by Jorge Augusto da Silva Moreira on 20/05/2015.
 */
public class MesesAdapter extends FragmentPagerAdapter {
    private List<String> nomesMeses;

    public MesesAdapter(FragmentManager fragmentManager, List<String> monthNames) {
        super(fragmentManager);
        nomesMeses = monthNames;
    }

    @Override
    public Fragment getItem(int position) {
        return MesFragment.obterNovo(position);
    }

    @Override
    public int getCount() {
        return 12;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return nomesMeses.get(position);
    }
}
