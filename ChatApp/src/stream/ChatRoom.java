package stream;

import java.util.ArrayList;

public class ChatRoom {
	
	private long idChatRoom;
	private String name;
	private ArrayList<Client> connectedMembers;
	private ArrayList<Message> chatMessages;
	
	public ChatRoom(long idChatRoom, String name, ArrayList<Client> connectedMembers, ArrayList<Message> chatMessages) {
		super();
		this.idChatRoom = idChatRoom;
		this.name = name;
		this.connectedMembers = connectedMembers;
		this.chatMessages = chatMessages;
	}

	public long getIdChatRoom() {
		return idChatRoom;
	}

	public void setIdChatRoom(long idChatRoom) {
		this.idChatRoom = idChatRoom;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ArrayList<Client> getConnectedMembers() {
		return connectedMembers;
	}

	public void setConnectedMembers(ArrayList<Client> connectedMembers) {
		this.connectedMembers = connectedMembers;
	}

	public ArrayList<Message> getChatMessages() {
		return chatMessages;
	}

	public void setChatMessages(ArrayList<Message> chatMessages) {
		this.chatMessages = chatMessages;
	}
}
