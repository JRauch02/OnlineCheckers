package checkers;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;

public class GameSidePanel extends JPanel{
	private ClientGUI parent;
	private JLabel piecesTakenLabel;
	private JLabel piecesTakenCounterLabel;
	private JLabel turnLabel;
	private JButton logOutButton;
	private Integer piecesTaken = 0;
	
	
	public void pieceTaken() {
		piecesTaken++;
		piecesTakenCounterLabel.setText(piecesTaken.toString());
	}
	
	public void setTurnLabel(String text) {
		turnLabel.setText(text);
	}
	
	public String getTurnLabel() {
		return turnLabel.getText();
	}
	
	public GameSidePanel(ClientGUI parent) {
		this.parent = parent;
		this.setBackground(new Color(200,170,130));
		this.setSize(100, 600);
		this.setBorder(BorderFactory.createLineBorder(Color.WHITE));
		setLayout(null);
		
		piecesTakenLabel = new JLabel("Pieces Taken:");
		piecesTakenLabel.setBounds(20, 11, 100, 14);
		add(piecesTakenLabel);
		
		piecesTakenCounterLabel = new JLabel("0");
		piecesTakenCounterLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));
		piecesTakenCounterLabel.setHorizontalAlignment(SwingConstants.CENTER);
		piecesTakenCounterLabel.setBounds(20, 36, 66, 47);
		add(piecesTakenCounterLabel);
		
		logOutButton = new JButton("Log Out");
		logOutButton.setBounds(10, 516, 100, 23);
		
		logOutButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				parent.getChatClient().sendLeaveGame();
				parent.changeToLoginView();
			}
		});
		add(logOutButton);
		
		turnLabel = new JLabel("Game Not Started");
		turnLabel.setHorizontalAlignment(SwingConstants.CENTER);
		turnLabel.setBounds(10, 280, 100, 15);
		add(turnLabel);;
	}
}
