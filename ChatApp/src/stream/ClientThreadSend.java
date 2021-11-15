package stream;

import java.io.*;
import java.net.*;
import java.util.ArrayList;

public class ClientThreadSend extends Thread {
	private PrintStream socOut = null;
	private BufferedReader socIn = null;

	private ArrayList<Client> clientList;

	private Socket clientSocket;

	ClientThreadSend(Socket s, ArrayList<Client> list) {
		this.clientSocket = s;
		this.clientList = list;
	}

	/**
	 * receives a request from client then sends an echo to the client
	 * 
	 * @param clientSocket the client socket
	 **/
	public void run() {
		try {
			socIn = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			socOut = new PrintStream(clientSocket.getOutputStream());
			BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
			while (true) {
				System.out.println("Thread " + getId() + " send");
				String line = stdIn.readLine();
				socOut.println(line);
				for (Client c : clientList) {
					if (this.getId() != c.getThreadSend().getId())
						c.getThreadReceive().receiveMessage(line);
				}
			}
		} catch (Exception e) {
			System.err.println("Error in EchoServer:" + e);
		}
	}

}
