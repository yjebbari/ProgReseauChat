package stream;

import java.io.*;
import java.net.*;
import java.util.ArrayList;

public class EchoServerMultiThreadedReceive extends Thread {

	ArrayList<Message> receivedMessages = new ArrayList<Message>();
	ArrayList<Client> clientList = new ArrayList<Client>();

	public EchoServerMultiThreadedReceive(ArrayList<Client> clientList) {
		this.clientList = clientList;
	}

	public void run() {
		try {
			//PrintStream socOut = null;
			BufferedReader socIn = null;
			while (true) {
				//System.out.println(clientList.size());
				for (Client c : clientList) {
					socIn = new BufferedReader(new InputStreamReader(c.getClientSocket().getInputStream()));
					//socOut = new PrintStream(c.getClientSocket().getOutputStream());
					String line = socIn.readLine();
					receivedMessages.add(new Message(line,c));
					//socOut.println(line);
					System.out.println("server receive: " + line + " , sending thread: " + c.getThreadSend().getId());
				}
			}
		} catch (Exception e) {
			System.err.println("Error in EchoServer:" + e);
		}
	}

	public ArrayList<Message> getReceivedMessages() {
		return receivedMessages;
	}

	public ArrayList<Client> getClientList() {
		return clientList;
	}
}
