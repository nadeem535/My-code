package com.optum.svc.service;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class Job1 implements Job{
	
	 public void execute(JobExecutionContext context) throws JobExecutionException 
	 {
	    System.out.println("*******************************************");
		System.out.println("*                                         *");
        System.out.println("*          J5687SVC JOB BEGINS            *");
        System.out.println("*       "+getCurrentTimeStamp()+"        *");
        System.out.println("*                                         *");
        System.out.println("*******************************************");
        Main.run();
	 	System.out.println("*******************************************");
	 	System.out.println("*                                         *");
        System.out.println("*          J5687SVC JOB ENDED             *");
        System.out.println("*       "+getCurrentTimeStamp()+"        *");
        System.out.println("*                                         *");
        System.out.println("*******************************************");
	 }

	 public static String getCurrentTimeStamp()
	 {
    	final ZoneId zoneId2 = ZoneId.of("US/Eastern");
        final ZonedDateTime zonedDateTime2 = ZonedDateTime.ofInstant(Instant.now(), zoneId2);
        String IST=zonedDateTime2.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        IST=IST.replaceAll(":", ".");
        IST=IST.replaceAll("T", "-")+"000000000";
        return IST.substring(0,26);
	}
}
