package com.optum.svc.dbutil;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import com.optum.svc.service.EncryptionDecryption;

public class DBConnectivity {
	
	private static Connection connection=null;
	//private static final String PATH= "MyFiles/config.ini";
	private static final String PATH= "/tmp/src/MyFiles/config.ini";
	
	private static Connection createConnection()
	{
		try
		{
		   Class.forName("com.ibm.db2.jcc.DB2Driver");
		   connection = DriverManager.getConnection("jdbc:db2://GWYA0002:50000/DB0P","ML5687HB",getPassword());
		   System.out.println("*   DB2 CONNECTION OPENED SUCCESSFULLY    *");
		   return connection;
		}
		catch(Exception e)
		{
			System.err.println("FAILED TO ESTABLISH DB2 CONNECTION");
		}
		return connection;
	}
	private static String getPassword()
	{
		Properties properties = new Properties();
		FileInputStream fis=null;
		try
		{
			fis = new FileInputStream(PATH);
			properties.load(fis);
			String stringToDecrypt= properties.getProperty("TsoPwd");
			String password= EncryptionDecryption.decryptString(stringToDecrypt);
			return password;
		}
		catch(IOException e)
		{
			System.err.println("FAILED TO OPEN CONFIG FILE");
		} 
		catch (Exception e) 
		{
			System.err.println("FAILED TO DECRYPT STRING");
		}
		finally
		{
			if (fis != null) 
			    safeClose(fis);
		}
		return null;
	}
	
	public static Connection getConnection() 
	{
		if(connection==null)
		{
			createConnection();
			return connection;
		}
		else if (connection!=null)
		{
			return connection;
		}
		else 
		{
			System.err.println("FAILED TO GET THE DB2 CONNECTION");
			return null;
		}
		
	}
	public static void setConnection(Connection connection) 
	{
		DBConnectivity.connection = connection;
	}
	public static void closeConnection() 
	{
		try 
		{
			connection.close();
			setConnection(null);
			System.out.println("*   DB2 CONNECTION CLOSED SUCCESSFULLY    *");
		} 
		catch (SQLException e) 
		{
			System.out.println("FAILED TO CLOSE DB2 CONNECTION");
		}
		
	}
	public static void safeClose(FileInputStream fis) {
		  if (fis != null) 
		  {
		    try 
		    {
		      fis.close();
		    } 
		    catch (IOException e) 
		    {
		      System.out.println("FAILED TO CLOSE FILE INPUT STREAM");
		    }
		  }
	}	  
	public static void main(String args[])
	{
		getConnection();
		//getPassword();
	}
}
