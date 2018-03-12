package ru.kugach.artem.otus.base.datasets;

import javax.persistence.*;

@Entity
@Table(name="Address")
public class AddressDataSet extends DataSet{

    public AddressDataSet(){

    }

    public AddressDataSet(String street){
        this.street = street;
    }



    @Column(name = "street")
    private String street;

    public String getStreet() {
        return street;
    }

    public void setStreet(String street){
        this.street=street;
    }

    @Override
    public String toString() {
        return "AddressDataSet{" +
                "id=" + getId() +
                ", street='" + street + '\'' +
                '}';
    }
}
