package ru.kugach.artem.otus;

public class CashInBucket extends CashBucket{

    CashInBucket() {
        super();
    }

    public void cashIn(Nominal nominal, int count) {
        int newCount = notes.getOrDefault(nominal, 0) + count;
        notes.put(nominal, newCount);
    }
}
