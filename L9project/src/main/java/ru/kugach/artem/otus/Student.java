package ru.kugach.artem.otus;

import ru.kugach.artem.otus.base.DataSet;

public class Student extends DataSet{
    private int age;
    private String name;

    public Student(Long id){
        super(id);
    }

    public Student(){
        super(0);
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }


    @Override
    public String toString() {
        return "id: "+getId()+";age: "+age+";name: "+name;
    }

    @Override
    public boolean equals(Object obj) {

        if (obj == this) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof Student)) {
            return false;
        }
        Student s = (Student) obj;
        return name.equals(s.name) && age==s.age;
    }
}
