package ru.kugach.artem.otus;


import java.util.*;
import org.openjdk.jol.info.ClassLayout;

        /**
         *
         *   -XX:-UseTLAB -Xmx512m -Xms512m
         *
         */
public class Main {

    public static void main(String... args) throws InterruptedException {

        //Calc Primitive Wrapper
        System.out.println( MemoryRate.toPrintble( () -> new Integer(0) ));
        System.out.println( ClassLayout.parseInstance(new Integer(0)).toPrintable());
        System.out.println("------------------------------------------------------------------------\n");

        //Calc Empty String
        System.out.println( MemoryRate.toPrintble( () -> new String() ));
        System.out.println( ClassLayout.parseInstance(new String()).toPrintable());
        System.out.println("------------------------------------------------------------------------\n");

        //Calc Empty List
        System.out.println( MemoryRate.toPrintble( () -> new ArrayList<>() ));
        System.out.println( ClassLayout.parseInstance(new ArrayList<>()).toPrintable());
        System.out.println("------------------------------------------------------------------------\n");
        System.out.println( MemoryRate.toPrintble( () -> new LinkedList<>() ));
        System.out.println( ClassLayout.parseInstance(new LinkedList<>()).toPrintable());
        System.out.println("------------------------------------------------------------------------\n");

        //Calc Empty Set
        System.out.println( MemoryRate.toPrintble( () -> new TreeSet<>() ));
        System.out.println( ClassLayout.parseInstance(new TreeSet<>()).toPrintable());
        System.out.println("------------------------------------------------------------------------\n");
        System.out.println( MemoryRate.toPrintble( () -> new LinkedHashSet<>() ));
        System.out.println( ClassLayout.parseInstance(new LinkedHashSet<>()).toPrintable());
        System.out.println("------------------------------------------------------------------------\n");
        System.out.println( MemoryRate.toPrintble( () -> new HashSet<>()));
        System.out.println( ClassLayout.parseInstance(new HashSet<>()).toPrintable());
        System.out.println("------------------------------------------------------------------------\n");

        //Calc List
        int i=1;
        while(i<10_000_000){
            System.out.println( MemoryRate.toPrintble( () -> new ArrayList<>() , i));
            Thread.sleep(100);
            i+=10*i;
        }
    }

}
