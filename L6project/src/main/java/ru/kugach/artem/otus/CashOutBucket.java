package ru.kugach.artem.otus;

public class CashOutBucket implements Comparable<CashOutBucket>{

    private Nominal nominal;
    private int count;

    CashOutBucket(Nominal nominal, int initCount) {
        this.count = initCount;
        this.nominal = nominal;
    }

    public void cashOut(Nominal nominal, int count) {
        if(validate(nominal)){
            this.count-=count;
        }
    }

    private boolean validate(Nominal nominal) {

        return this.nominal.equals(nominal);
    }

    public Nominal getNominal() {

        return nominal;
    }

    public int getCount() {

        return count;
    }

    public int getBalance(){

        return count * nominal.getValue();
    }

    @Override
    public int compareTo(CashOutBucket o) {
        if(o.equals(null))
            throw new NullPointerException();
        return o.getNominal().getValue() - this.getNominal().getValue();
    }
}
