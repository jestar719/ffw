package cn.jestar.ffw;

import android.arch.lifecycle.ViewModel;
import android.content.Context;

import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import cn.jestar.ffw.monster.DataRepository;
import cn.jestar.ffw.monster.MonsterGroup;

/**
 * Created by 花京院 on 2019/3/24.
 */

public class MainModel extends ViewModel {

    private DataRepository mRepository;

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
}
