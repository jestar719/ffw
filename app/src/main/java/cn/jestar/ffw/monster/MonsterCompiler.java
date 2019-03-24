package cn.jestar.ffw.monster;


import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 花京院 on 2019/3/23.
 */

public class MonsterCompiler {
    public static final String TR_START = "<tr";
    public static final String TR_END = "</tr";
    public static final String TD_START = "<td";
    public static final String TD_END = "</td>";
    public static final String HTTP = "http";
    public static final String JPG = "jpg";
    public static final String SPAN_START = "<span";
    public static final String SPAN_END = "</span>";
    public static final String LEFT = "<";
    public static final String RIGHT = ">";
    private static final String P_START = "<p>";
    private static final String P_END = "</p>";
    private static final String DIVIDER = ",";
    private static final String BR = "<br";
    private static final String TRANSFORM = "（变身）";
    private static final String NULL = "-";
    private final Map<String, String> mMap;
    private Map<String, String> mShadowMap;

    public MonsterCompiler() {
        mMap = new HashMap<>();
        mMap.put("瓦尔法雷", "5幕BOSS");
        mMap.put("大魔狼", "6幕BOSS");
        mMap.put("炸弹王", "11幕BOSS");
        mMap.put("吸血鬼始祖", "17幕BOSS");
        mMap.put("提灯怪国王", "18幕BOSS");
        mMap.put("亚秋女王", "18幕BOSS");
        mMap.put("阿斯忒里翁", "19幕BOSS");
        mMap.put("吸血鬼始祖", "19幕BOSS");
        mMap.put("克拉肯", "19幕BOSS");
        mMap.put("提亚马特", "19幕BOSS");
        mMap.put("布耶尔", "17幕BOSS");
        mMap.put("伊弗利特", "斗技场");
        mMap.put("湿婆", "斗技场");
        mMap.put("拉姆", "斗技场");
        mMap.put("火伊利", "真结局通关后追加隐藏迷宫·左");
        mMap.put("火伊特利", "真结局通关后追加隐藏迷宫·左");
        mMap.put("湿婆列", "真结局通关后追加隐藏迷宫·右");
        mMap.put("湿婆利", "真结局通关后追加隐藏迷宫·右");
        mMap.put("霹雳霹雳", "真结局通关后追加隐藏迷宫·中");
        mMap.put("雷拉姆", "真结局通关后追加隐藏迷宫·中");
        mMap.put("玉藻姬", "真结局通关");
        mMap.put("九尾狐", "真结局通关");
        mMap.put("吸血鬼始祖", "斗技场");
        mMap.put("汪洋莫古力", "萨洛尼亚港口隐藏点");
        mMap.put("黑陆行鸟宝宝", "进击的平原隐藏");
        mMap.put("地狱三头犬", "翼龙溪谷隐藏点");
        mMap.put("仙人掌巨怪", "零号魔光炉隐藏点");
        mMap.put("斯芬克斯", "幻影沙漠隐藏点");
        mMap.put("泰坦", "水晶之塔隐藏点");
        mMap.put("铁巨人", "大桥隐藏点");
        mMap.put("梦魇", "列车坟场(黑块强敌)");
        mMap.put("凤凰", "第七峡谷隐藏点");
        mMap.put("布丁公主", "刮风湿地隐藏点");
        mMap.put("神圣龙", "冰柱悬崖隐藏点");
        mMap.put("黄金模仿怪", "灯火之森隐藏点");
        mMap.put("雾龙", "地底监狱隐藏点");
        mMap.put("极恶毛尔波尔", "刮风湿地隐藏点");
        mMap.put("哥布林公主", "心灵任务:逼婚篇");
        mMap.put("高贵活尸", "心灵任务:狠心人篇");
        mMap.put("金属骷髅的记忆", "心灵任务:怨念未消篇");
        mMap.put("坚壳玳瑁龟", "心灵任务:金银岛篇");
        mMap.put("模仿怪皇后", "心灵任务:我讨厌打雷篇");
        mMap.put("东方不败", "心灵任务:黑色陆行鸟篇");
        mMap.put("马丁", "心灵任务:五大魔震 费加罗篇");
        mMap.put("伊芙利妲", "心灵任务:炎之特训篇");
        mMap.put("湿伯", "心灵任务:冰冷的正义篇");
        mMap.put("拉缪", "心灵任务:五大魔震·科内利亚篇");
        mMap.put("东方不败", "心灵任务:黑色陆行鸟篇");
        mMap.put("东方不败", "心灵任务:黑色陆行鸟篇");
    }

    public void setMap(Map<String,String> map){
        mShadowMap=map;
    }

    public List<Monster> readFile(File file) {
        List<Monster> list = new ArrayList<>();
        String name = file.getName();
        name = name.replace(".html", "").toUpperCase();
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            StringBuilder builder = new StringBuilder();
            String temp;
            Monster monster = null;
            int tdIndex = 0;
            while ((temp = reader.readLine()) != null) {
                if (temp.contains(TR_START)) {
                    monster = new Monster();
                    monster.setSize(name);
                    list.add(monster);
                } else if (temp.contains(TD_START)) {
                    builder.setLength(0);
                    builder.append(temp);
                } else if (temp.contains(TD_END)) {
                    builder.append(temp);
                    compilerMonster(tdIndex, builder, monster);
                    tdIndex++;
                } else if (temp.contains(TR_END)) {
                    tdIndex = 0;
                } else {
                    builder.append(temp);
                }
            }
            reader.close();
        } catch (Exception e) {
            e.fillInStackTrace();
        }
        return list;
    }

    public void write2Json(File file, Object o) throws IOException {
        FileWriter writer = new FileWriter(file);
        writer.write(new Gson().toJson(o));
        writer.close();
    }


    private void compilerMonster(int tdIndex, StringBuilder builder, Monster monster) {
        switch (tdIndex) {
            case 0:
                compileTitle(builder, monster);
                break;
            case 1:
                compileName(builder, monster);
                break;
            case 2:
                compileLocation$Transform(builder, monster);
                break;
            case 3:
                compileCatchCondition(builder, monster);
                break;
            case 4:
                compilerDrop(builder, monster);
                break;
            default:
                break;
        }
    }

    private void compilerDrop(StringBuilder builder, Monster monster) {
        deleteTd(builder);
        deleteBr(builder);
        if (builder.indexOf(NULL) >= 0)
            return;
        int start = 0;
        int end;
        String text;
        while ((end = builder.indexOf(DIVIDER, start)) >= 0) {
            text = builder.substring(start, end);
            monster.addDrop(text.trim());
            start = end + 1;
        }
        text = builder.substring(start).trim();
        if (text.length() > 0)
            monster.addDrop(text);
    }

    public void compileCatchCondition(StringBuilder builder, Monster monster) {
        deleteTd(builder);
        deleteSpan(builder);
        deleteSpecialChar(builder);
        deleteP(builder);
        deleteBr(builder);
        String string = builder.toString().replace(" ", "");
        if (string.length() == 0 || NULL.equals(string))
            return;
        if (string.contains(DIVIDER)) {
            string = string.replace(DIVIDER, "");
        }
        monster.setCatchCondition(string);
    }

    public void compileLocation$Transform(StringBuilder builder, Monster monster) {
        deleteTd(builder);
        deleteSpan(builder);
        deleteSpecialChar(builder);
        deleteP(builder);
        deleteBr(builder);
        int start = 0;
        int end;
        while ((end = builder.indexOf(DIVIDER, start)) >= 0) {
            setMonster(builder.substring(start, end), monster);
            start = end + 1;
        }
        setMonster(builder.substring(start), monster);
    }

    private void deleteTd(StringBuilder builder) {
        deleteString(builder, TD_END);
        int i = builder.indexOf(RIGHT);
        builder.delete(0, i + 1);
    }

    private void setMonster(String text, Monster monster) {
        text = text.trim();
        if (text.length() > 0 && !NULL.equals(text)) {
            if (text.contains(TRANSFORM)) {
                monster.addTransformTo(text.replace(TRANSFORM, ""));
            } else {
                monster.addLocation(text);
            }
        }
    }

    private void deleteBr(StringBuilder builder) {
        int index = builder.indexOf(BR);
        if (index >= 0) {
            int end = builder.indexOf(RIGHT, index);
            builder.delete(index, end + 1);
            builder.insert(index, DIVIDER);
            deleteBr(builder);
        }
    }

    public void deleteP(StringBuilder builder) {
        if (builder.indexOf(P_START) < 0) {
            return;
        }
        deleteString(builder, P_START);
        replaceString(builder, P_END, DIVIDER);
        deleteP(builder);
    }

    private boolean replaceString(StringBuilder builder, String str, String replace) {
        int index = builder.indexOf(str);
        boolean canReplace = index >= 0;
        if (canReplace) {
            builder.delete(index, index + str.length());
            builder.insert(index, replace);
        }
        return canReplace;
    }

    public void deleteSpecialChar(StringBuilder builder) {
        if (builderDelete(builder, "&", ";"))
            return;
        deleteSpecialChar(builder);
    }

    public void deleteSpan(StringBuilder builder) {
        if (builderDelete(builder, SPAN_START, RIGHT))
            return;
        deleteString(builder, SPAN_END);
        deleteSpan(builder);
    }

    private void deleteString(StringBuilder builder, String str) {
        int start = builder.indexOf(str);
        builder.delete(start, start + str.length());
    }

    private boolean builderDelete(StringBuilder builder, String startS, String endS) {
        int start = builder.indexOf(startS);
        if (start < 0)
            return true;
        int end = builder.indexOf(endS, start);
        builder.delete(start, end + 1);
        return false;
    }

    public void compileName(StringBuilder builder, Monster monster) {
        deleteTd(builder);
        deleteSpan(builder);
        deleteP(builder);
        String name = builder.toString().trim();
        monster.setName(name);
        monster.setTransformCondition(mMap.get(name));
        if (mShadowMap!=null)
            monster.setShadowStone(mShadowMap.get(name));
    }

    public void compileTitle(StringBuilder builder, Monster monster) {
        int start = builder.indexOf(HTTP);
        int end = builder.indexOf(JPG, start);
        String url = builder.substring(start, end + JPG.length()).trim();
        monster.setUrl(url);
    }
}
