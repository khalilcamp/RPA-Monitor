package org.user;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.impl.StdSchedulerFactory;
import static org.quartz.JobBuilder.newJob;
import static org.quartz.TriggerBuilder.newTrigger;
import static org.quartz.SimpleScheduleBuilder.simpleSchedule;

import org.user.scheduler.MonitorJob;
public class Main {
    public static void main(String[] args) {
        try {

            Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
            JobDetail job = newJob(MonitorJob.class)
                    .withIdentity("monitorJob", "monitorGroup")
                    .build();
            Trigger trigger = newTrigger()
                    .withIdentity("monitorTrigger", "monitorGroup")
                    .startNow()
                    .withSchedule(simpleSchedule()
                            .withIntervalInMinutes(5)
                            .repeatForever())
                    .build();
            scheduler.scheduleJob(job, trigger);
            scheduler.start();
            Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                try {
                    scheduler.shutdown(true);
                } catch (SchedulerException e) {
                    e.printStackTrace();
                }
            }));

        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }
}