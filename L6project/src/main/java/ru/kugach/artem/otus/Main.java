package ru.kugach.artem.otus;
import java.util.Map;

import static ru.kugach.artem.otus.Nominal.*;

public class Main {

    final static int minCount = 500;
    final static int maxCount = 1_000;

    public static void main(String... args) {

        ATM atm = new ATM(minCount,maxCount);

        int startBalance = atm.getBalance();
        System.out.println("Start balance: " + startBalance);
        System.out.println("Deposit: 1700");
        atm.deposit(ONE_HUNDRED,2 );
        atm.deposit(FIVE_HUNDREDS,3 );
        System.out.println("New balance: " + atm.getBalance());

        Map<Nominal, Integer> withdrawal = atm.withdraw(5700);
        System.out.println("Withdraw: 5700");
        withdrawal.entrySet().stream().forEach((entry)-> System.out.println( entry.getValue()+ " x " + entry.getKey().getValue()+"$"));
        System.out.println("New balance: " + atm.getBalance());

        try{
            int notPerformedSum = startBalance-5700+100;
            System.out.println("CashOutBalance: "+atm.getPublicBalance());
            System.out.println("Withdraw: " + notPerformedSum);
            atm.withdraw(notPerformedSum);
        } catch(RuntimeException re){
            System.out.println(re.getMessage());
        }
    }
}
