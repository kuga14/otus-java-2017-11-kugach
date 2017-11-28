package ru.kugach.artem.otus;

import java.util.Collection;

public class MemoryRate {

        private static final Runtime rt = Runtime.getRuntime ();

        private static long calcMemUsage(ObjectCreator factory) throws InterruptedException {
            Object handle;
            gc();
            int count = 5_000;
            Object[] objects = new Object[count];
            long mem1 = usedMemory();
            for (int i = 0; i < count; i++) {
                handle = factory.create();
                objects[i] = handle;
            }
            gc();
            long mem2 = usedMemory();
            for (int i = 0; i < count; i++) {
                objects[i] = null;
            }
            objects=null;
            return Math.round((float)(mem2 - mem1)/count);
        }

        private static long collectionCalcMemUsage(ObjectCreator factory,int elements) throws InterruptedException {
            Collection handle=null;
            gc();
            int count = 10;
            Object[] objects = new Object[count];
            long mem1 = usedMemory();
            for (int i = 0; i < count; i++) {
                handle = (Collection) factory.create();
                for(int j=0;j<elements;j++){
                    handle.add(new Integer(j+i));
                }
                objects[i] = handle;
            }
            gc();
            long mem2 = usedMemory();
            handle.clear();
            handle=null;
            return Math.round((float)(mem2 - mem1)/count);
        }

        public static String toPrintble(ObjectCreator factory) throws InterruptedException {
            long mem = calcMemUsage(factory);
            return factory.create().getClass().getCanonicalName() + " full size of instance = " + mem + " bytes\n";
        }

        public static String toPrintble(ObjectCreator factory,int elementsCount) throws InterruptedException {
            long mem = collectionCalcMemUsage(factory,elementsCount);
            return factory.create().getClass().getCanonicalName()+" "+elementsCount+" elements ," + " full size of instance = " + mem + " bytes\n";
        }

        public static void gc() throws InterruptedException {
            int i=0;
            while( i<5){
                rt.gc ();
                Thread.sleep(100);i++;
            }
        }

        private static long usedMemory(){
            return rt.totalMemory () - rt.freeMemory ();
        }
}
