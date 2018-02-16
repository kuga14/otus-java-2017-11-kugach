package ru.kugach.artem.otus;

public class Main {

    final static int MIN_NOTES_COUNT = 500;
    final static int MAX_NOTES_COUNT = 1_000;
    final static int ATM_COUNT = 10;

    public static void main(String... args) {

        Department department = new Department(ATM_COUNT,MIN_NOTES_COUNT,MAX_NOTES_COUNT);

        System.out.println("Start ATM Department balance: " + department.getBalance());

        ATM nextATM = department.getRoot().next();
        int i=2;
        while( nextATM != null) {
            nextATM.withdraw(i*1200);
            System.out.println("Withdraw from " + i + " ATM " +": " + i*1200);
            nextATM = nextATM.next();
            i++;
        }
        System.out.println("New ATM Department balance: "+department.getBalance());

        department.restore();
        System.out.println("Restore ATM Department");
        System.out.println("New ATM Department balance: "+department.getBalance());
    }
}
