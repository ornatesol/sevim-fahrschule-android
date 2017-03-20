package com.fahrschule.sevim.activities;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.fahrschule.sevim.R;
import com.fahrschule.sevim.utils.Utils;

public abstract class BaseActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @BindView(R.id.toolbar)
    protected Toolbar toolbar;

    @BindView(R.id.drawer_layout)
    protected DrawerLayout drawer;

    @BindView(R.id.nav_view)
    protected NavigationView navigationView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        setupToolbar();
        setupDrawer();
        setupNavigationView();
        if (savedInstanceState == null) {
            showDefaultFragment();
        }

        if(!Utils.isOnline(this)) {
            Utils.showConnectionErrorDialog(this, getString(R.string.error_no_connection));
        }
    }

    private void setupNavigationView() {
        navigationView.setNavigationItemSelectedListener(this);
    }

    private void setupDrawer() {
        ActionBarDrawerToggle toggle =
                new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open,
                        R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
    }

    private void setupToolbar() {
        toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);
    }

    //defined by the child activities
    protected abstract void showDefaultFragment();


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_learning_site) {
            Utils.openUrlInBrowser(this, getString(R.string.learning_site_url));
        } else if (id == R.id.nav_messages) {

        } else if (id == R.id.nav_infos) {

        } else if (id == R.id.nav_locations) {

        } else if (id == R.id.nav_theory_calendar) {

        } else if (id == R.id.nav_office_timings) {
            startActivity(MainActivity.newIntent(this, getString(R.string.office_timing)));
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
}
