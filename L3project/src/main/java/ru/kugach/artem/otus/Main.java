package ru.kugach.artem.otus;

import java.util.Collections;
import java.util.Comparator;
import java.util.ArrayList;
import java.util.Random;

public class Main {
    private static int BOUND= 10_000;
    private static int START_COUNT= 10;

    public static void main(String[] args) {
        MyArrayList<Integer> srcList = new MyArrayList<Integer>();

        //add elements to List
        System.out.println("add "+START_COUNT+" elements to MyList");
        System.out.println("Before add\n"+srcList.toString());
        Random random = new Random();
        random.ints(0, BOUND).limit(START_COUNT).forEach(i -> srcList.add(i));
        System.out.println("After add\n"+srcList.toString());
        System.out.println("Size: "+srcList.size()+"\n");

        //Collections addAll
        System.out.println("Collections.addAll element \"114\" to end of MyList");
        System.out.println("Before addAll\n"+srcList.toString());
        Collections.addAll(srcList, new Integer(114));
        System.out.println("After addAll\n"+srcList.toString());
        System.out.println("Size: "+srcList.size()+"\n");

        System.out.println("Collections.addAll array of 10 elements to end of List");
        Integer[] arr = {0,1,2,3,4,5,6,7,8,9,10};
        System.out.println("Before addAll\n"+srcList.toString());
        Collections.addAll(srcList, arr);
        System.out.println("After addAll\n"+srcList.toString());
        System.out.println("Size: "+srcList.size()+"\n");

        //Collections sort
        System.out.println("Collections.sort");
        System.out.println("Before addAll\n"+srcList.toString());
        Collections.sort(srcList, Comparator.naturalOrder());
        System.out.println("After addAll\n"+srcList.toString()+"\n");

        //Collections copy
        ArrayList<Number> destList = new ArrayList<Number>(srcList.size()*3/2);
        System.out.println("Created new ArrayList<Number>");
        random.ints(0, BOUND).limit(srcList.size()*3/2).forEach(i -> destList.add(i));
        System.out.println("Size: "+destList.size());
        System.out.println("Before copy\n"+destList.toString());
        Collections.copy(destList, srcList);
        System.out.println("After copy\n"+destList.toString());
    }
}
