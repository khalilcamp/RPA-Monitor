//package org.user.scheduler;
//
//import org.quartz.*;
//import org.quartz.impl.StdSchedulerFactory;
//
//public class MonitorScheduler {
//    public static void main(String[] args) {
//        try {
//            JobDetail job = JobBuilder.newJob(MonitorJob.class)
//                    .withIdentity("monitorJob")
//                    .build();
//
//            Trigger trigger = TriggerBuilder.newTrigger()
//                    .withIdentity("monitorTrigger")
//                    .startNow()
//                    .withSchedule(SimpleScheduleBuilder.simpleSchedule()
//                            .withIntervalInMinutes(5)
//                            .repeatForever())
//                    .build();
//
//            Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
//            scheduler.start();
//            scheduler.scheduleJob(job, trigger);
//
//        } catch (SchedulerException e) {
//            e.printStackTrace();
//        }
//    }
//}
