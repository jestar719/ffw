package cn.jestar.ffw.groups;

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

public class GroupsFragment extends BaseFragment {
    public GroupsFragment() {
        mTag =Tags.group;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Context context = container.getContext();
        RecyclerView view = new RecyclerView(context);
        view.setLayoutManager(new LinearLayoutManager(context));
        MonsterGroupAdapter adapter = new MonsterGroupAdapter(mModel.getGroups());
        view.setAdapter(adapter);
        return view;
    }
}
