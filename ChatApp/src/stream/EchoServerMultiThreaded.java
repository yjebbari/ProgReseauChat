/***
 * EchoServer
 * Example of a TCP server
 * Date: 10/01/04
 * Authors:
 */

package stream;

import java.io.*;
import java.net.*;
import java.util.ArrayList;

public class EchoServerMultiThreaded {

	
	/**
	 * main method
	 * 
	 * @param EchoServer port
	 * 
	 **/
	public static void main(String args[]) {
		ServerSocket listenSocket;

		ArrayList<Client> clientList = new ArrayList<Client>();
		
		if (args.length != 1) {
			System.out.println("Usage: java EchoServer <EchoServer port>");
			System.exit(1);
		}
		try {
			listenSocket = new ServerSocket(Integer.parseInt(args[0])); // port
			System.out.println("Server ready...");
			while (true) {
				Socket clientSocket = listenSocket.accept();
				System.out.println("Connexion from:" + clientSocket.getInetAddress());
				ClientThreadSend  ctSend = new ClientThreadSend(clientSocket, clientList);
				ClientThreadReceive ctReceive = new ClientThreadReceive(clientSocket);
				ctSend.start();
				ctReceive.start();
				clientList.add(new Client(ctSend, ctReceive));
			}
		} catch (Exception e) {
			System.err.println("Error in EchoServer:" + e);
		}
	}
}
