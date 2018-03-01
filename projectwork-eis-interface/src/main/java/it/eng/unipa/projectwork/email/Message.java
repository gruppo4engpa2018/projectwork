package it.eng.unipa.projectwork.email;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.io.*;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
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
	
//	private static Pattern twopart = Pattern.compile("(\\d+):(\\d+)");
//	
//	
//	public String[] SplitBodyLine(String body) {
//	    String split[];
//	    split = body.split("\\n");
//	    return split;
//	}
//	    
//	public Map<String,String> SplitBodyLine2(String s) {
//		//Map<String,String> map=new HashMap<>();
//		 Matcher m = twopart.matcher(s);
//	        if (m.matches()) {
//	            return map (""+m.group(1), ""+m.group(2));
//	        } else {
//	            return map ("","");
//	        }
//	    }
//		 		 
//		 private Map<String, String> map(String string, String string2) {
//		// TODO Auto-generated method stub
//		return null;
//	}
		
}
