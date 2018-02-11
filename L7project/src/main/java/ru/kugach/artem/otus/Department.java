package ru.kugach.artem.otus;

import java.util.ArrayList;

public class Department {

    private ArrayList<ATM> atmList;

    Department(int countATM, int minCountNotes, int maxCountNotes) {
        atmList = new ArrayList<>();
        for (int i = 0; i < countATM; i++) {
            atmList.add(new ATM(minCountNotes, maxCountNotes));
        }
    }

    public int getBalance() {
        return atmList.stream().mapToInt(atm -> atm.getBalance()).sum();
    }

    public void restore() {
        atmList.stream().forEach(atm -> atm.restore());
    }

    public ATM getAtm(int index) {
        return atmList.get(index);
    }
}
