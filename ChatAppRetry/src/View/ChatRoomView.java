package View;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.border.LineBorder;

import stream.Client;

public class ChatRoomView extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
//	private Client client;
	private Client client;
	private JTextArea messagesArea;
	private String messageToSend;

	public Client getClient() {
		return this.client;
	}

	public ChatRoomView(Client client) {
		super("Chat room");

		this.client = client;

		this.setSize(700, 900);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setLayout(new BorderLayout());

		messagesArea = new JTextArea();
		messagesArea.setEditable(false);
		messagesArea.setBackground(Color.GRAY);
		messagesArea.setLineWrap(true);
		messagesArea.setFont(new Font("Verdana", Font.PLAIN, 20));
		messagesArea.setBorder(new LineBorder(Color.BLACK, 4, true));
		JScrollPane messagesScrollPane = new JScrollPane(messagesArea);
		messagesScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		add(messagesScrollPane, BorderLayout.CENTER);

		JPanel topArea = new JPanel();
		topArea.setForeground(Color.BLACK);
		topArea.setBorder(new LineBorder(Color.BLACK, 4, true));

		JLabel usernameArea = new JLabel(this.client.getUsername());
		usernameArea.setFont(new Font("Lucida Handwriting", Font.PLAIN, 50));
		topArea.add(usernameArea, BorderLayout.LINE_START);
		add(topArea, BorderLayout.PAGE_START);

		JButton disconnectUser = new JButton("Disconnect");
		disconnectUser.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (getClient() != null) {
					System.exit(1);
				}
			}

		});
		topArea.add(disconnectUser, BorderLayout.LINE_END);

		JPanel newMessageArea = new JPanel();
		newMessageArea.setPreferredSize(new Dimension(680, 200));
		newMessageArea.setBackground(Color.BLUE);

		JTextArea newMessageTextArea = new JTextArea();
		newMessageTextArea.setFont(new Font("Verdana", Font.PLAIN, 20));
		newMessageTextArea.setBackground(Color.WHITE);
		newMessageTextArea.setLineWrap(true);
		JScrollPane newMessageScrollPane = new JScrollPane(newMessageTextArea);
		newMessageScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		newMessageScrollPane.setPreferredSize(new Dimension(680, 150));
		newMessageScrollPane.setBorder(new LineBorder(Color.BLACK, 4, true));
		newMessageArea.add(newMessageScrollPane, BorderLayout.PAGE_END);

		JButton sendButton = new JButton("Send");
		sendButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (!newMessageTextArea.getText().isEmpty()) {
					messageToSend = newMessageTextArea.getText();
					client.sendMessage(messageToSend);
					receiveMessage(messageToSend + "\n");
					newMessageTextArea.setText("");
				}

			}

		});
		newMessageArea.add(sendButton, BorderLayout.LINE_END);

		add(newMessageArea, BorderLayout.PAGE_END);

		setVisible(true);
	}

	public void receiveMessage(String message) {
		this.messagesArea.append(message);
	}

}
