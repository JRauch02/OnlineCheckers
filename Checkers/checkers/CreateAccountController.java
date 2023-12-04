package checkers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class CreateAccountController {
	private JPasswordField passwordTextField;
	private JTextField userNameTextField;
	private JButton submitButton;
	private JButton cancelButton;
	private JPasswordField verifyPasswordTextField;
	private CheckersClient client;
	public CreateAccountController(CreateAccountPanel createAccountPanel, ClientGUI parent, CheckersClient client) {
		this.passwordTextField = createAccountPanel.getPasswordTextField();
		this.userNameTextField = createAccountPanel.getUserNameTextField();
		this.verifyPasswordTextField = createAccountPanel.getVerifyPasswordTextField();
		this.submitButton = createAccountPanel.getSubmitButton();
		this.cancelButton = createAccountPanel.getCancelButton();
		this.cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				parent.changeToInitialView();
				//initialPanel.getParent().changeToLoginView();
			}
		});
		this.submitButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//check that fields are valid,
				String password = new String(passwordTextField.getPassword());
				String passwordVerify = new String(verifyPasswordTextField.getPassword());
				if (password.isEmpty() || userNameTextField.getText().isEmpty() || passwordVerify.isEmpty()) {
					//System.out.println("stuff is empty");
					createAccountPanel.userNameError("A Field is Empty");
				}
				else if (password.length() < 6) {
					createAccountPanel.userNameError("Password Too Short");
				}
				else if (!password.equals(passwordVerify)){
					createAccountPanel.userNameError("Passwords don't match!");
				}
				else {
					LoginData newUserLogin = new LoginData(userNameTextField.getText(), password);
					client.sendNewLogin(newUserLogin);
				}
				//send to server
				//display error if something is wrong.
				//client.sendNewLogin();
			}
		});
			
		
	}
}
