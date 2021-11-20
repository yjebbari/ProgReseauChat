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
	private String messageHistory;

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
			messageHistory = "D:/documents/insa_lyon/4A/S1/Programmation_reseau/Chat/ChatAppRetry/history/messageHistory.txt";
			loadTextHistory(messageHistory);

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

		while (clientSocket.isConnected()) {
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

	public void loadTextHistory(String messageHistory) {
		BufferedReader fileReader;
		try {
			socOut.write("----- Previous messages -----");
			socOut.newLine();
			socOut.flush();
			fileReader = new BufferedReader(new FileReader(messageHistory));
			String line;
			line = fileReader.readLine();
			while (line != null){
				socOut.write(line);
				socOut.newLine();
				socOut.flush();
				
				line = fileReader.readLine();
			}
			socOut.write("-----------------------------");
			socOut.newLine();
			socOut.flush();
			fileReader.close();
		} catch (IOException e) {
			try {
				socOut.write("Error in loading previous messages.");
				socOut.newLine();
				socOut.flush();
				closeAll(clientSocket, socIn, socOut);
			} catch (Exception exc) {
				closeAll(clientSocket, socIn, socOut);
			}
		}
	}
}
