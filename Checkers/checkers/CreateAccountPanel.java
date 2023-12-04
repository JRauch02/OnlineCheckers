package checkers;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class CreateAccountPanel extends JPanel{
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JLabel[] titleLabels =  new JLabel[2];
	private JLabel userNameLabel;
	private JLabel passwordLabel;
	private JLabel verifyPasswordLabel;
	private JTextField userNameTextField;
	private JPasswordField passwordTextField;
	private JPasswordField verifyPasswordTextField;
	private JButton submitButton;
	private JButton cancelButton;
	private CreateAccountController controller;
	private ClientGUI parent;
	private CheckersClient client;
	private JLabel statusMessageLabel;
	public JButton getSubmitButton() {
		return submitButton;
	}
	
	public JButton getCancelButton() {
		return cancelButton;
	}
	
	public JTextField getUserNameTextField() {
		return userNameTextField;
	}
	
	public JPasswordField getPasswordTextField() {
		return passwordTextField;
	}
	
	public JPasswordField getVerifyPasswordTextField() {
		return verifyPasswordTextField;
	}
	
	public void userNameError(String messageToSet) {
		//display error related to new thingy
		statusMessageLabel.setText(messageToSet);
	}
	
	
	public CreateAccountPanel(ClientGUI clientGUI) {
		setLayout(null);
		this.parent = clientGUI;
		this.setBackground(new Color(200,170,130));
		/*
		titleLabels[0] = new JLabel("Enter a Username and Password to Create an Account.");
		titleLabels[0].setFont(new Font("Tahoma", Font.PLAIN, 24));
		titleLabels[0].setBounds(89, 32, 300, 50);
		add(titleLabels[0]);
		
		titleLabels[1] = new JLabel("Your Password Must Be At Least 6 Characters");
		titleLabels[1].setFont(new Font("Tahoma", Font.PLAIN, 12));
		titleLabels[1].setBounds(110, 57, 300, 14);
		add(titleLabels[1]);
		*/
		
		userNameLabel = new JLabel("Username:");
		userNameLabel.setFont(new Font("Tahoma", Font.PLAIN, 12));
		userNameLabel.setBounds(180, 115, 99, 14);
		add(userNameLabel);
		
		passwordLabel = new JLabel("Password:");
		passwordLabel.setFont(new Font("Tahoma", Font.PLAIN, 12));
		passwordLabel.setBounds(180, 155, 70, 14);
		add(passwordLabel);
		
		verifyPasswordLabel= new JLabel("Verify Password:");
		verifyPasswordLabel.setFont(new Font("Tahoma", Font.PLAIN, 12));
		verifyPasswordLabel.setBounds(180, 195, 108, 14);
		add(verifyPasswordLabel);
		
		userNameTextField = new JTextField();
		userNameTextField.setBounds(290, 113, 86, 20);
		add(userNameTextField);
		userNameTextField.setColumns(10);
		
		passwordTextField = new JPasswordField();
		passwordTextField.setBounds(290, 153, 86, 20);
		add(passwordTextField);
		passwordTextField.setColumns(10);
		
		verifyPasswordTextField = new JPasswordField();
		verifyPasswordTextField.setBounds(290, 193, 86, 20);
		add(verifyPasswordTextField);
		verifyPasswordTextField.setColumns(10);
		
		submitButton = new JButton("Submit");
		submitButton.setForeground(new Color(1,50,32));
		submitButton.setBackground(new Color(215,185,145));
		submitButton.setBounds(180, 252, 89, 23);
		add(submitButton);
		
		cancelButton = new JButton("Cancel");
		cancelButton.setForeground(new Color(1,50,32));
		cancelButton.setBackground(new Color(215,185,145));
		cancelButton.setBounds(275, 252, 89, 23);
		add(cancelButton);
		
		statusMessageLabel = new JLabel("");
		statusMessageLabel.setBounds(195, 75, 166, 14);
		add(statusMessageLabel);
		this.client = parent.getChatClient();
		
		controller = new CreateAccountController(this, parent, client);
		
		JLabel lblNewLabel = new JLabel("Enter a Username and Password to Create an Account.");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblNewLabel.setBounds(140, 35, 311, 51);
		add(lblNewLabel);
		
		
	}
}
