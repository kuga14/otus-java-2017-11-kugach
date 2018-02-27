package ru.kugach.artem.otus;

import java.util.*;

public class Student {
    String name;
    private Map<Integer,Course> marks;

    public Student(String name){
        this.name = name;
        marks = new HashMap<>();
    }

    public Student(){
        this.name = "Default";
        marks = new HashMap<>();
    }

    public void setMark(Course course,Integer mark){
        marks.put(mark,course);
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
        return name.equals(s.name) && marks.equals(s.marks);
    }


}
