package com.loopcupcakes.gassy;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.loopcupcakes.gassy.adapters.ViewPagerAdapter;
import com.loopcupcakes.gassy.fragments.StationsFragment;
import com.loopcupcakes.gassy.services.LocationService;

public class MainActivity extends AppCompatActivity {

    // TODO: 6/26/16 Enable Facebook login
    // TODO: 6/26/16 Add SwipeRefreshLayout
    // TODO: 6/27/16 Add AutoComplete Google Places
    // TODO: 6/27/16 Add location support
    // TODO: 6/27/16 Runtime permissions
    // TODO: 6/27/16 Add no GPS support
    // TODO: 6/27/16 Add ViewPager ordering
    // TODO: 7/6/16 Add ButterKnife
    // TODO: 7/6/16 Handle configuration changes
    // TODO: 7/7/16 Add RxJava to update distance labels

    private static final String TAG = "MainActivityTAG_";

    private static final String STATIONS_FRAGMENT_TAG = "STATIONS_FRAGMENT_TAG";

    private Toolbar mToolbar;
    private DrawerLayout mDrawerLayout;
    private NavigationView mNavigationView;
    private ViewPager mViewPager;
    private TabLayout mTabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mToolbar = (Toolbar) findViewById(R.id.a_main_toolbar);
        mNavigationView = (NavigationView) findViewById(R.id.a_main_navigation);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.a_main_drawer);
        mViewPager = (ViewPager) findViewById(R.id.a_main_viewpager);
        mTabLayout = (TabLayout) findViewById(R.id.a_main_tablayout);

        setSupportActionBar(mToolbar);

        setupDrawerLayout();
        setupViewPager();
        triggerLocationService();
    }

    @Override
    public void onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
//            Log.d(TAG, "onOptionsItemSelected: " + LocationManager..getLastLocation());
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void setupDrawerLayout() {
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, mDrawerLayout, mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                int id = item.getItemId();

                if (id == R.id.nav_camera) {
                    // Handle the camera action
                }

                mDrawerLayout.closeDrawer(GravityCompat.START);
                return true;
            }
        });
    }

    private void triggerLocationService() {
        Intent intent = new Intent(this, LocationService.class);
        startService(intent);
    }

    private void setupViewPager() {
        if (mViewPager == null) {
            return;
        }

        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPagerAdapter.addFragment(StationsFragment.newInstance(true), getString(R.string.title_best_rated));
        viewPagerAdapter.addFragment(StationsFragment.newInstance(false), getString(R.string.title_closest));
        mViewPager.setAdapter(viewPagerAdapter);

        mTabLayout.setupWithViewPager(mViewPager);
    }
}
