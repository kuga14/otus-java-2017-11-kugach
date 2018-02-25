import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.kugach.artem.otus.Course;
import ru.kugach.artem.otus.MyJSON;
import ru.kugach.artem.otus.Student;

import java.lang.reflect.Type;
import java.util.*;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

public class UnitTest {

    private Gson gson;
    private MyJSON myjson;

    private Course course;
    private Student student;
    private static final int SIZE = 1_000;

    @Before
    public void initTest() {
        gson = new Gson();
        myjson = new MyJSON();
        course = new Course(1,"Algebra","Ivanov");
        student = new Student();
    }

    @After
    public void afterTest() {
        gson = null;
        myjson = null;
    }

    @Test
    public void deserializationPrimitiveValue() {
        boolean b=true;
        byte bt=0;
        short s=0;
        int i=0;
        long l=0;
        float f=0f;
        double d=0d;
        char c='0';

        // assert statements
        assertEquals(gson.fromJson(gson.toJson(b),boolean.class), gson.fromJson(myjson.toJSON(b),boolean.class));
        assertEquals(gson.fromJson(gson.toJson(bt),byte.class), gson.fromJson(myjson.toJSON(bt),byte.class));
        assertEquals(gson.fromJson(gson.toJson(s),short.class), gson.fromJson(myjson.toJSON(s),short.class));
        assertEquals(gson.fromJson(gson.toJson(i),int.class), gson.fromJson(myjson.toJSON(i),int.class));
        assertEquals(gson.fromJson(gson.toJson(l),long.class), gson.fromJson(myjson.toJSON(l),long.class));
        assertEquals(gson.fromJson(gson.toJson(d),double.class), gson.fromJson(myjson.toJSON(d),double.class));
        assertEquals(gson.fromJson(gson.toJson(f),float.class), gson.fromJson(myjson.toJSON(f),float.class));
        assertEquals(gson.fromJson(gson.toJson(c),char.class), gson.fromJson(myjson.toJSON(f),char.class));
    }

    @Test
    public void deserializationWrapperValue() {
        Boolean b=new Boolean(true);
        Byte bt=new Byte((byte) 0);
        Short s=new Short((short)0);
        Integer i=new Integer(0);
        Long l=new Long(0L);
        Float f=new Float(0F);
        Double d=new Double(0D);
        Character c=new Character('0');

        // assert statements
        assertEquals(gson.fromJson(gson.toJson(b),Boolean.class), gson.fromJson(myjson.toJSON(b),Boolean.class));
        assertEquals(gson.fromJson(gson.toJson(bt),Byte.class), gson.fromJson(myjson.toJSON(bt),Byte.class));
        assertEquals(gson.fromJson(gson.toJson(s),Short.class), gson.fromJson(myjson.toJSON(s),Short.class));
        assertEquals(gson.fromJson(gson.toJson(i),Integer.class), gson.fromJson(myjson.toJSON(i),Integer.class));
        assertEquals(gson.fromJson(gson.toJson(l),Long.class), gson.fromJson(myjson.toJSON(l),Long.class));
        assertEquals(gson.fromJson(gson.toJson(d),Double.class), gson.fromJson(myjson.toJSON(d),Double.class));
        assertEquals(gson.fromJson(gson.toJson(f),Float.class), gson.fromJson(myjson.toJSON(f),Float.class));
        assertEquals(gson.fromJson(gson.toJson(c),Character.class), gson.fromJson(myjson.toJSON(f),Character.class));
    }

    @Test
    public void deserializationStringValue() {
        String str="String Example";
        assertEquals(gson.fromJson(gson.toJson(str),String.class), gson.fromJson(myjson.toJSON(str),String.class));
    }

    @Test
    public void deserializationArrayOfPrimitives() {
        Random r=new Random();
        int[] ints=new int[SIZE];
        for (int i=0;i<SIZE;i++){
            ints[i] = r.ints(Integer.MIN_VALUE,Integer.MAX_VALUE).findFirst().getAsInt();
        }
        assertArrayEquals(gson.fromJson(gson.toJson(ints),ints.getClass()), gson.fromJson(myjson.toJSON(ints),ints.getClass()));
    }

    @Test
    public void deserializationArrayOfWrapper() {
        Random r=new Random();
        Integer[] ints=new Integer[SIZE];
        for (int i=0;i<SIZE;i++){
            ints[i] = new Integer(r.ints(Integer.MIN_VALUE,Integer.MAX_VALUE).findFirst().getAsInt());
        }
        assertArrayEquals(gson.fromJson(gson.toJson(ints),ints.getClass()), gson.fromJson(myjson.toJSON(ints),ints.getClass()));
    }

    @Test
    public void deserializationArrayOfString() {
        String[] strs=new String[SIZE];
        for (int i=0;i<SIZE;i++){
            strs[i] = new String("value:"+i);
        }
        assertArrayEquals(gson.fromJson(gson.toJson(strs),strs.getClass()), gson.fromJson(myjson.toJSON(strs),strs.getClass()));
    }

    @Test
    public void deserializationCollection() {
        Type itemsSetType = new TypeToken<Set<String>>() {}.getType();
        Set<String> collectionSet = new LinkedHashSet();
        for (int i=0;i<SIZE;i++){
            collectionSet.add(new String("value:"+i));
        }
        Set<String> collectionSetFromGSON = gson.fromJson(gson.toJson(collectionSet),itemsSetType);
        Set<String> collectionSetFromMYJSON = gson.fromJson(myjson.toJSON(collectionSet),itemsSetType);
        assertEquals(collectionSetFromGSON, collectionSetFromMYJSON);
        collectionSet=null;
        collectionSetFromGSON=null;
        collectionSetFromMYJSON=null;

        Type itemsListType = new TypeToken<List<String>>() {}.getType();
        List<String> collectionList = new ArrayList<>();
        for (int i=0;i<SIZE;i++){
            collectionList.add(new String("value:"+i));
        }
        List<String> collectionListFromGSON = gson.fromJson(gson.toJson(collectionList),itemsListType);
        List<String> collectionListFromMYJSON = gson.fromJson(myjson.toJSON(collectionList),itemsListType);
        assertEquals(collectionListFromGSON, collectionListFromMYJSON);
        collectionList=null;
        collectionListFromGSON=null;
        collectionListFromMYJSON=null;
    }

    @Test
    public void deserializationMap() {
        Type itemsMapType = new TypeToken<Map<Integer,String>>() {}.getType();
        Map<Integer,String> collectionMap = new HashMap<>();
        for (int i=0;i<SIZE;i++){
            collectionMap.put(new Integer(i), new String("value:"+i));
        }
        Map<Integer,String> collectionMapFromGSON = gson.fromJson(gson.toJson(collectionMap),itemsMapType);
        Map<Integer,String> collectionMapFromMYJSON = gson.fromJson(myjson.toJSON(collectionMap),itemsMapType);
        assertEquals(collectionMapFromGSON, collectionMapFromMYJSON);
        collectionMap=null;
        collectionMapFromGSON=null;
        collectionMapFromMYJSON=null;

    }

    @Test
    public void deserializationCourseObject() {
        assertEquals(gson.fromJson(gson.toJson(course),course.getClass()), gson.fromJson(myjson.toJSON(course),course.getClass()));
    }

    @Test
    public void deserializationStudentObject() {
        student.setMark(course,new Integer(5));
        assertEquals(gson.fromJson(gson.toJson(course),course.getClass()), gson.fromJson(myjson.toJSON(course),course.getClass()));
    }
}
