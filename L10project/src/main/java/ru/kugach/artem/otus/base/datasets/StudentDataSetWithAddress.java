package ru.kugach.artem.otus.base.datasets;

import javax.persistence.*;

@Entity
@Table(name="StudentWithAddress")
public class StudentDataSetWithAddress extends DataSet{

    @Column(name = "name")
    private String name;

    @OneToOne(cascade =CascadeType.ALL)
    @JoinColumn(name="address_id",referencedColumnName="id")
    private AddressDataSet address;


    public StudentDataSetWithAddress() {

    }

    public StudentDataSetWithAddress(String name) {
        this.name = name;
    }


    public AddressDataSet getAddress() {
        return address;
    }

    public void setAddress(AddressDataSet address){
        this.address = address;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "StudentDataSet{" +
                "id=" + getId() +
                ", name='" + name + '\'' +
                ", street=" + getAddress().toString() +
                '}';
    }

    @Override
    public boolean equals(Object obj) {

        if (obj == this) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof StudentDataSetWithAddress)) {
            return false;
        }
        StudentDataSetWithAddress s = (StudentDataSetWithAddress) obj;
        return name.equals(s.name);
    }
}

