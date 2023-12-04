package checkers;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class LoginPanel extends JPanel{
	private JLabel titleLabel;
	private JLabel userNameLabel;
	private JLabel passwordLabel;
	private JButton submitButton;
	private JButton cancelButton;
	private JLabel incorrectLoginLabel;
	private LoginController controller;
	private JTextField userNameTextField;
	private JPasswordField passwordTextField;
	private ClientGUI parent;
	private CheckersClient client;
	
	public JButton getSubmitButton() {
		return submitButton;
	}
	
	public JButton getCancelButton() {
		return cancelButton;
	}
	
	public JLabel getIncorrectLoginLabel() {
		return incorrectLoginLabel;
	}
	
	public void loginError() {
		//this.incorrectLoginLabel.setText("Username and Pasword are Incorrect");
		this.incorrectLoginLabel.setVisible(true);
	}
	
	public JTextField getUserNameTextField() {
		return userNameTextField;
	}
	
	public JPasswordField getPasswordTextField() {
		return passwordTextField;
	}
	

	public LoginPanel(ClientGUI clientGUI) {
		setLayout(null);
		//setLayout();
		this.setBackground(new Color(200,170,130));
		titleLabel = new JLabel("Enter Your Username and Password to Log In");
		titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
		titleLabel.setFont(new Font("Tahoma", Font.PLAIN, 12));
		titleLabel.setBounds(145, 60, 292, 14);
		add(titleLabel);
		
		userNameLabel = new JLabel("Username:");
		userNameLabel.setFont(new Font("Tahoma", Font.PLAIN, 12));
		userNameLabel.setBounds(185, 110, 98, 14);
		add(userNameLabel);
		
		passwordLabel = new JLabel("Password:");
		passwordLabel.setFont(new Font("Tahoma", Font.PLAIN, 12));
		passwordLabel.setBounds(185, 144, 98, 14);
		add(passwordLabel);
		
		userNameTextField = new JTextField();
		userNameTextField.setBounds(285, 108, 86, 20);
		add(userNameTextField);
		userNameTextField.setColumns(10);
		
		passwordTextField = new JPasswordField();
		passwordTextField.setBounds(285, 142, 86, 20);
		add(passwordTextField);
		passwordTextField.setColumns(10);
		
		submitButton = new JButton("Submit");
		submitButton.setForeground(new Color(1,50,32));
		submitButton.setBackground(new Color(215,185,145));
		submitButton.setBounds(185, 195, 77, 23);
		add(submitButton);
		
		cancelButton = new JButton("Cancel");
		cancelButton.setForeground(new Color(1,50,32));
		cancelButton.setBackground(new Color(215,185,145));
		cancelButton.setBounds(285, 195, 86, 23);
		add(cancelButton);
		
		incorrectLoginLabel = new JLabel("Username and Password are Incorrect");
		incorrectLoginLabel.setHorizontalAlignment(SwingConstants.CENTER);
		incorrectLoginLabel.setForeground(Color.RED);
		incorrectLoginLabel.setBounds(145, 35, 292, 14);
		add(incorrectLoginLabel);
		incorrectLoginLabel.setVisible(false);
		 
		this.parent = clientGUI;
		this.client = parent.getChatClient();
		//this.controller = new LoginController(submitButton, cancelButton, incorrectLoginLabel, userNameTextField, passwordTextField, parent);
		this.controller = new LoginController(this, parent,this.client);
		
		
	}
}
