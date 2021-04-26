package com.optum.svc.mail;

import java.io.File;
import java.util.*;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.internet.*;
import javax.mail.*;




public class SendMail {
	
   private static final String MAIL_MESSAGE = "<p style='font-family: initial;color: black;'> Hi Team,<br><br>This is an auto-generated mail. Please find the attached excel sheet.<br><br>Thanks & Regards<br>CHWY-SSMO Team</p>";
   private static final String MAIL_SUBJECT = "J5687SVC: SVC_REPORT";
   private static final String MAIL_TO = "CHSSS_DL@ds.uhc.com";
   private static final String MAIL_FROM = "CHWY_SSS_REPORTING@UHC.COM";
   
   public void htmlMail(String filename) throws Exception
   {
     Properties properties = System.getProperties();
     properties.setProperty("mail.smtp.host", "mailo2.uhc.com");
     Session session = Session.getDefaultInstance(properties);
     MimeMessage message = new MimeMessage(session);
     message.setFrom(new InternetAddress(MAIL_FROM));
     message.addRecipient(Message.RecipientType.TO, new InternetAddress(MAIL_TO));
     //message.addRecipient(Message.RecipientType.TO, new InternetAddress("deepak_kumar24@optum.com"));
     message.setSubject(MAIL_SUBJECT); 
     BodyPart messageBodyPart = new MimeBodyPart();
     messageBodyPart.setContent(MAIL_MESSAGE, "text/html");
     Multipart multipart = new MimeMultipart();
     multipart.addBodyPart(messageBodyPart);
     messageBodyPart = new MimeBodyPart();
     File file = new File(filename);
     DataSource source = new FileDataSource(file.getAbsolutePath());
     messageBodyPart.setDataHandler(new DataHandler(source));
     messageBodyPart.setFileName(filename);
     multipart.addBodyPart(messageBodyPart);
     message.setContent(multipart );
     Transport.send(message);   
   }
}