package ru.kugach.artem.otus;

import java.util.HashMap;
import java.util.Map;

public class CashInBucket{

    private Map<Nominal,Integer> notes;

    CashInBucket() {
        notes = new HashMap<>();
    }

    public void cashIn(Nominal nominal,int count) {
        int newCount = notes.getOrDefault(nominal,0)+count;
        notes.put(nominal,newCount);
    }

    public int getBalance() {
        return notes.entrySet()
                .stream()
                .mapToInt(entry -> entry.getKey().getValue() * entry.getValue())
                .sum();
    }
}
