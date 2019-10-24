package com.example.tooth;

import java.util.ArrayList;

public class SingletonInts {
    private static SingletonInts singletonInts = null;
    public static ArrayList<Integer> ints;

    private SingletonInts() {
        ints = new ArrayList<>();
    }

    public static SingletonInts getInts() {
        if (singletonInts == null) {
            singletonInts = new SingletonInts();
        }
        return singletonInts;
    }

    public void add(int elem) {
        try {
            ints.add(elem);
        } catch (NullPointerException e) {
            ints = new ArrayList<>();
            ints.add(elem);
        }
    }

    public ArrayList<Integer> getElem() {
        return ints;
    }

    public void clear() {
        ints.clear();
        ints = new ArrayList<>();
    }
}
