/*
 * Copyright (C) 2014 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package br.ufjf.app.ui;

import android.app.ActivityOptions;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import br.ufjf.app.model.Estudante;
import br.ufjf.app.util.AutenticacaoHelper;
import br.ufjf.dcc.pesquisa.R;

/**
 * Classe que todas as Activities com menu lateral devem extender.
 *
 * Baseada no exemplo do Google, Universal Music Player:
 * https://github.com/googlesamples/android-UniversalMusicPlayer
 */
public abstract class DrawerActivity extends ToolbarActivity {

    private static final int COD_REQ_LOGIN = 468;

    private ActionBarDrawerToggle drawerToggle;
    private DrawerLayout drawerLayout;

    private int itemParaAbrirAoFecharDrawer = -1;
    private int itemAberto = -1;

    private DrawerLayout.DrawerListener mDrawerListener = new DrawerLayout.DrawerListener() {
        @Override
        public void onDrawerClosed(View drawerView) {
            int position = itemParaAbrirAoFecharDrawer;
            if (position >= 0 && itemAberto != itemParaAbrirAoFecharDrawer) {

                // Decide qual Activity iniciar

                Class activityClass = null;
                switch (position) {
                    case 0:
                        activityClass = NoticiasActivity.class;
                        break;
                    case 1:
                        activityClass = ListaQuestionariosActivity.class;
                        break;
                    case 2:
                        activityClass = CalendarioActivity.class;
                        break;
                    case 3:
                        activityClass = InfoActivity.class;
                        break;
                }

                itemAberto = position;

                Bundle extras = ActivityOptions.makeCustomAnimation(
                        DrawerActivity.this, R.anim.fade_in, R.anim.fade_out).toBundle();
                startActivity(new Intent(DrawerActivity.this, activityClass), extras);
                finish();
            }
        }

        @Override
        public void onDrawerStateChanged(int newState) {

        }

        @Override
        public void onDrawerSlide(View drawerView, float slideOffset) {

        }

        @Override
        public void onDrawerOpened(View drawerView) {
        }
    };

    private FragmentManager.OnBackStackChangedListener mBackStackChangedListener =
            new FragmentManager.OnBackStackChangedListener() {
                @Override
                public void onBackStackChanged() {
                    updateDrawerToggle();
                }
            };

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        if (drawerToggle != null)
            drawerToggle.syncState();

    }

    @Override
    public void onResume() {
        super.onResume();

        // Whenever the fragment back stack changes, we may need to update the
        // action bar toggle: only top level screens show the hamburger-like icon, inner
        // screens - either Activities or fragments - show the "Up" icon instead.
        getSupportFragmentManager().addOnBackStackChangedListener(mBackStackChangedListener);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (drawerToggle != null)
            drawerToggle.onConfigurationChanged(newConfig);

    }

    @Override
    public void onPause() {
        super.onPause();
        getSupportFragmentManager().removeOnBackStackChangedListener(mBackStackChangedListener);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (drawerToggle != null && drawerToggle.onOptionsItemSelected(item))
            return true;

        // If not handled by drawerToggle, home needs to be handled by returning to anterior
        if (item != null && item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        // If the drawer is open, back will close it
        if (drawerLayout != null && drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawers();
            return;
        }
        // Otherwise, it may return to the anterior fragment stack
        FragmentManager fragmentManager = getSupportFragmentManager();
        if (fragmentManager.getBackStackEntryCount() > 0)
            fragmentManager.popBackStack();
        else
            // Lastly, it will rely on the system behavior for back
            super.onBackPressed();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == COD_REQ_LOGIN) {
            if (resultCode == RESULT_OK) {
                TextView nameView = (TextView) drawerLayout.findViewById(R.id.drawer_name);
                TextView courseView = (TextView) drawerLayout.findViewById(R.id.drawer_course);

                nameView.setText(data.getStringExtra("name"));
                courseView.setText(data.getStringExtra("course"));
            } else {
                finish();
            }
        } else super.onActivityResult(requestCode, resultCode, data);
    }

    protected void inicializarToolbar() {
        super.inicializarToolbar();

        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        // Create an ActionBarDrawerToggle that will handle opening/closing of the drawer:
        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout,
                getToolbar(), R.string.open_content_drawer, R.string.close_content_drawer);
        drawerToggle.setToolbarNavigationClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        drawerLayout.setDrawerListener(mDrawerListener);
        drawerLayout.setStatusBarBackgroundColor(
                getResources().getColor(R.color.primary));
        populateDrawerItems();
        updateDrawerToggle();
    }

    private void populateDrawerItems() {
        NavigationView navigationView = (NavigationView) drawerLayout.findViewById(R.id.drawer_list);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.drawer_noticias:
                        itemParaAbrirAoFecharDrawer = 0;
                        drawerLayout.closeDrawers();
                        break;
                    case R.id.drawer_questionarios:
                        itemParaAbrirAoFecharDrawer = 1;
                        drawerLayout.closeDrawers();
                        break;
                    case R.id.drawer_calendario:
                        itemParaAbrirAoFecharDrawer = 2;
                        drawerLayout.closeDrawers();
                        break;
                    case R.id.drawer_info:
                        itemParaAbrirAoFecharDrawer = 3;
                        drawerLayout.closeDrawers();
                        break;
                    case R.id.drawer_logout:
                        AutenticacaoHelper.registerLogout(DrawerActivity.this);
                        startActivityForResult(new Intent(DrawerActivity.this, AutenticacaoActivity.class), COD_REQ_LOGIN);
                }
                return true;
            }
        });

        View drawerHeader = drawerLayout.findViewById(R.id.drawer_header);
        TextView viewNome = (TextView) drawerHeader.findViewById(R.id.drawer_name);
        TextView viewCurso = (TextView) drawerHeader.findViewById(R.id.drawer_course);

        try {
            Estudante estudanteAutenticado = AutenticacaoHelper.getStudent(this);
            viewNome.setText(estudanteAutenticado.getNome());
            viewCurso.setText(estudanteAutenticado.getCurso());
        } catch (AutenticacaoHelper.StudentNaoAutenticado studentNaoAutenticado) {
            startActivityForResult(new Intent(DrawerActivity.this, AutenticacaoActivity.class), COD_REQ_LOGIN);
        }
    }

    protected void updateDrawerToggle() {
        if (drawerToggle == null)
            return;

        boolean isRoot = getSupportFragmentManager().getBackStackEntryCount() == 0;
        drawerToggle.setDrawerIndicatorEnabled(isRoot);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowHomeEnabled(!isRoot);
            getSupportActionBar().setDisplayHomeAsUpEnabled(!isRoot);
            getSupportActionBar().setHomeButtonEnabled(!isRoot);
        }
        if (isRoot)
            drawerToggle.syncState();

    }
}
