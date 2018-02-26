package it.eng.unipa.projectwork.email;

import it.eng.unipa.projectwork.email.exception.MailNotSendException;

public interface RceptionMail {
/// questa Interfaccia astrae l'azione di ricevere una mail
	
	public void rcptMail(Message message) throws MailNotSendException;// eccezione da implementare il caso di errore ricezione
}
