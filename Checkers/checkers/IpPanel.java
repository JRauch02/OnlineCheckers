package checkers;

import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JTextField;
import javax.swing.JButton;

public class IpPanel extends JPanel{
	private JTextField serverAddress;
	private JLabel titleLabel;
	private JLabel serverAddressLabel;
	private JButton submitButton;
	
	public IpPanel(ClientGUI parent) {
		setLayout(null);
		
		titleLabel = new JLabel("Enter IP Address of Server:");
		titleLabel.setFont(new Font("Tahoma", Font.PLAIN, 24));
		titleLabel.setBounds(138, 173, 298, 48);
		add(titleLabel);
		
		serverAddress = new JTextField();
		serverAddress.setBounds(237, 244, 166, 20);
		add(serverAddress);
		serverAddress.setColumns(10);
		
		serverAddressLabel = new JLabel("Server Address:");
		serverAddressLabel.setBounds(138, 247, 89, 14);
		add(serverAddressLabel);
		
		submitButton = new JButton("Submit");
		submitButton.setBounds(237, 297, 89, 23);
		
		submitButton.addActionListener(new ActionListener( ) {
			public void actionPerformed(ActionEvent e) {
				String address = serverAddress.getText();
				
				if (parent.openClientConnection(address)) {
					parent.changeToInitialView();
				}
				else {
					System.out.println("IP Not Running a Server");
				}
			}
		});
		
		add(submitButton);
		
	}
}
