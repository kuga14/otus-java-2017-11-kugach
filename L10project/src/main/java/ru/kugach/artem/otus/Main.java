package ru.kugach.artem.otus;

import ru.kugach.artem.otus.base.DBService;
import ru.kugach.artem.otus.base.datasets.*;
import ru.kugach.artem.otus.dbservice.DBServiceHibernateImpl;
import ru.kugach.artem.otus.dbservice.DBServiceImpl;

import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String... args) throws Exception {
        new Main().run();
    }

    private void run() throws Exception {
        try(DBService dbService = new DBServiceImpl()) {
            System.out.println(dbService.getMetaData());
            StudentDataSet student = new StudentDataSet();
            student.setName("Artem");
            dbService.save(student);
            StudentDataSet student1 = dbService.load(1,student.getClass());
            System.out.println(student1.toString());
        }
        System.out.println("Hibernate...");
        try(DBService dbService = new DBServiceHibernateImpl()){
            StudentDataSet student = new StudentDataSet("Artem");
            dbService.save(student);
            StudentDataSet student1 = dbService.load(1,student.getClass());
            System.out.println(student1.toString());
        }
        System.out.println("Hibernate one to one...");
        try(DBService dbService = new DBServiceHibernateImpl()){
            StudentDataSetWithAddress student = new StudentDataSetWithAddress("Artem");
            student.setAddress(new AddressDataSet("SPB"));
            dbService.save(student);
            StudentDataSetWithAddress student1 = dbService.load(1,student.getClass());
            System.out.println(student1.toString());
        }
        System.out.println("Hibernate one to many...");
        try(DBService dbService = new DBServiceHibernateImpl()){
            StudentDataSetWithAddressPhones student = new StudentDataSetWithAddressPhones("Artem");
            student.setAddress(new AddressDataSet("SPB"));
            List<PhoneDataSet> phones = new ArrayList<>();
            phones.add(new PhoneDataSet("88121112233"));
            phones.add(new PhoneDataSet("88121112244"));
            student.setPhones(phones);
            dbService.save(student);
            StudentDataSetWithAddressPhones student1 = dbService.load(1,student.getClass());
            System.out.println(student1.toString());
        }
    }
}
