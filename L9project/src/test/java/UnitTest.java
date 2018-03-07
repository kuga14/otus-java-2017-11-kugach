import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.kugach.artem.otus.DBServiceT;
import ru.kugach.artem.otus.Student;
import static org.junit.Assert.assertEquals;

public class UnitTest {

    private Student student;

    @Before
    public void init(){
        student = new Student();
        student.setName("Artem");
        student.setAge(25);

    }

    @Test
    public void studentNullTest(){
        try(DBServiceT dbService = new DBServiceT()) {
            dbService.save(student);
            assertEquals(
                    null,
                            dbService.load(2,student.getClass())
            );
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Test
    public void studentTest(){
        try(DBServiceT dbService = new DBServiceT()) {
            dbService.save(student);
            assertEquals(
                    student,
                            dbService.load(1,student.getClass())
            );
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @After
    public void after(){
        student = null;
    }
}
