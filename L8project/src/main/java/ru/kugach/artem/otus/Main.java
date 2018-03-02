package ru.kugach.artem.otus;

import com.google.gson.Gson;

public class Main {

    public static void main(String... args){
        Gson gson = new Gson();
        MyJSON myjson = new MyJSON();
        Course course = new Course(1,"Algebra","Ivanov");
        Student student = new Student("Artem");
        student.setMark(course,new Integer("5"));
        System.out.println("Gson output: "+gson.toJson(student));
        System.out.println("MyJSON output: "+myjson.toJson(student));
        gson.fromJson(myjson.toJson(student),student.getClass());
        gson.fromJson(gson.toJson(student),student.getClass());
    }
}
