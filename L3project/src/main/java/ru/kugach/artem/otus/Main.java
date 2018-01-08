package ru.kugach.artem.otus;

import java.lang.management.ManagementFactory;
import java.util.Collections;
import java.util.Comparator;
import java.util.ArrayList;
import java.util.Random;

public class Main {
    private static final int BOUND = 10_000;
    private static final int START_COUNT = 10;
    private static String logLevel = "[EXEC] ";

    public static void main(String[] args) throws InterruptedException {
        System.out.println(logLevel+"pid "+ ManagementFactory.getRuntimeMXBean().getName());

        MyArrayList<Integer> srcList = new MyArrayList<Integer>();

        //add elements to List
        System.out.println(logLevel+"add "+START_COUNT+" elements to MyList");
        System.out.println(logLevel+"Before add");
        System.out.println(logLevel+srcList.toString());
        Random random = new Random();
        random.ints(0, BOUND).limit(START_COUNT).forEach(i -> srcList.add(i));
        System.out.println(logLevel+"After add");
        System.out.println(logLevel+srcList.toString());
        System.out.println(logLevel+"Size: "+srcList.size());

        //Collections addAll
        System.out.println(logLevel+"Collections.addAll element \"114\" to end of MyList");
        System.out.println(logLevel+"Before addAll");
        System.out.println(logLevel+srcList.toString());
        Collections.addAll(srcList, new Integer(114));
        System.out.println(logLevel+"After addAll");
        System.out.println(logLevel+srcList.toString());
        System.out.println(logLevel+"Size: "+srcList.size());

        System.out.println(logLevel+"Collections.addAll array of 10 elements to end of List");
        Integer[] arr = {0,1,2,3,4,5,6,7,8,9,10};
        System.out.println(logLevel+"Before addAll");
        System.out.println(logLevel+srcList.toString());
        Collections.addAll(srcList, arr);
        System.out.println(logLevel+"After addAll");
        System.out.println(logLevel+srcList.toString());
        System.out.println(logLevel+"Size: "+srcList.size());

        //Collections sort
        System.out.println(logLevel+"Collections.sort");
        System.out.println(logLevel+"Before sort");
        System.out.println(logLevel+srcList.toString());
        Collections.sort(srcList, Comparator.naturalOrder());
        System.out.println(logLevel+"After sort");
        System.out.println(logLevel+srcList.toString());

        //Collections copy
        ArrayList<Number> destList = new ArrayList<Number>(srcList.size()*3/2);
        System.out.println(logLevel+"Created new ArrayList<Number>");
        random.ints(0, BOUND).limit(srcList.size()*3/2).forEach(i -> destList.add(i));
        System.out.println(logLevel+"Size: "+destList.size());
        System.out.println(logLevel+"Before copy");
        System.out.println(logLevel+destList.toString());
        Collections.copy(destList, srcList);
        System.out.println(logLevel+"After copy");
        System.out.println(logLevel+destList.toString());
        while(true){
            System.out.println("...");
            Thread.sleep(1000);
        }
    }
}
