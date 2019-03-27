package cn.jestar.ffw;

import java.util.HashMap;
import java.util.Map;

import cn.jestar.ffw.groups.GroupsFragment;

/**
 * Created by 花京院 on 2019/3/26.
 */

public class FragmentFactory {
    private Map<String, BaseFragment> mMap = new HashMap<>();

    public BaseFragment getFragment(String tag) {
        BaseFragment fragment = mMap.get(tag);
        if (fragment == null) {
            fragment = createFragment(tag);
        }
        return fragment;
    }

    private BaseFragment createFragment(String tag) {
        switch (tag) {
            case BaseFragment.Tags.group:
                return new GroupsFragment();
            default:
                return null;
        }
    }
}
