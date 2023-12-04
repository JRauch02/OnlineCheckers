package checkers;

import javax.swing.JPanel;
import javax.swing.JLabel;

import java.awt.Color;

import javax.swing.JButton;
import javax.swing.SwingConstants;

public class MenuPanel extends JPanel{
	private JLabel menuLabel;
	private JButton hostGameButton;
	private JButton joinGameButton;
	private JButton logOutButton;
	private MenuController controller;
	private ClientGUI parent;
	private CheckersClient client;
	
	public JLabel getMenuLabel() {
		return menuLabel;
	}
	
	public JButton getHostGameButton() {
		return hostGameButton;
	}
	
	public JButton getJoinGameButton() {
		return joinGameButton;
	}
	
	public JButton getLogOutButton() {
		return logOutButton;
	}
	
	public MenuPanel(ClientGUI clientGUI) {
		setLayout(null);
		this.setBackground(new Color(200,170,130));
		
		menuLabel = new JLabel("Menu");
		menuLabel.setHorizontalAlignment(SwingConstants.CENTER);
		menuLabel.setBounds(240, 70, 75, 14);
		add(menuLabel);
		
		hostGameButton = new JButton("Host Game");
		hostGameButton.setForeground(new Color(1,50,32));
		hostGameButton.setBackground(new Color(215,185,145));
		hostGameButton.setBounds(220, 120, 115, 25);
		add(hostGameButton);
		
		joinGameButton = new JButton("Join Game");
		joinGameButton.setForeground(new Color(1,50,32));
		joinGameButton.setBackground(new Color(215,185,145));
		joinGameButton.setBounds(220, 180, 115, 25);
		add(joinGameButton);
		
		logOutButton = new JButton("Log Out");
		logOutButton.setForeground(new Color(1,50,32));
		logOutButton.setBackground(new Color(215,185,145));
		logOutButton.setBounds(220, 242, 112, 23);
		add(logOutButton);
		
		this.parent = clientGUI;
		client = parent.getChatClient();
		
		controller = new MenuController(this, this.parent, this.client);
		
	}
}
