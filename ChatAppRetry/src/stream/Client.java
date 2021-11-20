package stream;

import java.io.*;
import java.net.*;

public class Client {
	private Socket clientSocket;
	private BufferedWriter socOut;
	private BufferedReader socIn;
	private String username;

	public Client(Socket socket, String username) {
		super();
		try {
			this.clientSocket = socket;
			this.username = username;

			this.socIn = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			this.socOut = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
		} catch (IOException e) {
			closeAll(clientSocket, socIn, socOut);
		}
	}

	public void sendMessage() {
		try {
			socOut.write(username);
			socOut.newLine();
			socOut.flush();

			BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
			while (true) {
				String text = stdIn.readLine();
				socOut.write(username + " : " + text);
				socOut.newLine();
				socOut.flush();
			}
		} catch (IOException e) {
			closeAll(clientSocket, socIn, socOut);
		}
	}

	public void readMessages() {
		new Thread(new Runnable() {
			@Override
			public void run() {
				String textReceived;
				while (true) {
					try {
						textReceived = socIn.readLine();
						System.out.println(textReceived);
					} catch (IOException e) {
						closeAll(clientSocket, socIn, socOut);
					}
				}
			}
		}).start();
	}

	public void closeAll(Socket socket, BufferedReader socIn, BufferedWriter socOut) {
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

	public static void main(String[] args) {
		if (args.length != 2) {
			System.out.println("Usage: java EchoClient <EchoServer host> <EchoServer port>");
			System.exit(1);
		}

		try {
			BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
			System.out.println("Enter your username.");
			String username = stdIn.readLine();

			Socket echoSocket = new Socket(args[0], new Integer(args[1]).intValue());
			Client client = new Client(echoSocket, username);
			client.readMessages();
			client.sendMessage();
		} catch (IOException e) {
			System.err.println("Couldn't get I/O for " + "the connection to:" + args[0]);
			System.exit(1);
		}
	}
}
