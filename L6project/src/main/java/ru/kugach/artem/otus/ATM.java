package ru.kugach.artem.otus;

import java.util.*;

public class ATM {

    private CashInBucket cashInBucket;
    private SortedSet<CashOutBucket> cashOutBuckets;

    ATM(int minInitCount,int maxInitCount){
        this.cashInBucket = new CashInBucket();
        this.cashOutBuckets = new TreeSet<>();
        initAtm(minInitCount,maxInitCount);
    }

    private void initAtm(int minInitCount,int maxInitCount){
        Random r = new Random();
        for (Nominal n : Nominal.values()) {
            this.setCashOutBucket( new CashOutBucket(n, r.ints(minInitCount, maxInitCount+1).findFirst().getAsInt()));
        }
    }

    private void setCashOutBucket(CashOutBucket cashOutBucket) {

        this.cashOutBuckets.add(cashOutBucket);
    }

    public int getPublicBalance() {

        return cashOutBuckets.stream().mapToInt(CashOutBucket::getBalance).sum();
    }

    public int getBalance(){

        return getPublicBalance() + cashInBucket.getBalance();
    }

    public void deposit(Nominal nominal, int count) {

        cashInBucket.cashIn(nominal,count);
    }

    public Map<Nominal,Integer> withdraw(final int amount) {
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
        for(CashOutBucket cashOutBucket : cashOutBuckets){
            currentNominal = cashOutBucket.getNominal();
            cashOutBucket.cashOut(currentNominal, withdrawal.get(currentNominal));
        }
        return withdrawal;
    }
}
