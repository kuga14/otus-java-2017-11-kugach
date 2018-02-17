package ru.kugach.artem.otus;

public class WithdrawCommand implements Command {

    private ATM atm;
    private int amount;

    public WithdrawCommand(ATM atm , int amount){
        this.amount = amount;
        this.atm = atm;
    }

    @Override
    public void execute() {
        atm.withdraw(amount);
        System.out.println("Withdraw from " + atm.hashCode() + " ATM " +": " + amount);
    }
}
