package cn.jestar.ffw;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

public class MainActivity extends AppCompatActivity {

    private Toolbar mToolBar;
    private MainModel mModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mToolBar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolBar);
        mModel = ViewModelProviders.of(this).get(MainModel.class);
        mModel.init(this);
        initRv();
    }

    private void initRv() {
        RecyclerView mRv = findViewById(R.id.rv);
        mRv.setLayoutManager(new LinearLayoutManager(this));
        MonsterGroupAdapter adapter = new MonsterGroupAdapter(mModel.getGroups());
        mRv.setAdapter(adapter);
    }
}
