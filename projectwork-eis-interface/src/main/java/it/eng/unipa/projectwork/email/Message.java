package it.eng.unipa.projectwork.email;

import java.util.ArrayList;
import java.util.List;

public class Message {
	
	public static enum TYPE{TEXT,HTML}
	private String mittente;
	private String subject;
	private String body;
	private TYPE type;
	private List<Attachment> attachments = new ArrayList<>();
	
	
	public Message(String mittente,String subject,String body,TYPE type,List<Attachment> a) {
		this.mittente = mittente;
		this.subject = subject;
		this.body = body;
		this.type = type;
		this.attachments = a;
	}
	
	public Message(String subject,String body,TYPE type,List<Attachment> a) {
		this.subject = subject;
		this.body = body;
		this.type = type;
		this.attachments = a;
	}
	
	
	public Message(String subject,String body,TYPE type) {
		this.subject = subject;
		this.body = body;
		this.type = type;
		
	}
	
	
	public String getMittente() {
		return mittente;
	}
	
	public List<Attachment> getAttachments() {
		return attachments;
	}
	
	public String getSubject() {
		return subject;
	}
	
	public String getBody() {
		return body;
	}
	
	public TYPE getType() {
		return type;
	}

}
