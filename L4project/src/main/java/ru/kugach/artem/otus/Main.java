package ru.kugach.artem.otus;

import java.lang.management.ManagementFactory;
import java.util.ArrayList;
import java.util.Random;

public class Main {

    /* VM options
    -Xms512m -Xmx512m -Xloggc:gc.log -verbose:gc -XX:+PrintGCDetails -XX:+PrintGCDateStamps -XX:+UseCompressedClassPointers -XX:+UseCompressedOops -XX:-UseLargePagesIndividualAllocation
    1)-XX:+UseSerialGC
    2)-XX:+UseParallelGC -XX:+UseParallelOldGC
    3)-XX:+UseParNewGC -XX:+UseConcMarkSweepGC
    4)-XX:+UseG1GC
     */


    private static final int INIT_COUNT = 10_000;
    private static final int MAX_COUNT = 14_700;
    private static final int MAX_BOUND = 1_000_000;

    public static void main(String... args) throws InterruptedException {
        System.out.println(ManagementFactory.getRuntimeMXBean().getName());
        GCMonitor gcMonitor = new GCMonitor();
        gcMonitor.installGCMonitoring();
        ArrayList<Integer> integers = new ArrayList<>();
        Random random = new Random();
        for(int i=0;i<INIT_COUNT;i++){
            integers.add(random.nextInt(MAX_BOUND));
        }
        while (true) {
            for(int i=0;i<MAX_COUNT;i++){
                integers.add(random.nextInt(MAX_BOUND));
            }
            Thread.sleep(100);
            int size=integers.size();
            for(int i=size-1;i>size-1-MAX_COUNT/2;i--){
                integers.remove(i);
            }

        }
    }
}
