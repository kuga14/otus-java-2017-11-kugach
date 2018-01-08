package ru.kugach.artem.otus;

import com.sun.management.GarbageCollectionNotificationInfo;
import javax.management.NotificationEmitter;
import javax.management.NotificationListener;
import javax.management.openmbean.CompositeData;
import java.lang.management.GarbageCollectorMXBean;
import java.util.*;

public class GCMonitor {
    private int counterMinorGC;
    private int counterFullGC;
    private long minorGcExecutionTime;
    private long fullGcExecutionTime;

    private double getAverageMinorGcExecutionTime(){
        return (minorGcExecutionTime==0)? 0 : minorGcExecutionTime/counterMinorGC;
    }

    private int getCounterMinorGC(){
        return counterMinorGC;
    }

    private int getCounterFullGC(){
        return counterFullGC;
    }

    private double getAverageFullGcExecutionTime(){
        return (counterFullGC==0)? 0 : fullGcExecutionTime/counterFullGC;
    }

    protected void installGCMonitoring() {
        List<GarbageCollectorMXBean> gcbeans = java.lang.management.ManagementFactory.getGarbageCollectorMXBeans();
        for (GarbageCollectorMXBean gcbean : gcbeans) {
            NotificationEmitter emitter = (NotificationEmitter) gcbean;
            NotificationListener listener = (notification, handback) -> {
                if (notification.getType().equals(GarbageCollectionNotificationInfo.GARBAGE_COLLECTION_NOTIFICATION)) {
                    GarbageCollectionNotificationInfo info = GarbageCollectionNotificationInfo.from((CompositeData) notification.getUserData());
                    long duration = (info.getGcInfo().getEndTime() - info.getGcInfo().getStartTime());
                    String gcAction = info.getGcAction();
                    switch (gcAction) {
                        case "end of minor GC":
                            gcAction = "Minor GC";
                            counterMinorGC++;
                            minorGcExecutionTime+= duration;
                            System.out.println(info.getGcInfo().getStartTime()+" "+gcAction+" "+duration+"ms;"
                                    +" Minor GC Average Execution Time: "+getAverageMinorGcExecutionTime()+"ms("+getCounterMinorGC()+" executions)");
                            break;
                        case "end of major GC":
                            gcAction = "Full GC";
                            counterFullGC++;
                            fullGcExecutionTime+=duration;
                            System.out.println(info.getGcInfo().getStartTime()+" "+gcAction+" "+duration+"ms;"
                                    +" Full GC Average Execution Time: "+getAverageFullGcExecutionTime()+"ms("+getCounterFullGC()+" executions)");
                            break;
                    }
                }
            };

            emitter.addNotificationListener(listener, null, null);
        }
    }

}
