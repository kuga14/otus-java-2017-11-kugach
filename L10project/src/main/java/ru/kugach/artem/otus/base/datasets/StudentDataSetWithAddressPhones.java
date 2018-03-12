package ru.kugach.artem.otus.base.datasets;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="StudentWithAddressPhones")
public class StudentDataSetWithAddressPhones extends DataSet{

    @Column(name = "name")
    private String name;

    @OneToOne(cascade =CascadeType.ALL)
    @JoinColumn(name="address_id",referencedColumnName="id")
    private AddressDataSet address;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name="student_id", referencedColumnName="id")
    private List<PhoneDataSet> phones;

    public StudentDataSetWithAddressPhones() {

    }

    public StudentDataSetWithAddressPhones(String name) {
        this.name = name;
        phones = new ArrayList<>();
    }


    public AddressDataSet getAddress() {
        return address;
    }

    public void setAddress(AddressDataSet address){
        this.address = address;
    }

    public List<PhoneDataSet> getPhones() {
        return phones;
    }

    public void setPhones(List<PhoneDataSet> phones) {
        this.phones = phones;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        boolean first = true;
        for(PhoneDataSet phone : getPhones()){
            if(first){
                first = false;
            } else {
                sb.append(",");
            }
            sb.append(phone.toString());
        }
        return "StudentDataSet{" +
                "id=" + getId() +
                ", name='" + name + '\'' +
                ", street=" + getAddress().toString() +
                ", phones=[" + sb.toString()+
                "]"+
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
        if (!(obj instanceof StudentDataSetWithAddressPhones)) {
            return false;
        }
        StudentDataSetWithAddressPhones s = (StudentDataSetWithAddressPhones) obj;
        return name.equals(s.name);
    }
}

