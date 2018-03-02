package ru.kugach.artem.otus;

import java.util.Objects;

public class Course {
    int id;
    String name;
    transient String teacher;

    public Course(int id,String name,String teacher){
        this.id = id;
        this.name = name;
        this.teacher = teacher;
    }

    public Course(int id, String name){
        this.id = 0;
        this.name = name;
    }

    public Course(){
        this.id = 0;
        this.name = "Base";
        this.teacher = "Unknown";
    }

    @Override
    public boolean equals(Object obj) {

        if (obj == this) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof Course)) {
            return false;
        }
        Course c = (Course) obj;
        return id==c.id && name.equals(c.name) && teacher.equals(c.teacher);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id,name, teacher);
    }
}
