package ru.kugach.artem.otus;

public class Department {

    private ATM root;

    public ATM getRoot(){
        return root;
    }

    Department(int countATM, int minCountNotes, int maxCountNotes) {
        root = new ATM(minCountNotes, maxCountNotes);
        for (int i = 0; i < countATM; i++) {
            root.add(new ATM(minCountNotes, maxCountNotes));
        }
    }

    public int getBalance() {
        int sum = root.getBalance();
        ATM nextATM = root.next();
        while( nextATM != null) {
            sum+= nextATM.getBalance();
            nextATM = nextATM.next();
        }
        return sum;
    }

    public void restore() {
        root.restore();
    }
}
