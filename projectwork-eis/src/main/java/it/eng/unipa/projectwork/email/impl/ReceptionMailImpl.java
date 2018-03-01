package it.eng.unipa.projectwork.email.impl;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import it.eng.unipa.projectwork.email.SendMail;
import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.jms.Queue;
import javax.jms.QueueConnectionFactory;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMessage.RecipientType;
import javax.mail.internet.MimeMultipart;

import org.apache.commons.io.IOUtils;

import javax.mail.BodyPart;
import javax.mail.Flags.Flag;
import javax.mail.Folder;
//import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.NoSuchProviderException;
import javax.mail.Part;
import javax.mail.Provider;
import javax.mail.Session;
import javax.mail.Store;

import it.eng.unipa.projectwork.email.Attachment;
import it.eng.unipa.projectwork.email.Message;
import it.eng.unipa.projectwork.email.ReceptionMail;
import it.eng.unipa.projectwork.email.Message.TYPE;
import it.eng.unipa.projectwork.email.exception.MailNotSendException;

@Stateless
public class ReceptionMailImpl implements ReceptionMail {

	@Resource(mappedName="java:jboss/mail/projectworkreceive")
	Session emailSession;

	//public static void check(String host, String storeType, String user,
	//    String password)
	@Override
	public List<Message> getMails() {
		List<Message> ret = new ArrayList<>();

		try {

//				Properties properties = new Properties();
//
//		      properties.put("mail.pop3.host", "pop.gmail.com");
//		      properties.put("mail.pop3.port", "995");
//		      properties.put("mail.debug","true");
//		      properties.put("mail.pop3.starttls.enable", "true");
//		      Session emailSession = Session.getDefaultInstance(properties);
//		      for(Provider p : emailSession.getProviders()) {
//		    	  System.out.println(p);
//		      }
//		      //create the POP3 store object and connect with the pop server
		      Store store = emailSession.getStore("pop3s");
			store.connect("pop.gmail.com","gruppo4eng2018@gmail.com","gruppo412");
			//create the folder object and open it
			Folder emailFolder = store.getFolder("INBOX");
			
			emailFolder.open(Folder.READ_WRITE);

			// retrieve the messages from the folder in an array and print it
			javax.mail.Message[] messages = emailFolder.getMessages();
			System.out.println("messages.length---" + messages.length);

			for (int i = 0, n = messages.length; i < n; i++) {
				javax.mail.Message message = messages[i];
				System.out.println("---------------------------------");
				System.out.println("Email Number " + (i + 1));
				System.out.println("Subject: " + message.getSubject());
				System.out.println("From: " + message.getFrom()[0]);
				System.out.println("Text: " + ((MimeMultipart)message.getContent()).toString());
				// message.get
				try {

					ret.add(elab(message));

				}catch (Exception e) {
					e.printStackTrace();
				}finally {
					message.setFlag(Flag.DELETED, true);
				}

			}

			//close the store and folder objects
			emailFolder.close(true);
			store.close();

		} catch (Exception e) {
			e.printStackTrace();
		} 

		return ret;
	}


	public Message elab(javax.mail.Message message) throws IOException, MessagingException {
		List<Attachment> l = new ArrayList<>();
		String subject = message.getSubject();
		String body = getTextFromMessage(message);
		Multipart multipart = (Multipart) message.getContent();

		for (int i = 0; i < multipart.getCount(); i++) {
			BodyPart bodyPart = multipart.getBodyPart(i);
			if(Part.ATTACHMENT.equalsIgnoreCase(bodyPart.getDisposition())) {
				String fileName = bodyPart.getFileName();
				String mimeType = evalMimeType(fileName);
				byte[] bodyB = IOUtils.toByteArray(bodyPart.getInputStream());
				l.add(new Attachment(bodyB, fileName, mimeType));
			}

		}
		return new Message(subject, body, TYPE.HTML, l);

	}
	
	private String getTextFromMessage(javax.mail.Message message) throws MessagingException, IOException {
	    String result = "";
	    if (message.isMimeType("text/plain")) {
	        result = message.getContent().toString();
	    } else if (message.isMimeType("multipart/*")) {
	        MimeMultipart mimeMultipart = (MimeMultipart) message.getContent();
	        result = getTextFromMimeMultipart(mimeMultipart);
	    }
	    return result;
	}

	private String getTextFromMimeMultipart(
	        MimeMultipart mimeMultipart)  throws MessagingException, IOException{
	    String result = "";
	    int count = mimeMultipart.getCount();
	    for (int i = 0; i < count; i++) {
	        BodyPart bodyPart = mimeMultipart.getBodyPart(i);
	        if (bodyPart.isMimeType("text/plain")) {
	            result = result + "\n" + bodyPart.getContent();
	            break; // without break same text appears twice in my tests
	        } else if (bodyPart.isMimeType("text/html")) {
	            String html = (String) bodyPart.getContent();
	            result = result + "\n" + org.jsoup.Jsoup.parse(html).text();
	        } else if (bodyPart.getContent() instanceof MimeMultipart){
	            result = result + getTextFromMimeMultipart((MimeMultipart)bodyPart.getContent());
	        }
	    }
	    return result;
	}


	private String evalMimeType(String fileName) {
		// TODO Auto-generated method stub
		return null;
	}

}
