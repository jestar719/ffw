package cn.jestar.ffw;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.internal.NavigationMenuView;
import android.support.design.widget.NavigationView;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private Toolbar mToolBar;
    private MainModel mModel;
    private FragmentFactory mFactory;
    private BaseFragment mCurrentFragment;
    private DrawerLayout mDrawer;
    private SearchView mSearchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mToolBar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolBar);
        mModel = ViewModelProviders.of(this).get(MainModel.class);
        mModel.init(this);
        mModel.observerFragmentSwitch(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                showFragment(s);
            }
        });
        mFactory = new FragmentFactory();
        showFragment(BaseFragment.Tags.GROUP);
        initLeftMenu();
    }

    /**
     * 初始化侧拉菜单
     */
    private void initLeftMenu() {
        mDrawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, mDrawer, mToolBar, R.string.open, R.string.close);
        toggle.syncState();
        mDrawer.setDrawerListener(toggle);
        NavigationView navigation = mDrawer.findViewById(R.id.navigation);
        navigation.setItemIconTintList(null);
        NavigationMenuView menuView = (NavigationMenuView) navigation.getChildAt(0);
        menuView.setVerticalScrollBarEnabled(false);
        navigation.setNavigationItemSelectedListener(this);
    }

    private void showFragment(String tag) {
        BaseFragment fragment = mFactory.getFragment(tag);
        if (fragment == mCurrentFragment)
            return;
        mCurrentFragment = fragment;
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fl_container, fragment, tag)
                .commit();
    }

    @Override
    public void onBackPressed() {
        if (mDrawer.isDrawerOpen(Gravity.START)) {
            mDrawer.closeDrawer(Gravity.START);
        } else if (!mCurrentFragment.getTag().equals(BaseFragment.Tags.GROUP)) {
            showFragment(BaseFragment.Tags.GROUP);
        } else {
            super.onBackPressed();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_title, menu);
        MenuItem item = menu.findItem(R.id.action_search);
        mSearchView = (SearchView) MenuItemCompat.getActionView(item);
        initSearchView();
        return super.onCreateOptionsMenu(menu);
    }

    private void initSearchView() {
        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                mModel.onSearch(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int groupId = item.getGroupId();
        if (groupId == R.id.group_index) {
            showFragment((String) item.getTitle());
            mDrawer.closeDrawers();
        }
        return false;
    }
}
