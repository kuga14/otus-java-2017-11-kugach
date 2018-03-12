package ru.kugach.artem.otus.base.datasets;

import javax.persistence.*;

@Entity
@Table(name="StudentHibernate")
public class StudentDataSet extends DataSet{

    @Column(name = "name")
    private String name;

    public StudentDataSet() {

    }

    public StudentDataSet(String name) {
        this.name = name;
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
                "}";
    }

    @Override
    public boolean equals(Object obj) {

        if (obj == this) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof StudentDataSet)) {
            return false;
        }
        StudentDataSet s = (StudentDataSet) obj;
        return name.equals(s.name);
    }
}

