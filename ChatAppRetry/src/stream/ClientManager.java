package stream;

import java.io.*;
import java.net.*;
import java.util.ArrayList;

public class ClientManager implements Runnable {

	private static ArrayList<ClientManager> clientManagers = new ArrayList<>();
	private Socket clientSocket;
	private BufferedWriter socOut;
	private BufferedReader socIn;
	private String clientUsername;

	public String getClientUsername() {
		return clientUsername;
	}

	public BufferedWriter getSocOut() {
		return socOut;
	}

	public ClientManager(Socket clientSocket) {
		super();
		try {
			this.clientSocket = clientSocket;
			this.socIn = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			this.socOut = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
			this.clientUsername = socIn.readLine();
			clientManagers.add(this);
			sendMessage(clientUsername + " has entered the chat!");
		} catch (IOException e) {
			closeAll(clientSocket, socIn, socOut);
		}
	}

	/**
	 * TODO
	 * 
	 * @param clientSocket the client socket
	 **/
	public void run() {
		String text;

		while (true) {
			try {
				text = socIn.readLine();
				sendMessage(text);
			} catch (IOException e) {
				closeAll(clientSocket, socIn, socOut);
				break;
			}
		}
	}

	public void sendMessage(String text) {
		for (ClientManager clientManager : clientManagers) {
			try {
				if (!clientManager.getClientUsername().equals(this.clientUsername)) {
					clientManager.getSocOut().write(text);
					clientManager.getSocOut().newLine();
					clientManager.getSocOut().flush();
				}
			} catch (Exception e) {
				closeAll(clientSocket, socIn, socOut);
			}
		}
	}

	public void deleteClientManager() {
		clientManagers.remove(this);
		sendMessage(this.clientUsername + " has left the chat");
	}

	public void closeAll(Socket socket, BufferedReader socIn, BufferedWriter socOut) {
		deleteClientManager();
		try {
			if (socIn != null)
				socIn.close();
			if (socOut != null)
				socOut.close();
			if (socket != null)
				socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
