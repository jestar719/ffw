package cn.jestar.ffw.groups;

import android.arch.lifecycle.Observer;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import cn.jestar.ffw.BaseFragment;

/**
 * Created by 花京院 on 2019/3/26.
 */

public class GroupsFragment extends BaseFragment implements MonsterGroupAdapter.OnItemChildClickListener {

    private MonsterGroupAdapter mAdapter;
    private RecyclerView mRecyclerView;

    public GroupsFragment() {
        mTag = Tags.GROUP;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Context context = container.getContext();
        mRecyclerView = new RecyclerView(context);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        mAdapter = new MonsterGroupAdapter(mModel.getGroups()).setListener(this);
        mRecyclerView.setAdapter(mAdapter);
        return mRecyclerView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        mModel.observerSearch(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                int index = mModel.getGroupIdByMonsterName(s);
                if (index >= 0) {
                    mRecyclerView.smoothScrollToPosition(index);
                }
            }
        });
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onItemChildClick(int position, int index) {
        mModel.onGroupSelect(position, index);
    }
}
