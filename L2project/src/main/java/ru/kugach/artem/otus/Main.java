package ru.kugach.artem.otus;


import java.util.*;
import org.openjdk.jol.info.ClassLayout;


        /**
         *
         *   JVM options: -XX:-UseTLAB -Xmx512m -Xms512m
         *
         */
public class Main {

    public static void main(String... args) throws InterruptedException {

        //Primitive Wrapper
        System.out.println( MemoryRate.toPrintable( () -> new Integer(0) ));
        System.out.println( ClassLayout.parseInstance(new Integer(0)).toPrintable());
        System.out.println("------------------------------------------------------------------------\n");

        //Empty String
        System.out.println( MemoryRate.toPrintable( () -> new String() ));
        System.out.println( ClassLayout.parseInstance(new String()).toPrintable());
        System.out.println("------------------------------------------------------------------------\n");

        //Empty List
        System.out.println( MemoryRate.toPrintable( () -> new ArrayList<>() ));
        System.out.println( ClassLayout.parseInstance(new ArrayList<>()).toPrintable());
        System.out.println("------------------------------------------------------------------------\n");
        System.out.println( MemoryRate.toPrintable( () -> new LinkedList<>() ));
        System.out.println( ClassLayout.parseInstance(new LinkedList<>()).toPrintable());
        System.out.println("------------------------------------------------------------------------\n");

        //Empty Set
        System.out.println( MemoryRate.toPrintable( () -> new TreeSet<>() ));
        System.out.println( ClassLayout.parseInstance(new TreeSet<>()).toPrintable());
        System.out.println("------------------------------------------------------------------------\n");
        System.out.println( MemoryRate.toPrintable( () -> new LinkedHashSet<>() ));
        System.out.println( ClassLayout.parseInstance(new LinkedHashSet<>()).toPrintable());
        System.out.println("------------------------------------------------------------------------\n");
        System.out.println( MemoryRate.toPrintable( () -> new HashSet<>()));
        System.out.println( ClassLayout.parseInstance(new HashSet<>()).toPrintable());
        System.out.println("------------------------------------------------------------------------\n");

        //List of up to 100_000 elements
        int j=0;
        int i= (int) Math.pow(10,j);
        while(i<100_000){
            j+=1;
            while(i< (int) Math.pow(10,j)) {
                System.out.println(MemoryRate.toPrintable(() -> new ArrayList<>(), i));
                Thread.sleep(100);
                i+=(int) Math.pow(10,j-1);
            }
        }
        System.out.println(MemoryRate.toPrintable(() -> new ArrayList<>(), 100_000));
    }

}
