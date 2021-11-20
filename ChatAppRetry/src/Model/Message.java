package Model;

import java.util.Date;

import stream.Client;

public class Message {
	
	private String text;
	private Date date;
	private Client sender;
//	private long idChatRoom;
	
	
	public Message(String text, Client sender) {
		super();
		this.text = text;
		this.sender = sender;
	}
	
	public String getText() {
		return text;
	}
	
	public Date getDate() {
		return date;
	}
	
	public Client getSender() {
		return sender;
	}
}
