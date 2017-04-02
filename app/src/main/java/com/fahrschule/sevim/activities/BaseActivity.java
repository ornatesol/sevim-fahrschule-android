package com.fahrschule.sevim.activities;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.fahrschule.sevim.R;

public abstract class BaseActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @BindView(R.id.toolbar)
    protected Toolbar toolbar;

    @BindView(R.id.drawer_layout)
    protected DrawerLayout drawer;

    @BindView(R.id.nav_view)
    protected NavigationView navigationView;

    private NavItemActionTargetListener listener;

    ActionBarDrawerToggle toggle;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        setupToolbar();
        setupDrawer();
        setupNavigationView();
        registerOnBackStackListener();
        if (savedInstanceState == null) {
            showDefaultFragment();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        try {
            listener = (NavItemActionTargetListener) this;
        } catch (ClassCastException e) {
            throw new IllegalStateException(
                    "Activity should implement NavItemActionTargetListener!", e);
        }
    }

    private void setupNavigationView() {
        navigationView.setNavigationItemSelectedListener(this);
    }

    private void setupDrawer() {
        toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open,
                        R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(getSupportFragmentManager().getBackStackEntryCount() > 0){
                    getSupportFragmentManager().popBackStack();
                }else {
                    drawer.openDrawer(GravityCompat.START);
                }
            }
        });
    }
    
    private void setupToolbar() {
        toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);
    }

    //defined by the child activities
    protected abstract void showDefaultFragment();

    @SuppressWarnings("StatementWithEmptyBody") @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        switch (id) {
            case R.id.nav_learning_site:
                listener.handleSelection(getString(R.string.learning_site));
                break;
            case R.id.nav_messages:
                listener.handleSelection(getString(R.string.messages));
                break;
            case R.id.nav_infos:
                listener.handleSelection(getString(R.string.infos));
                break;
            case R.id.nav_locations:
                listener.handleSelection(getString(R.string.locations));
                break;
            case R.id.nav_theory_calendar:
                listener.handleSelection(getString(R.string.theory_calendar));
                break;
            case R.id.nav_office_timings:
                listener.handleSelection(getString(R.string.office_timing));
                break;

            default:
                listener.handleSelection("");
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    private void registerOnBackStackListener() {
        getSupportFragmentManager().addOnBackStackChangedListener(
                new FragmentManager.OnBackStackChangedListener() {
            @Override
            public void onBackStackChanged() {
                updateActionBar();
            }
        });
    }

    private void updateActionBar() {
        if(getSupportActionBar() != null) {
            if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            } else {
                getSupportActionBar().setDisplayHomeAsUpEnabled(false);
                toggle.syncState();
            }
        }
    }

    /**
     * To establish communication with the Activity handling receiving Nav Item Actions
     */
    interface NavItemActionTargetListener {
        /**
         * Handles which particular Activity/Fragment to initiate based on selected Menu
         * @param selectedMenu Navigation Drawer Item clicked
         */
        void handleSelection(String selectedMenu);
    }

}
