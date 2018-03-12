package ru.kugach.artem.otus.base.datasets;

import javax.persistence.*;

@Entity
@Table(name="Phone")
public class PhoneDataSet extends DataSet{

    public PhoneDataSet(){

    }

    public PhoneDataSet(String phone){
        this.phone = phone;
    }

    @Column(name = "phone")
    private String phone;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone){
        this.phone=phone;
    }

    @Override
    public String toString() {
        return "PhoneDataSet{" +
                "id=" + getId() +
                ", phone='" + phone + '\'' +
                '}';
    }
}
