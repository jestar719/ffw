package cn.jestar.ffw;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;

import cn.jestar.ffw.groups.MonsterGroupAdapter;

public class MainActivity extends AppCompatActivity {

    private Toolbar mToolBar;
    private MainModel mModel;
    private FragmentFactory mFactory;
    private BaseFragment mCurrentFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mToolBar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolBar);
        mModel = ViewModelProviders.of(this).get(MainModel.class);
        mModel.init(this);
        mFactory = new FragmentFactory();
        showFragment(BaseFragment.Tags.group);
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

}
