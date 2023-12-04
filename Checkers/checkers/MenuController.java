package checkers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;

public class MenuController {
	private JLabel menuLabel;
	private JButton hostGameButton;
	private JButton joinGameButton;
	private JButton logOutButton;
	public MenuController(MenuPanel menuPanel, ClientGUI parent, CheckersClient client) {
		this.menuLabel = menuPanel.getMenuLabel();
		this.hostGameButton = menuPanel.getHostGameButton();
		this.joinGameButton = menuPanel.getJoinGameButton();
		this.logOutButton = menuPanel.getLogOutButton();
		
		logOutButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				parent.changeToInitialView();
			}
		});
		
		hostGameButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				client.sendHostGame();
				parent.getGamePanel().getBoard().setPlayerNumber(true);
				parent.getGamePanel().buildBoard();
				parent.getGamePanel().addBoard();
			}
		});
		
		joinGameButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				client.sendJoinGame();
				parent.getGamePanel().getBoard().setPlayerNumber(false);
				parent.getGamePanel().buildBoard();
				parent.getGamePanel().addBoard();
			}
		});
	}
}
