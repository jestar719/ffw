package cn.jestar.ffw;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

/**
 * Created by 花京院 on 2019/3/26.
 */

public class BaseFragment extends Fragment {
    protected String mTag;
    protected MainModel mModel;

    @CallSuper
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mModel = ViewModelProviders.of(getActivity()).get(MainModel.class);
    }

    public @interface Tags {
        String GROUP = "怪物组";
        String MONSTER = "怪物";
    }


}
