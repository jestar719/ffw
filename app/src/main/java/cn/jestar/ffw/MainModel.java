package cn.jestar.ffw;

import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModel;
import android.content.Context;

import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Map;

import cn.jestar.ffw.monster.DataRepository;
import cn.jestar.ffw.monster.Monster;
import cn.jestar.ffw.monster.MonsterGroup;

/**
 * Created by 花京院 on 2019/3/24.
 */

public class MainModel extends ViewModel {

    private DataRepository mRepository;
    private MutableLiveData<String> mSearchData = new MutableLiveData<>();
    private MutableLiveData<String> mFragmentName = new MutableLiveData<>();
    private MutableLiveData<Integer> mGroupSelect = new MutableLiveData<>();

    public void init(Context context) {
        try {
            InputStream open = context.getAssets().open("repository.json");
            InputStreamReader reader = new InputStreamReader(open);
            mRepository = new Gson().fromJson(reader, DataRepository.class);
            mRepository.init();
            reader.close();
            open.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<MonsterGroup> getGroups() {
        return mRepository.getList();
    }

    public void onSearch(String query) {
        mSearchData.setValue(query);
    }

    public void observerSearch(LifecycleOwner owner, Observer<String> observer) {
        mSearchData.observe(owner, observer);
    }

    public void observerFragmentSwitch(LifecycleOwner owner, Observer<String> observer) {
        mFragmentName.observe(owner, observer);
    }

    public void observerGroupSelect(LifecycleOwner owner, Observer<Integer> observer) {
        mGroupSelect.observe(owner, observer);
    }

    public void onGroupSelect(int position, int index) {
        mFragmentName.setValue(BaseFragment.Tags.MONSTER);
        int i = (position << 4) + index;
        mGroupSelect.setValue(i);
    }


    public int getGroupIdByMonsterName(String s) {
        int index = 0;
        Map<String, Monster> monsters = mRepository.getMonsters();
        for (String name : monsters.keySet()) {
            if (name.contains(s)) {
                index = monsters.get(name).getGroup();
                break;
            }
        }
        return index - 1;
    }
}
