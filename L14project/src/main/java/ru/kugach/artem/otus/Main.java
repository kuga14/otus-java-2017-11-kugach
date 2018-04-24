package ru.kugach.artem.otus;

import java.util.Arrays;
import java.util.Random;
import java.util.stream.Collectors;


public class Main {

    private static final int ARRAY_SIZE = 1_0;

    public static void main(String... args) throws Exception {
        ArraySorter sorter = new ArraySorter();
        int[] arr = new int[ARRAY_SIZE];
        Random random = new Random();
        for (int i = 0; i < ARRAY_SIZE; i++) {
            arr[i] = random.nextInt(ARRAY_SIZE);
        }
        sorter.sort(arr);
        System.out.print("["+
                Arrays.stream(arr)
                .mapToObj(i -> ((Integer) i).toString())
                .collect(Collectors.joining(","))+"]");
    }
}


