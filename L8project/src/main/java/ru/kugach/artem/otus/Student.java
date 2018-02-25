package ru.kugach.artem.otus;

import java.util.*;

public class Student {
    String name;
    private Map<Course,Integer> marks;

    public Student(){
        marks = new HashMap<>();
    }

    public void setMark(Course course,Integer mark){
        marks.put(course,mark);
    }
}
