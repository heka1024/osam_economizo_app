package com.example.tooth;

import java.util.ArrayList;

import io.realm.RealmList;
import io.realm.RealmObject;

public class BasicData extends RealmObject {
    private RealmList<Integer> ints;
    private int current_money;
    private int used_money;

    public int getCurrent_money() {
        return current_money;
    }

    public void setCurrent_money(int current_money) {
        this.current_money = current_money;
    }

    public int getUsed_money() {
        return used_money;
    }

    public void setUsed_money(int used_money) {
        this.used_money = used_money;
    }
}
