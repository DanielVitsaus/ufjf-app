package br.ufjf.app.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import br.ufjf.dcc.pesquisa.R;

/**
 * Basic Fragment to manage a Toolbar
 * Created by Jorge on 30/11/2014.
 */
public abstract class ToolbarFragment extends Fragment {

    private Toolbar mToolbar;
    private View.OnClickListener mNavigationOnClickListener;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mNavigationOnClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) getActivity()).openDrawer();
            }
        };
    }

    void initializeToolbar(View rootView, @StringRes int title) {
        initializeToolbar(rootView, rootView.getContext().getString(title));
    }

    void initializeToolbar(View rootView, String title) {
        mToolbar = (Toolbar) rootView.findViewById(R.id.toolbar);
        if (title != null)
            mToolbar.setTitle(title);
        setupToolbar();
    }

    private void setupToolbar() {
        mToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                onToolbarMenuItemClick(menuItem);
                return true;
            }
        });

        mToolbar.setNavigationIcon(R.drawable.ic_menu_white_24dp);
        mToolbar.setNavigationOnClickListener(mNavigationOnClickListener);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        invalidateMenu();
    }

    protected abstract void inflateMenu(Toolbar toolbar);

    void invalidateMenu() {
        mToolbar.getMenu().clear();
        mToolbar.inflateMenu(R.menu.main);
        inflateMenu(mToolbar);
    }

    protected abstract void onToolbarMenuItemClick(MenuItem menuItem);
}
