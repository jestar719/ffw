package cn.jestar.ffw.monster;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 花京院 on 2019/3/23.
 */

public class MonsterGroup {
    private int id;
    private List<Monster> list = new ArrayList<>();

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void addMonster(Monster monster) {
        list.add(monster);
    }

    public List<Monster> getList() {
        return list;
    }

    public void setList(List<Monster> list) {
        this.list = list;
    }
}
