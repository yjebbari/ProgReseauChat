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

public class Server {

	
	
	
	/**
	 * main method
	 * 
	 * @param EchoServer port
	 * 
	 **/
	public static void main(String args[]) {
		ServerSocket listenSocket = null;
		
		if (args.length != 1) {
			System.out.println("Usage: java EchoServer <EchoServer port>");
			System.exit(1);
		}
		try {
			listenSocket = new ServerSocket(Integer.parseInt(args[0])); // port
			System.out.println("Server ready...");
			
			while (!listenSocket.isClosed()) {
				Socket clientSocket = listenSocket.accept();
				System.out.println("Connexion from:" + clientSocket.getInetAddress());
				
				ClientManager clientManager = new ClientManager(clientSocket);
				Thread thread = new Thread(clientManager);
				thread.start();
			}
		} catch (Exception e) {
			try {
				if(listenSocket != null)
					listenSocket.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
			System.err.println("Error in EchoServer:" + e);
		}
	}
}
