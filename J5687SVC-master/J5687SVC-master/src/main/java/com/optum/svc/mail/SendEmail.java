/*package com.optum.svc.mail;

import java.util.*; 
import javax.mail.*; 
import javax.mail.internet.*; 

//Send Mail in Java using SMTP with TLS authentication
public class SendEmail {
	private static final String HOST="localhost";
	private static final String USER_NAME = "username@gmail.com"; 
	private static final String PASSWORD = "password";
	private static final String SEND_TO = "CHSSS_DL@ds.uhc.com";
	private static final String SEND_FROM = "CHWY_SSS_REPORTING@UHC.COM";
	public static void mail(String[] args) { 
		Properties props = new Properties();			 
		
		// enable authentication 
		props.put("mail.smtp.auth", HOST);			 
		
		// enable STARTTLS 
		props.put("mail.smtp.starttls.enable", "true");	 
		
		// Setup mail server 
		props.put("mail.smtp.host", "smtp.gmail.com");	 
		
		// TLS Port 
		props.put("mail.smtp.port", "587");				 

		// creating Session instance referenced to 
		// Authenticator object to pass in 
		// Session.getInstance argument 
		Session session = Session.getInstance(props, 
		new javax.mail.Authenticator() { 
			
			//override the getPasswordAuthentication method 
			protected PasswordAuthentication 
						getPasswordAuthentication() { 
										
				return new PasswordAuthentication(USER_NAME, PASSWORD); 
			} 
		}); 

		try { 
			
			// compose the message 
			// javax.mail.internet.MimeMessage class is 
			// mostly used for abstraction. 
			Message message = new MimeMessage(session);	 
			
			// header field of the header. 
			message.setFrom(new InternetAddress(SEND_FROM)); 
			
			message.setRecipients(Message.RecipientType.TO, 
				InternetAddress.parse(SEND_TO)); 
			message.setSubject("hello"); 
			message.setText("Yo it has been sent"); 

			Transport.send(message);		 //send Message 

			System.out.println("Done"); 

		} catch (MessagingException e) { 
			throw new RuntimeException(e); 
		} 
	} 

}
*/