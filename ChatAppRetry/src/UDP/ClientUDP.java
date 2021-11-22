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

public class ClientUDP extends DatagramSocket {

	private MulticastSocket socket;
	private InetAddress group;
	private String username;

	public ClientUDP() throws SocketException {
		super();
		try {
			System.out.println("Enter your username.");
			BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
			username = stdIn.readLine();
			this.group = InetAddress.getByName("228.5.6.7");
			this.socket = new MulticastSocket(1234);
//			this.manager = new ServerUDP();
			this.socket.joinGroup(group);
		} catch (UnknownHostException e) {
			closeAll();
		} catch (IOException e) {
			closeAll();
		}

	}

	public void sendText() {
				BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
				String text;
				String textToSend;
				try {
					while (true) {
						text = stdIn.readLine();
						textToSend = username  + " : " + text;
						DatagramPacket message = new DatagramPacket(textToSend.getBytes(), textToSend.length(), group, 1234);
						socket.send(message);
					}
				} catch (IOException e) {
					closeAll();
				}
	}

	public void receiveMessage() {
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					// get their responses!
					byte[] buf = new byte[1000];
					DatagramPacket recv = new DatagramPacket(buf, buf.length);
					while (true) {
						socket.receive(recv);
						
						String text = new String (recv.getData(), 0, recv.getLength());
						System.out.println(text);
					}
				} catch (IOException e) {
					closeAll();
				}

			}
		}).start();
	}

	public void leaveGroup() {
		try {
			socket.leaveGroup(group);
		} catch (IOException e) {
			closeAll();
		}
	}

	public void closeAll() {
		leaveGroup();
		if (socket != null)
			socket.close();
	}

	public static void main(String[] args) {
		try {
			ClientUDP client = new ClientUDP();
			client.receiveMessage();
			client.sendText();
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
