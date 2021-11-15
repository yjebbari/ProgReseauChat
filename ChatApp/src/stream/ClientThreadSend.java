package stream;

import java.io.*;
import java.net.*;

public class ClientThreadSend
	extends Thread {
	private PrintStream socOut = null;
	private BufferedReader socIn = null;
	
	private Socket clientSocket;
	
	ClientThreadSend(Socket s) {
		this.clientSocket = s;
	}

 	/**
  	* receives a request from client then sends an echo to the client
  	* @param clientSocket the client socket
  	**/
	public void run() {
    	  try {
    		socIn = new BufferedReader(
    			new InputStreamReader(clientSocket.getInputStream()));    
    		socOut = new PrintStream(clientSocket.getOutputStream());
    		BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
    		while (true) {
    			
    		  socOut.println(stdIn.readLine());
    		}
    	} catch (Exception e) {
        	System.err.println("Error in EchoServer:" + e); 
        }
       }
  
  }

 
