package UDP;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.SocketException;
import java.net.UnknownHostException;

/**
 * This class creates a UDP client.
 * 
 * @author Nathalie Lebon et Yousra Jebbari
 *
 */
public class ClientUDP extends DatagramSocket {

	private MulticastSocket socket;
	private InetAddress group;
	private String username;

	/**
	 * Constructor of ClientUDP : initializes the usrename that is typed in by the
	 * user, the group and the socket of the client.
	 * 
	 * @throws SocketException
	 */
	public ClientUDP() throws SocketException {
		super();
		try {
			System.out.println("Enter your username.");
			BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
			username = stdIn.readLine();
			this.group = InetAddress.getByName("228.5.6.7");
			this.socket = new MulticastSocket(1234);
			this.socket.joinGroup(group);
		} catch (UnknownHostException e) {
			closeAll();
		} catch (IOException e) {
			closeAll();
		}

	}

	/**
	 * Takes the input of the user (String) and sends it to the other clients of the
	 * multicast socket.
	 */
	public void sendText() {
		BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
		String text;
		String textToSend;
		try {
			while (true) {
				text = stdIn.readLine();
				if (text.equals("bye"))
					closeAll();
				else {
					textToSend = username + " : " + text;
					DatagramPacket message = new DatagramPacket(textToSend.getBytes(), textToSend.length(), group,
							1234);
					socket.send(message);
				}
			}
		} catch (IOException e) {
			closeAll();
		}
	}

	/**
	 * Creates a new thread to receive messages and print them to the user.
	 */
	public void receiveMessage() {
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					byte[] buf = new byte[1000];
					DatagramPacket recv = new DatagramPacket(buf, buf.length);
					while (true) {
						socket.receive(recv);

						String text = new String(recv.getData(), 0, recv.getLength());
						System.out.println(text);
					}
				} catch (IOException e) {
					closeAll();
				}

			}
		}).start();
	}

	/**
	 * The socket leaves the group.
	 */
	public void leaveGroup() {
		try {
			socket.leaveGroup(group);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Closes the multicast socket of the client after leaving the group.
	 */
	public void closeAll() {
		leaveGroup();
		if (socket != null)
			socket.close();
		System.exit(1);
	}

	/**
	 * Main method. Creates a ClientUDP and starts receiving and sending messages.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			ClientUDP client = new ClientUDP();
			client.receiveMessage();
			client.sendText();
		} catch (SocketException e) {
			e.printStackTrace();
		}

	}

}
