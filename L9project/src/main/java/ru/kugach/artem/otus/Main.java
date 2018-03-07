package ru.kugach.artem.otus;

public class Main {
    public static void main(String... args) throws Exception {
        new Main().run();
    }

    private void run() throws Exception {
        try(DBServiceT dbService = new DBServiceT()) {
            System.out.println(dbService.getMetaData());
            Student student = new Student(0L);
            student.setAge(25);
            student.setName("Artem");
            dbService.save(student);
            System.out.println(dbService.load(1,student.getClass()).equals(student));
        }
    }
}
