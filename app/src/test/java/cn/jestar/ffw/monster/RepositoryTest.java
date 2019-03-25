package cn.jestar.ffw.monster;

import com.google.gson.Gson;

import org.junit.Before;
import org.junit.Test;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Created by 花京院 on 2019/3/25.
 */

public class RepositoryTest {

    private Gson mGson;
    private DataRepository mRepository;
    private File mFile;
    private File mDir;

    @Before
    public void init() throws FileNotFoundException {
        mFile = new File("app/src/main/assets/repository.json");
        mDir=new File("app/src/main/res/mipmap-xxhdpi");
        assertTrue(mFile.exists());
        mGson = new Gson();
        FileReader reader = new FileReader(mFile);
        mRepository = mGson.fromJson(reader, DataRepository.class);
        assertNotNull(mRepository);
    }

    @Test
    public void resetMonsterGroups() throws IOException {
        List<MonsterGroup> list = mRepository.getList();
        resetGroupId(list);
        write();
    }


    @Test
    public void resetName() throws IOException {
        for (MonsterGroup group : mRepository.getList()) {
            List<Monster> list = group.getList();
            if (list.size()>1){
                for (Monster monster : list) {
                    String name = monster.getName();
                    if (name.contains("☆")){
                        monster.setName(name.substring(0, name.length() - 1));
                        monster.setCatchCondition(null);
                        monster.setShadowStone(null);
                        monster.setTransformCondition(null);
                    }
                }
            }
        }
        write();
    }

    private void resetGroupId(List<MonsterGroup> list) throws IOException {
        int size = list.size();
        int id;
        int mId = 0;
        for (int i = 0; i < size; i++) {
            id = i + 1;
            MonsterGroup group = list.get(i);
            group.setId(id);
            for (Monster monster : group.getList()) {
                mId++;
                monster.setGroup(id);
                monster.setId(mId);
                loadImg(monster);
            }
        }
    }

    private void loadImg(Monster monster) throws IOException {
        URL url = new URL(monster.getUrl());
        File file = new File(mDir, String.format("icon_%s.jpg", monster.getId()));
        BufferedOutputStream outputStream = new BufferedOutputStream(new FileOutputStream(file));
        byte[] buff = new byte[4096];
        InputStream stream = url.openStream();
        int len = 0;
        while ((len = stream.read(buff)) != -1) {
            outputStream.write(buff, 0, len);
        }
        outputStream.close();
        stream.close();
    }

    private void write() throws IOException {
        FileWriter writer = new FileWriter(mFile);
        writer.write(mGson.toJson(mRepository));
        writer.close();
    }
}
