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

import org.apache.commons.io.IOUtils;

import javax.mail.BodyPart;
import javax.mail.Flags.Flag;
import javax.mail.Folder;
//import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.NoSuchProviderException;
import javax.mail.Part;
import javax.mail.Session;
import javax.mail.Store;

import it.eng.unipa.projectwork.email.Attachment;
import it.eng.unipa.projectwork.email.Message;
import it.eng.unipa.projectwork.email.ReceptionMail;
import it.eng.unipa.projectwork.email.Message.TYPE;
import it.eng.unipa.projectwork.email.exception.MailNotSendException;

@Stateless
public class ReceptionMailImpl implements ReceptionMail {

//	@Resource(mappedName="java:jboss/mail/projectworkreceive")
//	Session mailSession;

	//public static void check(String host, String storeType, String user,
	//    String password)
	@Override
	public List<Message> getMails() {
		List<Message> ret = new ArrayList<>();

		try {

			//create the POP3 store object and connect with the pop server
			Store store = mailSession.getStore("pop3s");
			store.connect(System.getProperty("pop3.username"),System.getProperty("pop3.password"));
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
				System.out.println("Text: " + message.getContent().toString());
				// message.get
				try {

					ret.add(new Message(message.getFrom()[0].toString(), message.getSubject(), message.getContent().toString(),TYPE.HTML, elab(message)));

				}catch (Exception e) {
					e.printStackTrace();
				}finally {
					message.setFlag(Flag.DELETED, true);
				}

			}

			//close the store and folder objects
			emailFolder.close(false);
			store.close();

		} catch (Exception e) {
			e.printStackTrace();
		} 

		return ret;
	}


	public List<Attachment> elab(javax.mail.Message message) throws IOException, MessagingException {
		List<Attachment> l = new ArrayList<>();
		Multipart multipart = (Multipart) message.getContent();

		for (int i = 0; i < multipart.getCount(); i++) {
			BodyPart bodyPart = multipart.getBodyPart(i);
			if(Part.ATTACHMENT.equalsIgnoreCase(bodyPart.getDisposition())) {
				String fileName = bodyPart.getFileName();
				String mimeType = evalMimeType(fileName);
				byte[] body = IOUtils.toByteArray(bodyPart.getInputStream());
				l.add(new Attachment(body, fileName, mimeType));
			}

		}
		return l;

	}


	private String evalMimeType(String fileName) {
		// TODO Auto-generated method stub
		return null;
	}

}
