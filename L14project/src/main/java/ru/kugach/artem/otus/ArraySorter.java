package ru.kugach.artem.otus;

import java.util.Arrays;
import java.util.concurrent.CountDownLatch;

public class ArraySorter {

    private static final int THREAD_COUNT = 4;
    private final CountDownLatch countDownLatch = new CountDownLatch(THREAD_COUNT);

    class Worker extends Thread {

        private int[] array;

        public Worker(int[] array) {
            this.array = array;
        }

        @Override
        public void run() {
            Arrays.sort(array);
            countDownLatch.countDown();
        }
        public int[] getArray() {
            return array;
        }
    }

    private static int[][] split(int[] arr){
        int size = arr.length / THREAD_COUNT;
        int[][] result=new int[THREAD_COUNT][];
        for (int i = 0; i < THREAD_COUNT; i++) {
            int start = i * size;
            int end = (start+size <= arr.length) ? start+size : arr.length;
            if (i == THREAD_COUNT-1) {
                end = arr.length;
            }
            int length = end - start;
            result[i] = new int[length];
            System.arraycopy(arr, start, result[i], 0, length);
        }
        return result;
    }

    public void sort(int[] array){
        Worker[] workers = new Worker[THREAD_COUNT];
        int[][] arrs = split(array);
        for (int i = 0; i < THREAD_COUNT; i++) {
            workers[i] = new Worker(arrs[i]);
            workers[i].start();
        }
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        int[] result = workers[0].getArray();
        for (int i = 1; i < THREAD_COUNT; i++) {
            result = merge(result, workers[i].getArray());
        }
        System.arraycopy(result, 0, array, 0, result.length);
    }



    private static int[] merge(int[] firstArr, int[] secondArr) {
        int[] result = new int[firstArr.length+secondArr.length];
        int firstArrIndex = 0;
        int secondArrIndex = 0;
        int resultArrIndex = 0;
        while (firstArrIndex < firstArr.length && secondArrIndex < secondArr.length) {
            if (firstArr[firstArrIndex] <= (secondArr[secondArrIndex])) {
                result[resultArrIndex] = firstArr[firstArrIndex];
                firstArrIndex++;
            }
            else {
                result[resultArrIndex] = secondArr[secondArrIndex];
                secondArrIndex++;
            }
            resultArrIndex++;
        }
        System.arraycopy(firstArr, firstArrIndex, result, resultArrIndex, firstArr.length - firstArrIndex);
        System.arraycopy(secondArr, secondArrIndex, result, resultArrIndex, secondArr.length - secondArrIndex);
        return result;
    }
}
