package com.freemoz.app.service;


import com.freemoz.app.service.jobs.IndexJob;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleTrigger;

import static org.quartz.JobBuilder.newJob;
import static org.quartz.SimpleScheduleBuilder.simpleSchedule;
import static org.quartz.TriggerBuilder.newTrigger;

public class JobService {

    public JobService() {}

    public void startJobs() {
        try {
            this.startIndexerJob();
        } catch (SchedulerException e) {}
    }

    private void startIndexerJob() throws SchedulerException {
        Scheduler scheduler = Singleton.getScheduler();
        // Setup the indexer which runs forever indexing
        JobDetail job = newJob(IndexJob.class)
                .withIdentity("indexjob")
                .build();

        SimpleTrigger trigger = newTrigger()
                .withIdentity("indexerjob")
                .withSchedule(simpleSchedule()
                        .withIntervalInHours(24)
                        .repeatForever()
                )
                .build();

        scheduler.scheduleJob(job, trigger);
        scheduler.start();
    }
}
