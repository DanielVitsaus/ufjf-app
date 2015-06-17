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
import android.app.FragmentManager;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import br.ufjf.app.model.Student;
import br.ufjf.app.util.AuthHelper;
import br.ufjf.dcc.pesquisa.R;

public abstract class DrawerActivity extends ToolbarActivity {

    private static final String TAG = "DrawerActivity";
    private static final int REQ_CODE_LOGIN = 468;

    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout mDrawerLayout;

    private int mItemToOpenWhenDrawerCloses = -1;

    private DrawerLayout.DrawerListener mDrawerListener = new DrawerLayout.DrawerListener() {
        @Override
        public void onDrawerClosed(View drawerView) {
            if (mDrawerToggle != null) mDrawerToggle.onDrawerClosed(drawerView);
            int position = mItemToOpenWhenDrawerCloses;
            if (position >= 0) {

                Class activityClass = null;
                switch (position) {
                    case 0:
                        activityClass = NewsActivity.class;
                        break;
                    case 1:
                        activityClass = SurveysExplorerActivity.class;
                        break;
                }

                Bundle extras = ActivityOptions.makeCustomAnimation(
                        DrawerActivity.this, R.anim.fade_in, R.anim.fade_out).toBundle();
                startActivity(new Intent(DrawerActivity.this, activityClass), extras);
                finish();
            }
        }

        @Override
        public void onDrawerStateChanged(int newState) {
            if (mDrawerToggle != null) mDrawerToggle.onDrawerStateChanged(newState);
        }

        @Override
        public void onDrawerSlide(View drawerView, float slideOffset) {
            if (mDrawerToggle != null) mDrawerToggle.onDrawerSlide(drawerView, slideOffset);
        }

        @Override
        public void onDrawerOpened(View drawerView) {
            if (mDrawerToggle != null) mDrawerToggle.onDrawerOpened(drawerView);
            if (getSupportActionBar() != null) getSupportActionBar()
                    .setTitle(R.string.app_name);
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
        if (mDrawerToggle != null)
            mDrawerToggle.syncState();

    }

    @Override
    public void onResume() {
        super.onResume();

        // Whenever the fragment back stack changes, we may need to update the
        // action bar toggle: only top level screens show the hamburger-like icon, inner
        // screens - either Activities or fragments - show the "Up" icon instead.
        getFragmentManager().addOnBackStackChangedListener(mBackStackChangedListener);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (mDrawerToggle != null)
            mDrawerToggle.onConfigurationChanged(newConfig);

    }

    @Override
    public void onPause() {
        super.onPause();
        getFragmentManager().removeOnBackStackChangedListener(mBackStackChangedListener);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mDrawerToggle != null && mDrawerToggle.onOptionsItemSelected(item))
            return true;

        // If not handled by drawerToggle, home needs to be handled by returning to previous
        if (item != null && item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        // If the drawer is open, back will close it
        if (mDrawerLayout != null && mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawers();
            return;
        }
        // Otherwise, it may return to the previous fragment stack
        FragmentManager fragmentManager = getFragmentManager();
        if (fragmentManager.getBackStackEntryCount() > 0)
            fragmentManager.popBackStack();
        else
            // Lastly, it will rely on the system behavior for back
            super.onBackPressed();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQ_CODE_LOGIN && resultCode == RESULT_OK) {
            TextView nameView = (TextView) mDrawerLayout.findViewById(R.id.drawer_name);
            TextView emailView = (TextView) mDrawerLayout.findViewById(R.id.drawer_email);

            nameView.setText(data.getStringExtra("name"));
            emailView.setText(data.getStringExtra("email"));
        } else super.onActivityResult(requestCode, resultCode, data);
    }

    protected void initializeToolbar() {
        super.initializeToolbar();

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        // Create an ActionBarDrawerToggle that will handle opening/closing of the drawer:
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
                getToolbar(), R.string.open_content_drawer, R.string.close_content_drawer);
        mDrawerLayout.setDrawerListener(mDrawerListener);
        mDrawerLayout.setStatusBarBackgroundColor(
                getResources().getColor(R.color.primary));
        populateDrawerItems();
        updateDrawerToggle();
    }

    private void populateDrawerItems() {
        NavigationView navigationView = (NavigationView) mDrawerLayout.findViewById(R.id.drawer_list);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.drawer_news:
                        mItemToOpenWhenDrawerCloses = 0;
                        break;
                    case R.id.drawer_surveys:
                        mItemToOpenWhenDrawerCloses = 1;
                        break;
                }
                mDrawerLayout.closeDrawers();
                return true;
            }
        });

        View drawerHeader = mDrawerLayout.findViewById(R.id.drawer_header);
        drawerHeader.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(new Intent(DrawerActivity.this, AuthActivity.class), REQ_CODE_LOGIN);
            }
        });

        TextView nameView = (TextView) drawerHeader.findViewById(R.id.drawer_name);
        TextView emailView = (TextView) drawerHeader.findViewById(R.id.drawer_email);

        try {
            Student loggedStudent = AuthHelper.getStudent(this);

            nameView.setText(loggedStudent.getName());
            emailView.setText(loggedStudent.getEmail());
        } catch (AuthHelper.StudentNotLoggedIn studentNotLoggedIn) {
            nameView.setText("Estudante,");
            emailView.setText("Toque aqui para entrar.");
        }
    }

    protected void updateDrawerToggle() {
        if (mDrawerToggle == null)
            return;

        boolean isRoot = getFragmentManager().getBackStackEntryCount() == 0;
        mDrawerToggle.setDrawerIndicatorEnabled(isRoot);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowHomeEnabled(!isRoot);
            getSupportActionBar().setDisplayHomeAsUpEnabled(!isRoot);
            getSupportActionBar().setHomeButtonEnabled(!isRoot);
        }
        if (isRoot)
            mDrawerToggle.syncState();

    }
}
