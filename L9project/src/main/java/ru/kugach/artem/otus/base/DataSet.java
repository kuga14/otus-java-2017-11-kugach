package ru.kugach.artem.otus.base;

public abstract class DataSet{
    private final long id;

    public DataSet(long id) {
        this.id = id;
    }

    public DataSet() {
        this.id = 0;
    }

    public long getId() {
        return id;
    }

}
