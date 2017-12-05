package ru.kugach.artem.otus;

import java.util.Collection;

public class MemoryRate {

        private static final Runtime rt = Runtime.getRuntime ();
        private static final int count = 1_000_000;
        private static final int countCollection = 20;

        private static long calcMemUsage(ObjectCreator factory) throws InterruptedException {
            Object handle;
            gc();
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
            Object[] objects = new Object[countCollection];
            gc();
            long mem1 = usedMemory();
            for (int i = 0; i < countCollection; i++) {
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
            for (int i = 0; i < countCollection; i++) {
                objects[i]=null;
            }
            objects=null;
            return Math.round((float)(mem2 - mem1)/countCollection);
        }

        public static String toPrintable(ObjectCreator factory) throws InterruptedException {
            long mem = calcMemUsage(factory);
            return factory.create().getClass().getCanonicalName() + " full size of instance = " + mem + " bytes\n";
        }

        public static String toPrintable(ObjectCreator factory,int elementsCount) throws InterruptedException {
            long mem = collectionCalcMemUsage(factory,elementsCount);
            return factory.create().getClass().getCanonicalName()+" of "+elementsCount+" elements ," + " full size of instance = " + mem + " bytes";
        }

        private static void gc() throws InterruptedException {
            rt.gc ();
            Thread.sleep(1000);
        }

        private static long usedMemory(){
            return rt.totalMemory () - rt.freeMemory ();
        }
}
