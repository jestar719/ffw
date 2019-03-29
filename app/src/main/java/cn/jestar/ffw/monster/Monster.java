package cn.jestar.ffw.monster;


import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

/**
 * 怪物数据
 * Created by 花京院 on 2019/3/23.
 */

public class Monster implements Comparable<Monster> {
    private int id;
    private String name;
    //组别（可互变身的为一组）
    private int group;
    //地点
    private List<String> location;
    //捕获条件
    private String catchCondition;
    //变身成当前怪物的条件
    private String transformCondition;
    private List<String> transformTo;
    private String size;
    private String url;
    private List<String> drop;
    private String shadowStone;
    private int transType;

    public List<String> getDrop() {
        return drop;
    }

    public void setDrop(List<String> drop) {
        this.drop = drop;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public List<String> getTransformTo() {
        return transformTo;
    }

    public void setTransformTo(List<String> transformTo) {
        this.transformTo = transformTo;
    }

    public void addTransformTo(String string) {
        transformTo.add(string);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getGroup() {
        return group;
    }

    public void setGroup(int group) {
        this.group = group;
    }

    public List<String> getLocation() {
        return location;
    }

    public void setLocation(List<String> location) {
        this.location = location;
    }

    public String getCatchCondition() {
        return catchCondition;
    }

    public void setCatchCondition(String catchCondition) {
        this.catchCondition = catchCondition;
    }

    public String getTransformCondition() {
        return transformCondition;
    }

    public void setTransformCondition(String transformCondition) {
        this.transformCondition = transformCondition;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public void addLocation(String text) {
        if (location == null) {
            location = new ArrayList<>();
        }
        location.add(text);
    }

    @Override
    public String toString() {
        return "Monster{" +
                "name='" + name + '\'' +
                ", size='" + size + '\'' +
                "location='" + location + '\'' +
                ", transformTo=" + transformTo +
                ", catchCondition=" + catchCondition +
                '}';
    }

    public void addDrop(String drop) {
        this.drop.add(drop);
    }

    public String getShadowStone() {
        return shadowStone;
    }

    public void setShadowStone(String shadowStone) {
        this.shadowStone = shadowStone;
    }

    @Override
    public int compareTo(@NonNull Monster o) {
        return id - o.getId();
    }

    public int getTransType() {
        return transType;
    }

    public void setTransType(int transType) {
        this.transType = transType;
    }
}
