package ru.kugach.artem.otus;

public class Main {

    public static void main(String... args){
        MyUnitFramework junit = new MyUnitFramework();
        junit.runPackage("tests");
        System.out.println("-------------------------------------------");
        junit.runClasses(CalcTest.class);
    }
}
