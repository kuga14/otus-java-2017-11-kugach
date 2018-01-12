package ru.kugach.artem.otus;

public class Calc {

        public int getSum(int x, int y) {
            return x+y;
        }

        public int getDivide(int x, int y) throws ArithmeticException{
            return x/y;
        }

        public int getMultiple(int x, int y) {
            return x*y;
        }
}
