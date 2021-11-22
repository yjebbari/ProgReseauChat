package stream;

import java.io.*;
import java.net.*;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JOptionPane;

import View.ChatRoomView;

public class Client {
	private Socket clientSocket;
	private BufferedWriter socOut;
	private BufferedReader socIn;
	private String username;
	private ChatRoomView clientView;
	private long id;

	public String getUsername() {
		return username;
	}

	public BufferedWriter getSocOut() {
		return socOut;
	}
	
	public void setId() {
		try {
			this.id = Long.parseLong(socIn.readLine());
			System.out.println(this.id);
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			closeAll();
		}
	}

	public Client(Socket socket, String username) {
		super();
		try {
			this.clientSocket = socket;
			this.username = username;

			this.socIn = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			this.socOut = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));

			clientView = new ChatRoomView(this);
		} catch (IOException e) {
			closeAll();
		}
	}

//	public void sendMessage() {
//		try {
//			socOut.write(username);
//			socOut.newLine();
//			socOut.flush();
//
//			BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
//			while (clientSocket.isConnected()) {
//				String text = stdIn.readLine();
//				socOut.write(username + " : " + text);
//				socOut.newLine();
//				socOut.flush();
//			}
//		} catch (IOException e) {
//			closeAll(clientSocket, socIn, socOut);
//		}
//	}

	public void sendMessage(String text) {
		try {
			SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
			Date currentDate = new Date();
			
			socOut.write("(" + dateFormat.format(currentDate) + ") " + username + " : " + text);
			socOut.newLine();
			socOut.flush();
		} catch (IOException e) {
			closeAll();
		}
	}

	public void readMessages() {
		new Thread(new Runnable() {
			@Override
			public void run() {
				String textReceived;
				while (clientSocket.isConnected()) {
					try {
						textReceived = socIn.readLine();
						clientView.dispalySentMessage(textReceived);
						clientView.dispalySentMessage("\n");
					} catch (IOException e) {
						closeAll();
					}
				}
			}
		}).start();
	}

	public void closeAll() {

		try {
			if (socIn != null)
				socIn.close();
			if (socOut != null)
				socOut.close();
			if (clientSocket != null)
				clientSocket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		if (args.length != 2) {
			System.out.println("Usage: java EchoClient <EchoServer host> <EchoServer port>");
			System.exit(1);
		}

		try {
			String username = JOptionPane.showInputDialog("Enter your username");
			if (username != null) {
				Socket echoSocket = new Socket(args[0], new Integer(args[1]).intValue());

				Client client = new Client(echoSocket, username);
				
				client.readMessages();
//			client.sendMessage();
				client.getSocOut().write(username);
				client.getSocOut().newLine();
				client.getSocOut().flush();
//				client.setId();
			}

		} catch (IOException e) {
			System.err.println("Couldn't get I/O for " + "the connection to:" + args[0]);
			System.exit(1);
		}
	}
}
