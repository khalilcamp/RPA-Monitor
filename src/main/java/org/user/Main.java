package org.user;
//Quartz foi um desafio, sinceramente.
// Uiuiui
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.impl.StdSchedulerFactory;
import static org.quartz.JobBuilder.newJob;
import static org.quartz.TriggerBuilder.newTrigger;
import static org.quartz.SimpleScheduleBuilder.simpleSchedule;

// O job personalizado!
import org.user.scheduler.MonitorJob;
public class Main {
    public static void main(String[] args) {
        try {
            // Gerenciando a job e dando a "agenda"
            //StdScheduler = https://www.quartz-scheduler.org/api/2.3.0/org/quartz/impl/StdSchedulerFactory.html
            Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
            // Qual tarefa vai ser exec.
            JobDetail job = newJob(MonitorJob.class)
                    .withIdentity("monitorJob", "monitorGroup")
                    .build();
            // Frequencia
            Trigger trigger = newTrigger()
                    .withIdentity("monitorTrigger", "monitorGroup")
                    .startNow()
                    .withSchedule(simpleSchedule()
                            .withIntervalInMinutes(5)
                            .repeatForever())
                    .build();
            // Agenda
            scheduler.scheduleJob(job, trigger);
            scheduler.start();
            // Se não quiser desgraçar tudo, seguir parada segura.
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