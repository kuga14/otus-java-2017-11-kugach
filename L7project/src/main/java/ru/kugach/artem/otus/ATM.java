package ru.kugach.artem.otus;

import java.util.*;

public class ATM implements  Cloneable{

    private CashInBucket cashInBucket;
    private SortedSet<CashOutBucket> cashOutBuckets;
    private Memento memento;
    private ATM next;

    ATM(int minInitCount, int maxInitCount) {
        this.cashInBucket = new CashInBucket();
        this.cashOutBuckets = new TreeSet<>();
        initAtm(minInitCount, maxInitCount);
        saveState();
    }

    private void initAtm(int minInitCount, int maxInitCount) {
        Random r = new Random();
        for (Nominal n : Nominal.values()) {
            this.setCashOutBucket(new CashOutBucket(n, r.ints(minInitCount, maxInitCount + 1).findFirst().getAsInt()));
        }
    }

    private void saveState() {
        memento = new Memento();
    }

    public ATM next(){
        return next;
    }


    public void add(ATM atm) {
        if (next == null) {
            next = atm;
        } else {
            next.add(atm);
        }
    }

    public void restore() {
        cashInBucket = memento.getCashInBucketState();
        cashOutBuckets = memento.getCashOutBucketsState();
        if (next != null) {
            next.restore();
        }
    }

    private class Memento {
        private CashInBucket cashInBucketState;
        private SortedSet<CashOutBucket> cashOutBucketsState;

        Memento() {
            try {
                cashInBucketState = (CashInBucket) cashInBucket.clone();
                cashOutBucketsState = new TreeSet<>();
                for (CashOutBucket cashOutBucket : cashOutBuckets) {
                    cashOutBucketsState.add((CashOutBucket) cashOutBucket.clone());
                }
            } catch (CloneNotSupportedException e) {
                e.printStackTrace();
            }
        }

        public CashInBucket getCashInBucketState() {
            return cashInBucketState;
        }

        public SortedSet<CashOutBucket> getCashOutBucketsState() {
            return cashOutBucketsState;
        }
    }

    private void setCashOutBucket(CashOutBucket cashOutBucket) {
        this.cashOutBuckets.add(cashOutBucket);
    }

    public int getPublicBalance() {
        return cashOutBuckets.stream().mapToInt(CashOutBucket::getBalance).sum();
    }

    public int getBalance() {
        return getPublicBalance() + cashInBucket.getBalance();
    }

    public void deposit(Nominal nominal, int count) {
        cashInBucket.cashIn(nominal, count);
    }

    public Map<Nominal, Integer> withdraw(final int amount) {
        Map<Nominal, Integer> withdrawal = new HashMap<>();
        int remain = amount;
        int notes;
        Nominal currentNominal;
        for (CashOutBucket cashOutBucket : cashOutBuckets) {
            notes = cashOutBucket.getCount();
            if (notes > 0) {
                currentNominal = cashOutBucket.getNominal();
                int notesNeeded = remain / currentNominal.getValue();
                int notesToCashOut = Math.min(notes, notesNeeded);
                withdrawal.put(currentNominal, notesToCashOut);
                remain -= currentNominal.getValue() * notesToCashOut;
            }
        }
        if (remain > 0) {
            throw new RuntimeException("This operation can not be performed");
        }
        for (CashOutBucket cashOutBucket : cashOutBuckets) {
            currentNominal = cashOutBucket.getNominal();
            cashOutBucket.cashOut(currentNominal, withdrawal.get(currentNominal));
        }
        return withdrawal;
    }
}
