package cn.jestar.ffw.monster;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 花京院 on 2019/3/23.
 */

public class DataRepository {
    private List<MonsterGroup> list = new ArrayList<>();
    private List<String> location = new ArrayList<>();
    private List<String> item = new ArrayList<>();
    private Map<String, Monster> monsters = new HashMap<String, Monster>();

    public void init() {
        for (MonsterGroup group : list) {
            for (Monster m : group.getList()) {
                monsters.put(m.getName(), m);
            }
        }
    }

    public Map<String, Monster> getMonsters() {
        return monsters;
    }

    public void setMonsters(Map<String, Monster> monsters) {
        this.monsters = monsters;
    }

    public Monster getMonster(String name) {
        return monsters.get(name);
    }


    public List<MonsterGroup> getList() {
        return list;
    }

    public void setList(List<MonsterGroup> list) {
        this.list = list;
    }

    public List<String> getLocation() {
        return location;
    }

    public void setLocation(List<String> location) {
        this.location = location;
    }

    public List<String> getItem() {
        return item;
    }

    public void setItem(List<String> item) {
        this.item = item;
    }
}
