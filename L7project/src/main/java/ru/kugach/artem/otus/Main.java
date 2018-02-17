package ru.kugach.artem.otus;

import java.util.Random;

public class Main {

    final static int MIN_NOTES_COUNT = 500;
    final static int MAX_NOTES_COUNT = 1_000;
    final static int ATM_COUNT = 10;

    public static void main(String... args) {

        Department department = new Department(ATM_COUNT,MIN_NOTES_COUNT,MAX_NOTES_COUNT);
        System.out.println("Start ATM Department balance: " + department.getBalance());

        Random r = new Random();
        ATM atm = department.getRoot();
        while(atm != null){
            new WithdrawCommand(atm,r.ints(1, 1_000).findFirst().getAsInt() * 100).execute();
            atm = atm.next();
        }
        System.out.println("New ATM Department balance: "+department.getBalance());

        department.restore();
        System.out.println("Restore ATM Department");
        System.out.println("New ATM Department balance: "+department.getBalance());
    }
}
