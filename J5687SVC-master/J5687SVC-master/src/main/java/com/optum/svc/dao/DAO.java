package com.optum.svc.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import com.optum.svc.dbutil.DBConnectivity;

public class DAO {
	private static PreparedStatement pstmt;
	private static ResultSet rset;
	
	public DAO()
	{
		if(DBConnectivity.getConnection()==null)
			DBConnectivity.getConnection();
	}
	public int getCount(String PROC_DT, String EVNT_TYP_ID)
	{
		
		String sql1="SELECT COUNT(DISTINCT INVN_CTL_NBR) " +  
					"FROM D5406TOP.FICS_CLM_ACTV " +                                        
					"WHERE EVNT_TYP_ID IN ( ? ) "+                           
					"AND PROC_DT = ? "+                                        
					"WITH UR"; 	 
		try
		{
			if(DBConnectivity.getConnection()!=null)
			{
			pstmt= DBConnectivity.getConnection().prepareStatement(sql1);
			pstmt.setString(1, EVNT_TYP_ID);
			pstmt.setString(2, PROC_DT);
			rset=pstmt.executeQuery();
			}
			while(rset.next())
			{
			   int count=rset.getInt(1);
			   return count;
			}
			
		}
		catch (Exception e) 
		{
			System.out.println("FAILED IS EXECUTING THE DB2 QUERY");
			e.printStackTrace();
		}
		return 0;
	}
	public static void main(String args[])
	{
		System.out.println(new DAO().getCount("2019-09-12", "12006"));
	}
}
