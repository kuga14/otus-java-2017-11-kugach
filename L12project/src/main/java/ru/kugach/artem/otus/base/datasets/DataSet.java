package ru.kugach.artem.otus.base.datasets;

import javax.persistence.*;

@MappedSuperclass
public class DataSet {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id")
    private long id;

    DataSet(){

    }

    DataSet(long id){
        this.id=id;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
