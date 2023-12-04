package checkers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class LoginController {
	private JButton submitButton;
	private JButton cancelButton;
	private JLabel incorrectLogin;
	private JTextField userNameTextField;
	private JPasswordField passwordTextField;
	private ClientGUI parent;
	private CheckersClient client;
	private LoginData loginData;
	public LoginController(LoginPanel loginPanel, ClientGUI parent, CheckersClient client) {
		
		submitButton = loginPanel.getSubmitButton();
		cancelButton = loginPanel.getCancelButton();
		incorrectLogin = loginPanel.getIncorrectLoginLabel();
		userNameTextField = loginPanel.getUserNameTextField();
		passwordTextField = loginPanel.getPasswordTextField();
		this.parent = parent;
		cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				parent.changeToInitialView();
				//System.out.println("got here");
			}
		});
		submitButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String password = new String (passwordTextField.getPassword());
				if(userNameTextField.getText().isEmpty() || password.isEmpty()) {
					incorrectLogin.setText("UserName Or Password is Empty");
				}
				else {
					loginData = new LoginData(userNameTextField.getText(), password);
					client.sendLoginData(loginData);
				}
			}
		});
	}

}
