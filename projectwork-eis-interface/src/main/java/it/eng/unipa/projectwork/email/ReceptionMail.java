package it.eng.unipa.projectwork.email;

import it.eng.unipa.projectwork.email.exception.MailNotSendException;

public interface ReceptionMail {

   
	public abstract void receptiontMail(Message message, String mittente) throws MailNotSendException;// eccezione da implementare il caso di errore ricezione

	public abstract void receptionMail(javax.jms.Message message);

	public abstract void receptionMail(Message message, String email);
}
