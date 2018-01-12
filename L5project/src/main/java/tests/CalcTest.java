package tests;

import ru.kugach.artem.otus.Calc;
import ru.kugach.artem.otus.annotations.After;
import ru.kugach.artem.otus.annotations.Before;
import ru.kugach.artem.otus.annotations.Test;

public class CalcTest {

    private Calc calculator;

    @Before
    public void initTest() {
        calculator = new Calc();
    }

    @After
    public void afterTest() {
        calculator = null;
    }

    @Test
    public void testSum() throws Exception {
        assert (20 == calculator.getSum(14, 6));
        System.out.println("*** Success");
    }

    @Test
    public void testDivide() throws Exception {
        assert (20 == calculator.getDivide(100, 5));
        System.out.println("*** Success");
    }

    @Test
    public void testMultiple() throws Exception {
        assert (20 == calculator.getMultiple(5, 4));
        System.out.println("*** Success");
    }
}
