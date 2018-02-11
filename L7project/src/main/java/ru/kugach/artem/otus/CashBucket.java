package ru.kugach.artem.otus;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public abstract class CashBucket implements Cloneable{

    protected Map<Nominal,Integer> notes;

    CashBucket(){
        super();
        notes = new HashMap<>();
    }

    public int getBalance(){
        return notes.entrySet()
                .stream()
                .mapToInt(entry -> entry.getKey().getValue() * entry.getValue())
                .sum();
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        CashBucket clone = (CashBucket) super.clone();
        clone.notes = new HashMap<>();
        this.notes.entrySet().stream().forEach((entry) -> clone.notes.put(entry.getKey(),entry.getValue()));
        return clone;
    }

}

