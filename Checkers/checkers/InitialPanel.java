package checkers;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;



public class InitialPanel extends JPanel{
	private JLabel titleLabel;
	private JButton loginButton;
	private JButton createButton;
	private InitialController controller;
	private ClientGUI parent;
	
	public JButton getLoginButton() {
		return loginButton;
	}
	
	public JButton getCreateButton() {
		return createButton;
	}
	
	
	
	public InitialPanel(ClientGUI clientGUI) {
		this.setBackground(new Color(200,170,130));
		this.parent = clientGUI;
		setLayout(null);
		titleLabel = new JLabel("Account Information");
		titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
		titleLabel.setBounds(170, 45, 255, 40);
		titleLabel.setFont(new Font("Tahoma", Font.PLAIN, 24));
		add(titleLabel);
		
		loginButton = new JButton("Login");
		loginButton.setBounds(245, 130, 89, 23);
		loginButton.setForeground(new Color(1,50,32));
		loginButton.setBackground(new Color(215,185,145));
		add(loginButton);
		
		createButton= new JButton("Create");
		createButton.setBounds(245, 180, 89, 23);
		createButton.setForeground(new Color(1,50,32));
		createButton.setBackground(new Color(215,185,145));
		add(createButton);
		
		controller = new InitialController(this, parent);
	}
}
