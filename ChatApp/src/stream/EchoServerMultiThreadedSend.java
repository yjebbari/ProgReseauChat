package stream;

import java.io.*;
import java.net.*;
import java.util.ArrayList;

public class EchoServerMultiThreadedSend extends Thread {

	//ArrayList<Message> messagesToSend = new ArrayList<Message>();
	ArrayList<Client> clientList = new ArrayList<Client>();
	EchoServerMultiThreadedReceive serverReceive = new EchoServerMultiThreadedReceive(clientList);
	
	public EchoServerMultiThreadedSend(ArrayList<Client> clientList) {
		super();
		this.clientList = clientList;
	}

	public void run() {
		try {
			this.start();
			//PrintStream socOut = null;
			//BufferedReader socIn = null;
			while (true) {
				for (Message m : serverReceive.getReceivedMessages()) {
					String line = m.getText();
					for (Client c : clientList) {
						//if (this.getId() != c.getThreadSend().getId())
							c.getThreadReceive().receiveMessage(line);
					}
					serverReceive.getReceivedMessages().remove(m);
				}
			}
		} catch (Exception e) {
			System.err.println("Error in EchoServer:" + e);
		}
	}
}
