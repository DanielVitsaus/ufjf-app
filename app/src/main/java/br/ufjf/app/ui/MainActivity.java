package br.ufjf.app.ui;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import br.ufjf.app.model.news.Article;
import br.ufjf.app.ui.adapter.NewsAdapter;
import br.ufjf.dcc.pesquisa.R;

/**
 * Created by Jorge Augusto da Silva Moreira on 20/05/2015.
 */
public class MainActivity extends AppCompatActivity implements NewsAdapter.OnArticleSelectedListener {

    public static final int NEWS_FRAG = 0;
    public static final int SURVEYS_FRAG = 1;
    // Constants to identify the fragment currently being shown
    private static final int NO_FRAG = -1;
    private static final int SECONDARY_FRAG = -2;
    // Constants for fragment containers
    private static final String TAG_MAIN_FRAG = "main";

    private DrawerLayout mDrawerLayout;
    private int mCurrentFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Navigation Drawer stuff
        mDrawerLayout = (DrawerLayout) findViewById(R.id.main_drawer);
        mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, Gravity.START);

        // Initialize fragment
        if (mCurrentFragment != NO_FRAG) {
            mCurrentFragment = NEWS_FRAG;
            checkDrawerItem(getDrawerItem(mCurrentFragment), false);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.main_content, new NewsFragment())
                    .commit();
        }
    }

    private LinearLayout getDrawerItem(int drawerPosition) {
        switch (drawerPosition) {
            case NEWS_FRAG:
                return (LinearLayout) mDrawerLayout.findViewById(R.id.drawer_news);
            case SURVEYS_FRAG:
                return (LinearLayout) mDrawerLayout.findViewById(R.id.drawer_surveys);
            default:
                return null;
        }
    }

    private void checkDrawerItem(LinearLayout itemToCheck, boolean uncheckPrevious) {
        if (uncheckPrevious)
            uncheckDrawerItem(mCurrentFragment);

        if (itemToCheck != null) {
            ((ImageView) itemToCheck.getChildAt(0)).setColorFilter(getResources().getColor(R.color.primary_dark));
            TextView textView = ((TextView) itemToCheck.getChildAt(1));
            textView.setTextColor(getResources().getColor(R.color.primary_dark));
            textView.setTypeface(Typeface.DEFAULT_BOLD);
        }
    }

    private void uncheckDrawerItem(int drawerPosition) {
        LinearLayout checkedDrawerItem = getDrawerItem(drawerPosition);
        if (checkedDrawerItem != null) {
            ((ImageView) checkedDrawerItem.getChildAt(0)).setColorFilter(getResources().getColor(R.color.drawer_icon));
            TextView textView = ((TextView) checkedDrawerItem.getChildAt(1));
            textView.setTextColor(getResources().getColor(android.R.color.black));
            textView.setTypeface(Typeface.DEFAULT);
        }
    }

    public void openDrawer(){
        mDrawerLayout.openDrawer(Gravity.START);
    }

    /**
     * Swaps fragments in the main content view
     */
    public void selectNavigationDrawerItem(View view) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = null;

        switch (view.getId()) {
            case R.id.drawer_news:
                if (mCurrentFragment == NEWS_FRAG) {
                    mDrawerLayout.closeDrawer(Gravity.START);
                    return;
                }

                checkDrawerItem((LinearLayout) view, mCurrentFragment != NO_FRAG);
                fragment = new NewsFragment();

                mCurrentFragment = 0;
                break;
            case R.id.drawer_surveys:
                if (mCurrentFragment == SURVEYS_FRAG) {
                    mDrawerLayout.closeDrawer(Gravity.START);
                    return;
                }

                checkDrawerItem((LinearLayout) view, mCurrentFragment != NO_FRAG);
                fragment = new SurveysExplorerFragment();
                mCurrentFragment = 1;
                break;
            case R.id.drawer_settings:
                //startActivity(new Intent(this, SettingsActivity.class));
                return;
        }

        //Clears back stack
        fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);

        // Insert the fragment by replacing any existing fragment
        fragmentManager.beginTransaction()
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .replace(R.id.main_content, fragment, TAG_MAIN_FRAG)
                .commit();

        // Close the drawer
        mDrawerLayout.closeDrawer(Gravity.START);
    }

    @Override
    public void onArticleSelected(Article article) {
        ArticleFragment fragment = ArticleFragment.newInstance(article);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.main_content, fragment)
                .addToBackStack(null)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .commit();
    }
}
