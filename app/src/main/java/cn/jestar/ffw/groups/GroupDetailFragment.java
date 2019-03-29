package cn.jestar.ffw.groups;

import android.arch.lifecycle.Observer;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import cn.jestar.ffw.BaseFragment;
import cn.jestar.ffw.R;
import cn.jestar.ffw.StringUtils;
import cn.jestar.ffw.monster.Monster;

/**
 * Created by 花京院 on 2019/3/29.
 */

public class GroupDetailFragment extends BaseFragment {
    private MonsterAdapter mAdapter;
    private List<Monster> mList;
    private TextView mTvName;
    private TextView mTvLocation;
    private TextView mTvCatch;
    private TextView mTvTransform;
    private TextView mTvShadow;
    private TextView mTvWeight;
    private TextView mTvDrop;
    private TextView mTvPrivateSkill;
    private TextView mTvExcRes;
    private TextView mTvEleRes;
    private TextView mTvPublicSkill;
    private TextView mTvPassiveSkill;

    public GroupDetailFragment() {
        mTag = Tags.MONSTER;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_monster, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        initRecycler(view);
        initViews(view);
        mModel.observerGroupSelect(this, new Observer<Integer>() {
            @Override
            public void onChanged(@Nullable Integer integer) {
                mList = mModel.getGroups().get(integer >> 4).getList();
                mAdapter.setList(mList);
                int i = integer & 7;
                onMonsterSelect(i);
            }
        });
        super.onViewCreated(view, savedInstanceState);
    }

    private void initViews(View view) {
        mTvName = getTextView(view, R.id.tv_name_value);
        mTvLocation = getTextView(view, R.id.tv_location_value);
        mTvCatch = getTextView(view, R.id.tv_catch_value);
        mTvTransform = getTextView(view, R.id.tv_transform_value);
        mTvShadow = getTextView(view, R.id.tv_shadow_value);
        mTvWeight = getTextView(view, R.id.tv_weight_value);
        mTvDrop = getTextView(view, R.id.tv_drop_value);
        mTvEleRes = getTextView(view, R.id.tv_element_resist_value);
        mTvExcRes = getTextView(view, R.id.tv_exception_resist_value);
        mTvPrivateSkill = getTextView(view, R.id.tv_private_skill_value);
        mTvPublicSkill = getTextView(view, R.id.tv_public_skill_value);
        mTvPassiveSkill = getTextView(view, R.id.tv_passive_skill_value);
    }

    private TextView getTextView(View view, @IdRes int id) {
        return (TextView) view.findViewById(id);
    }

    private void initRecycler(@NonNull View view) {
        RecyclerView mRv = view.findViewById(R.id.rv);
        mRv.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        mAdapter = new MonsterAdapter().setListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int tag = (int) v.getTag();
                onMonsterSelect(tag);
            }
        });
        mRv.setAdapter(mAdapter);
        LinearSnapHelper helper = new LinearSnapHelper();
        helper.attachToRecyclerView(mRv);
    }

    private void onMonsterSelect(int index) {
        Monster monster = mList.get(index);
        setMonsterData(monster);
    }

    private void setMonsterData(Monster monster) {
        mTvName.setText(monster.getName());
        mTvLocation.setText(StringUtils.getString(monster.getLocation()));
        mTvCatch.setText(StringUtils.getString(monster.getCatchCondition()));
        mTvTransform.setText(StringUtils.getString(monster.getTransformCondition()));
        mTvShadow.setText(StringUtils.getString(monster.getShadowStone()));
        mTvDrop.setText(StringUtils.getString(monster.getDrop()));
    }


}
