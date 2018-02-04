package ru.kugach.artem.otus;

import java.util.HashMap;
import java.util.Map;

public abstract class CashBucket {

    protected Map<Nominal,Integer> notes;

    CashBucket(){
        notes = new HashMap<>();
    }

    public int getBalance(){
        return notes.entrySet()
                .stream()
                .mapToInt(entry -> entry.getKey().getValue() * entry.getValue())
                .sum();
    }
}
