package it.eng.unipa.projectwork.email;

import it.eng.unipa.projectwork.email.exception.MailNotSendException;

public interface ReceptionMail {
/// questa Interfaccia astrae l'azione di ricevere una mail
	
	public abstract void receptiontMail(Message message, String mittente) throws MailNotSendException;// eccezione da implementare il caso di errore ricezione
}
