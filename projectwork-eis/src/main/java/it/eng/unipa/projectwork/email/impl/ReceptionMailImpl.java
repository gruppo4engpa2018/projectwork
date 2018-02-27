package it.eng.unipa.projectwork.email.impl;

import java.util.Properties;

import javax.annotation.Resource;
import javax.jms.Queue;
import javax.jms.QueueConnectionFactory;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMessage.RecipientType;
import javax.mail.Folder;
//import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.Store;
import it.eng.unipa.projectwork.email.Message;
import it.eng.unipa.projectwork.email.ReceptionMail;
import it.eng.unipa.projectwork.email.Message.TYPE;
import it.eng.unipa.projectwork.email.exception.MailNotSendException;

public class ReceptionMailImpl implements ReceptionMail {
/*	
	@Resource(mappedName="java:jboss/mail/projectwork")
	Session mailSession;
	
	@Resource(mappedName="java:/ConnectionFactory")
	QueueConnectionFactory queueConnectionFactory;
	
	@Resource(mappedName="java:/jms/queue/EmailQueue")
	Queue queue;
*/
	// da implementare
/*	
	import java.util.Properties;


*/

     

	

	//public static void check(String host, String storeType, String user,
	  //    String password) 
	public static void checkMail()
	
	   {
	      try {
	    	  String host = "pop.gmail.com";// change accordingly
	          String mailStoreType = "pop3";
	          String user = "gruppo4eng2018@gmail.com";// change accordingly
	          String password = "gruppo412";// change accordingly

	      //create properties field
	      Properties properties = new Properties();

	      properties.put("mail.pop3.host", host);
	      properties.put("mail.pop3.port", "995");
	      properties.put("mail.pop3.starttls.enable", "true");
	      Session emailSession = Session.getDefaultInstance(properties);
	  
	      //create the POP3 store object and connect with the pop server
	      Store store = emailSession.getStore("pop3s");

	      store.connect(host, user, password);

	      //create the folder object and open it
	      Folder emailFolder = store.getFolder("INBOX");
	      emailFolder.open(Folder.READ_ONLY);

	      // retrieve the messages from the folder in an array and print it
	      javax.mail.Message[] messages = emailFolder.getMessages();
	      System.out.println("messages.length---" + messages.length);

	      for (int i = 0, n = messages.length; i < n; i++) {
	         javax.mail.Message message = messages[i];
	         System.out.println("---------------------------------");
	         System.out.println("Email Number " + (i + 1));
	         System.out.println("Subject: " + message.getSubject());
	         System.out.println("From: " + message.getFrom()[0]);
	         System.out.println("Text: " + message.getContent().toString());

	      }

	      //close the store and folder objects
	      emailFolder.close(false);
	      store.close();

	      } catch (NoSuchProviderException e) {
	         e.printStackTrace();
	      } catch (MessagingException e) {
	         e.printStackTrace();
	      } catch (Exception e) {
	         e.printStackTrace();
	      }
	   }
/*
	   public static void main(String[] args) {

	      String host = "pop.gmail.com";// change accordingly
	      String mailStoreType = "pop3";
	      String username = "gruppo4eng2018@gmail.com";// change accordingly
	      String password = "gruppo412";// change accordingly

	      check(host, mailStoreType, username, password);

	   }

	}
*/
/*	@Override
	public void receptiontMail(Message message, String mittente) throws MailNotSendException { // da implementare in versione ricevente
		try{
			MimeMessage mimeMessage = new MimeMessage(mailSession);
			mimeMessage.addRecipient(RecipientType.TO, new InternetAddress(mittente));
			mimeMessage.setSubject(message.getSubject());
			if(TYPE.HTML.equals(message.getType())){
				mimeMessage.setContent(message.getBody(), "text/html; charset=utf-8");
			}else{
				mimeMessage.setText(message.getBody());
			}
			Transport.send(mimeMessage);
		}catch (Exception e) {
			throw new MailNotSendException(e);
		}
	}

*/

@Override
public void receptiontMail(Message message, String mittente) throws MailNotSendException {
	// TODO Auto-generated method stub
	
}

@Override
public void receptionMail(javax.jms.Message message) {
	// TODO Auto-generated method stub
	
}

@Override
public void receptionMail(Message message, String email) {
	// TODO Auto-generated method stub
	
}
}
