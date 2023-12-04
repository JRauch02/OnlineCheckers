package checkers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

public class LeaderboardController {
	private JButton logoutButton;
	private JButton exitButton;

	public LeaderboardController (LeaderboardPanel leaderboardPanel, ClientGUI parent) {
		logoutButton = leaderboardPanel.getLogoutButton();
		exitButton = leaderboardPanel.getExitButton();
		
		logoutButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				parent.changeToInitialView();
			}
		});
		
		exitButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
	}
}
