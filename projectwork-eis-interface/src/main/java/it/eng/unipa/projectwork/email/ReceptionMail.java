package it.eng.unipa.projectwork.email;

import java.util.List;

import javax.ejb.Local;

import it.eng.unipa.projectwork.email.exception.MailNotSendException;

@Local
public interface ReceptionMail {

   public List<Message> getMails();
	
}
