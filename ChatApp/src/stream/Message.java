package stream;

import java.util.Date;

public class Message {
	
	private String text;
	private Date date;
	private Client sender;
	private long idChatRoom;
	
	public Message(String text, Date date, Client sender, long idChatRoom) {
		super();
		this.text = text;
		this.date = date;
		this.sender = sender;
		this.idChatRoom = idChatRoom;
	}
	
	public Message(String text, Client sender) {
		super();
		this.text = text;
		this.sender = sender;
	}
	
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public Client getSender() {
		return sender;
	}
	public void setSender(Client sender) {
		this.sender = sender;
	}
	public long getIdChatRoom() {
		return idChatRoom;
	}
	public void setIdChatRoom(long idChatRoom) {
		this.idChatRoom = idChatRoom;
	}
}
