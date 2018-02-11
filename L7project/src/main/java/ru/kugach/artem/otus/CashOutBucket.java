package ru.kugach.artem.otus;

import java.util.Map;

public class CashOutBucket extends CashBucket implements Comparable<CashOutBucket> {

    private CashOutBucket() {
        super();
    }

    CashOutBucket(Nominal nominal, int initCount) {
        super();
        notes.put(nominal, initCount);
    }

    public void cashOut(Nominal nominal, int count) {
        if (validate(nominal)) {
            int newCount = notes.get(nominal) - count;
            notes.put(nominal, newCount);
        }
    }

    private boolean validate(Nominal nominal) {
        return notes.containsKey(nominal);
    }

    public Nominal getNominal() {
        return notes.entrySet().stream().findFirst().get().getKey();
    }

    public int getCount() {
        return notes.get(getNominal());
    }

    @Override
    public int compareTo(CashOutBucket o) {
        if (o.equals(null))
            throw new NullPointerException();
        return o.getNominal().getValue() - this.getNominal().getValue();
    }
}
