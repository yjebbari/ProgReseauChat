package stream;

public class Message {
	
	private String message;
	private Date date;
	private Client sender;
	private long idChatRoom;
	
	public Message(String message, Date date, Client sender, long idChatRoom) {
		super();
		this.message = message;
		this.date = date;
		this.sender = sender;
		this.idChatRoom = idChatRoom;
	}
	
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
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
