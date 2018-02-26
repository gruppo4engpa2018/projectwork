package it.eng.unipa.projectwork.email.impl;

import javax.annotation.Resource;
import javax.jms.Queue;
import javax.jms.QueueConnectionFactory;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMessage.RecipientType;

import it.eng.unipa.projectwork.email.Message;
import it.eng.unipa.projectwork.email.RceptionMail;
import it.eng.unipa.projectwork.email.Message.TYPE;
import it.eng.unipa.projectwork.email.exception.MailNotSendException;

public class ReceptionMailImpl implements RceptionMail {
	
	@Resource(mappedName="java:jboss/mail/projectwork")
	Session mailSession;
	
	@Resource(mappedName="java:/ConnectionFactory")
	QueueConnectionFactory queueConnectionFactory;
	
	@Resource(mappedName="java:/jms/queue/EmailQueue")
	Queue queue;


	@Override
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


}
