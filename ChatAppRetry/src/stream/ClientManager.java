package stream;

import java.io.*;
import java.net.*;
import java.nio.file.Paths;
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
//			// send the id to the client
//			System.out.println(clientManagers.size());
//			socOut.write(clientManagers.size());
//			socOut.newLine();
//			socOut.flush();
			
			clientManagers.add(this);
			messageHistory = Paths.get("").toAbsolutePath().getParent().toString(); 
			messageHistory = messageHistory.replace(System.getProperty("file.separator"), "/");
			
			messageHistory += "/history/messageHistory.txt";
			loadTextHistory();

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

	
	/*
	 * when someone disconnects it sends a message with null which is a problem
	 */
	public void sendMessage(String text) {
		// Saving new texts into messageHistory.txt
		try {
			PrintWriter fileWriter = new PrintWriter(new BufferedWriter(new FileWriter(messageHistory, true)));
			fileWriter.println(text);
			fileWriter.close();
		} catch (IOException e) {
			System.out.println("Error in saving message.");
		}
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

	public void loadTextHistory() {
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
