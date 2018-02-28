package it.eng.unipa.projectwork.email;

public class Attachment {
	private byte[] body;
	private String name;
	private String mimeType;
	public Attachment(byte[] body, String name, String mimeType) {
		super();
		this.body = body;
		this.name = name;
		this.mimeType = mimeType;
	}
	
	public byte[] getBody() {
		return body;
	}
	
	public String getName() {
		return name;
	}
	
	public String getMimeType() {
		return mimeType;
	}
}
