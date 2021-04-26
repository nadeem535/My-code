package com.optum.svc.service;

import java.util.TimeZone;

import org.quartz.CronScheduleBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;

public class JobScheduler {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("*******************************************");
		System.out.println("*      JOB  SCHEDULER    DETAILS          *");
        System.out.println("*           JOB NAME:J5687SVC             *");
        System.out.println("*      RUNS: EVERYDAY AT 5:30 EST         *");
        System.out.println("*        SCHEDULER UP TIMESTAMP           *");
        System.out.println("*      "+Job1.getCurrentTimeStamp()+"         *");
        System.out.println("*******************************************");
		 JobDetail job = JobBuilder.newJob(Job1.class)
                 .withIdentity("job", "group").build();
		 
		 final CronScheduleBuilder cronSchedule = CronScheduleBuilder.dailyAtHourAndMinute(5, 30)
                   .inTimeZone(TimeZone.getTimeZone("EST"));
	
         Trigger trigger = TriggerBuilder.newTrigger()
                 .withIdentity("cronTrigger", "group")
                 .withSchedule(cronSchedule)
                 .build();
		try {
			 Scheduler scheduler = new StdSchedulerFactory().getScheduler();
			 scheduler.start();
	         scheduler.scheduleJob(job, trigger);
		} catch (SchedulerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
	}
}
