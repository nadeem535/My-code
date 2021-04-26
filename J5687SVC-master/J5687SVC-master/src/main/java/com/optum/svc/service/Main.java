package com.optum.svc.service;


import java.time.LocalDate;
import com.optum.svc.bean.SvcBean;
import com.optum.svc.dao.DAO;
import com.optum.svc.dbutil.DBConnectivity;
import com.optum.svc.excel.Excel;

public class Main {
	public static void run()
	{
		try
		{
			SvcBean svcBean[] =new SvcBean[3];
			String PROC_DT[]= getDate(); 
			svcBean[0]=execute(PROC_DT[0]);
			svcBean[1]=execute(PROC_DT[1]);
			svcBean[2]=execute(PROC_DT[2]);		
			Excel.write(svcBean);
		}
		catch(Exception e)
		{
			System.err.println("J5687SVC FAILED IN PROCESSING");
		}
		finally
		{
			DBConnectivity.closeConnection();
		}
	}
	public static String[] getDate()
	{
		String proc_dt[]=new String[3];
		LocalDate date= LocalDate.now();
		for(int i=0;i<3;i++)
		{
			date=date.minusDays(1);
			proc_dt[i]=date.toString();
		}
		return proc_dt;	
	}
	public static SvcBean execute(String PROC_DT)
	{	
		SvcBean bean =new SvcBean();
		DAO dao=new DAO();
		//PROCESS DATE 
		bean.setPROCESS_DATE1(PROC_DT);  
		//LOADED TO PRADJD CLAIM REPOSITORY TABLES     
		bean.setLOADED_TO_PRADJD_CLAIM_REPOSITORY_TABLES(dao.getCount(PROC_DT, "12006"));
		//LOADED TO PRADJD SERVICE CHANNEL TABLES      
		bean.setLOADED_TO_PRADJD_SERVICE_CHANNEL_TABLES(dao.getCount(PROC_DT, "12007"));
		//PENDING PRADJD CLAIMS
		bean.setPENDING_PRADJD_CLAIMS(bean.getLOADED_TO_PRADJD_CLAIM_REPOSITORY_TABLES()-bean.getLOADED_TO_PRADJD_SERVICE_CHANNEL_TABLES());                        
		//PROCESS DATE
		bean.setPROCESS_DATE2(PROC_DT); 
		//LOADED TO ADJD CLAIM REPOSITORY TABLES 
		bean.setLOADED_TO_ADJD_CLAIM_REPOSITORY_TABLES(dao.getCount(PROC_DT, "12008")); 
		//LOADED TO ADJD SERVICE CHANNEL TABLES 
		bean.setLOADED_TO_ADJD_SERVICE_CHANNEL_TABLES(dao.getCount(PROC_DT, "12009"));
		//PENDING ADJD CLAIMS       
		bean.setPENDING_ADJD_CLAIMS(bean.getLOADED_TO_ADJD_CLAIM_REPOSITORY_TABLES()-bean.getLOADED_TO_ADJD_SERVICE_CHANNEL_TABLES());
		return bean;
	}
	
	public static void main(String args[])
	{
		run();
	}
	

}
