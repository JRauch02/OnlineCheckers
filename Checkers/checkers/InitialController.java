package checkers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;

public class InitialController {
	private ClientGUI parent;
	private JLabel titleLabel;
	private JButton loginButton;
	private JButton createButton;
	public InitialController(InitialPanel initialPanel, ClientGUI parent) {
		this.parent= parent;
		this.loginButton = initialPanel.getLoginButton();
		this.createButton = initialPanel.getCreateButton();
		
		this.loginButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				parent.changeToLoginView();
			}
		});
		
		this.createButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				parent.changeToCreateAccountView();
			}
		});
	}
}
