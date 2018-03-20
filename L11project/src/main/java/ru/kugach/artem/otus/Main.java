package ru.kugach.artem.otus;

import ru.kugach.artem.otus.base.DBService;
import ru.kugach.artem.otus.base.datasets.StudentDataSet;
import ru.kugach.artem.otus.cache.CacheEngineImpl;
import ru.kugach.artem.otus.dbservice.DBServiceImpl;

public class Main {

    private static final int MAX = 1000;

    public static void main(String... args) throws Exception {
        new Main().run();
    }

    private void run() throws Exception {
        try (DBService dbService = new DBServiceImpl(new CacheEngineImpl(MAX/2,MAX,0,false))) {
            System.out.println(dbService.getMetaData());
            StudentDataSet student;
            for (int i = 1; i < MAX; i++) {
                student = new StudentDataSet();
                student.setName("Artem" + i);
                dbService.save(student);
            }
            for (int i = 1; i < MAX; i++) {
                System.out.println(dbService.load(i, StudentDataSet.class).toString());
            }
        }
    }
}
