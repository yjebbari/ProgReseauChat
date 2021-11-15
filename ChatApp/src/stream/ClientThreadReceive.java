/***
 * ClientThread
 * Example of a TCP server
 * Date: 14/12/08
 * Authors:
 */

package stream;

import java.io.*;
import java.net.*;

public class ClientThreadReceive
	extends Thread {

	private PrintStream socOut = null;
	private BufferedReader socIn = null;
	
	private Socket clientSocket;
	
	ClientThreadReceive(Socket s) {
		this.clientSocket = s;
	}

	public void receiveMessage(String line) {
		this.socOut.println(line);
		System.out.println("thread " + getId() + " received : " + line);
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
    		while (true) {
    		  String line = socIn.readLine();
    		  socOut.println(line);
    		}
    	} catch (Exception e) {
        	System.err.println("Error in EchoServer:" + e); 
        }
       }
  
  }

  
