package cn.jestar.ffw.monster;

import android.support.annotation.NonNull;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

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
import java.io.Writer;
import java.lang.reflect.Type;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static junit.framework.Assert.assertNotNull;

/**
 * Created by 花京院 on 2019/3/23.
 */
public class MonsterCompilerTest {

    private MonsterCompiler mCompiler;
    private File mDir;
    private String mMonsterFile;
    private String mLocationFile;
    private String mShadowFile;
    private Gson mGson;
    private String mRepositoryFile;

    @Before
    public void setUp() throws Exception {
        mCompiler = new MonsterCompiler();
        mDir = new File("ffwdata");
        mMonsterFile = "monster.json";
        mLocationFile = "location.json";
        mGson = new Gson();
        mShadowFile = "shadowStone.json";
        mRepositoryFile = "repository.json";
    }

    @Test
    public void readFile() throws Exception {
    }

    @Test
    public void compileTitle() throws Exception {
        String text1 = "<td bgcolor=\"ffffff\" style=\"padding: 6px 4px; border: 1px solid rgb(102, 102, 102); line-height: 1.6; font-size: 13px;\"><img src=\"http://img.dwstatic.com/ps4/1611/342635399268/342635524595.jpg\" alt=\"\" /></td>\n";
        String text2 = "<td bgcolor=\"ffffff\"\n" +
                "        style=\"padding: 6px 4px; border: 1px solid rgb(102, 102, 102); line-height: 1.6; font-size: 13px;\">\n" +
                "        <img src=\"http://img.dwstatic.com/ps4/1611/342635399268/342635821943.jpg\"\n" +
                "             title=\"1478345464995917.jpg\" alt=\"1478335133790728.jpg\"></td>";
        Monster monster = new Monster();
        StringBuilder builder = new StringBuilder();
        builder.append(text2);
        mCompiler.compileTitle(builder, monster);
        System.out.println(monster.getUrl());
    }

    @Test
    public void compileName() throws Exception {
        Monster monster = new Monster();
        StringBuilder builder = new StringBuilder();
        String text = "<td style=\"padding: 6px 4px; border: 1px solid rgb(102, 102, 102); line-height: 1.6; font-size: 13px;\"" +
                ">仙人掌怪大师</td>";
        builder.append(text);
        mCompiler.compileName(builder, monster);
        System.out.println(monster.getName());
    }

    @Test
    public void compilerLocation() {
        StringBuilder builder = new StringBuilder("<td style=\"padding: 6px 4px; border: 1px solid rgb(102, 102, 102); line-height: 1.6; font-size: 13px;\">" +
                "        <span style=\"font-size: 13px; line-height: 20.7999992370605px; background-color: rgb(255, 255, 255);\">涅布拉洞</span><span" +
                "            style=\"font-size: 13px; line-height: 20.7999992370605px; background-color: rgb(255, 255, 255);\">窟</span>（隐藏点）<br" +
                "            style=\"letter-spacing: 0px;\">" +
                "        （变身）伊弗利" +
                "    </td>");
        Monster monster = new Monster();
        mCompiler.compileLocation$Transform(builder, monster);
        System.out.println(monster);
    }


    @Test
    public void deleteSpanTest() {
        StringBuilder builder = new StringBuilder("<span style=\"line-height: 20.7999992370605px; background-color: rgb(255, 255, 255);\">涅布拉洞</span><span style=\"line-height: 20.7999992370605px; background-color: rgb(255, 255, 255);\">窟</span><span style=\"line-height: 20.7999992370605px;\">（隐藏点）</span><br style=\"letter-spacing: 0px;\">");
        mCompiler.deleteSpan(builder);
        System.out.println(builder.toString());
    }

    @Test
    public void deleteSpecialCharTest() {
        StringBuilder builder = new StringBuilder("<td style=\"padding: 6px 4px; border: 1px solid rgb(102, 102, 102); line-height: 1.6; font-size: 13px;\">\n" +
                "        &nbsp;" +
                "        &lt;" +
                "    </td>");
        mCompiler.deleteSpecialChar(builder);
        System.out.println(builder);
    }

    @Test
    public void deleteP() {
        StringBuilder builder = new StringBuilder("<span style=\"line-height: 20.7999992370605px; background-color: rgb(255, 255, 255);\">涅布拉洞</span><span style=\"line-height: 20.7999992370605px; background-color: rgb(255, 255, 255);\">窟</span><span style=\"line-height: 20.7999992370605px;\">（隐藏点）</span><br style=\"letter-spacing: 0px;\">\n" +
                "（变身）尚电小子<br style=\"letter-spacing: 0px;\">\n" +
                "<p>（变身）拉姆</p>\n" +
                "<p><span style=\"line-height: 20.7999992370605px;\">（变身）拉谬</span></p>");
        mCompiler.deleteSpan(builder);
        mCompiler.deleteP(builder);
        System.out.println(builder);
    }

    @Test
    public void testTransformCondition() {
        Monster monster = new Monster();
        StringBuilder builder = new StringBuilder("<td style=\"padding: 6px 4px; border: 1px solid rgb(102, 102, 102); line-height: 1.6; font-size: 13px;\">" +
                "    少女房间灵魂任务&ldquo;五大魔震费加罗篇&rdquo;的迷你游戏<span style=\"line-height: 1.6;\"><br/>" +
                "            ※</span>全员生存状态10回合以内胜出解锁" +
                "</td>");
        mCompiler.compileCatchCondition(builder, monster);
        System.out.println(monster);
    }

    @Test
    public void test() {
        List<Monster> list = mCompiler.readFile(new File(mDir, "s.html"));
        assertNotNull(list);
        System.out.println(list);
    }

    @Test
    public void compileTest() throws IOException {
        HashMap<String, String> map = mGson.fromJson(getReader(mShadowFile), HashMap.class);
        mCompiler.setMap(map);
        LinkedList<Monster> monsters = new LinkedList<>();
        for (File file : mDir.listFiles()) {
            monsters.addAll(mCompiler.readFile(file));
        }
        LinkedHashSet<String> set = new LinkedHashSet<>();
        for (Monster monster : monsters) {
            set.addAll(monster.getLocation());
        }
        File file = new File(mMonsterFile);
        mCompiler.write2Json(file, monsters);
        file = new File(mLocationFile);
        mCompiler.write2Json(file, set);
    }

    @NonNull
    private FileReader getReader(String file) throws FileNotFoundException {
        return new FileReader(file);
    }

    @Test
    public void makeRepository() throws IOException {
        Type type = new TypeToken<LinkedList<Monster>>() {
        }.getType();
        DataRepository repository = new DataRepository();
        LinkedList<Monster> monsters = mGson.fromJson(getReader(mMonsterFile), type);
        final List<String> locations = mGson.fromJson(getReader(mLocationFile), ArrayList.class);
//        repository.setLocation(locations);
        setGroup(monsters, repository);
        mCompiler.write2Json(new File(mRepositoryFile), repository);
    }

    private void setGroup(LinkedList<Monster> monsters, DataRepository repository) {
        int index = 0;
        Map<String, Integer> map = new HashMap<>();
        List<MonsterGroup> groups = new ArrayList<>();
        Set<String> items = new HashSet<>();
        MonsterGroup group;
        for (Monster monster : monsters) {
            Integer integer = map.get(monster.getName());
            items.addAll(monster.getDrop());
            if (integer == null) {
                group = new MonsterGroup();
                groups.add(group);
                group.setId(index);
                group.addMonster(monster);
                monster.setGroup(index);
                for (String s : monster.getTransformTo()) {
                    map.put(s, index);
                }
                index++;
            } else {
                monster.setGroup(integer);
                groups.get(integer).addMonster(monster);
            }
        }
        ArrayList<Monster> temp = new ArrayList<>();
        for (MonsterGroup monsterGroup : groups) {
            List<Monster> list = monsterGroup.getList();
            if (list.size() == 1) {
                Monster monster = list.get(0);
                if (!monster.getTransformTo().isEmpty()) {
                    temp.add(monster);
                }
            }
        }
        System.out.println(temp);
        repository.setList(groups);
        repository.setItem(new ArrayList<>(items));
    }

    private void setId(LinkedList<Monster> monsters, final List<String> locations, Set<String> set) {
        for (Monster monster : monsters) {
            monster.setId(getIndex(locations, monster.getLocation()));
        }
        Collections.sort(monsters);
        int id = 1;
        for (Monster monster : monsters) {
            monster.setId(id);
            id++;
            set.addAll(monster.getDrop());
        }
    }

    private int getIndex(List<String> locations, List<String> location) {
        int index = Byte.MAX_VALUE;
        for (String s : location) {
            int i = locations.indexOf(s);
            if (i < index) {
                index = i;
            }
        }
        return index;
    }

    @Test
    public void getIcon() throws IOException {
        File file = new File(mRepositoryFile);
        FileReader reader = new FileReader(file);
        DataRepository repository = mGson.fromJson(reader, DataRepository.class);
        repository.init();
        for (Monster monster : repository.getMonsters().values()) {
            loadImg(monster);
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

    @Test
    public void setLocation() throws IOException {
        Type type = new TypeToken<ArrayList<LocationBean>>() {
        }.getType();
        String file = "locations.json";
        final List<LocationBean> locations = mGson.fromJson(getReader(file), type);
        int size = locations.size();
        for (int i = 0; i < size; i++) {
            locations.get(i).setId(i + 1);
        }
        Writer writer = new FileWriter(file);
        writer.write(mGson.toJson(locations));
        writer.close();
    }
}
