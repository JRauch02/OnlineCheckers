package checkers;

import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class LeaderboardPanel extends JPanel {
	private ClientGUI parent;
	private CheckersClient client;
	private LeaderboardController controller;
	private JLabel winLoseLabel;
	private JLabel titleLabel;
	//private JTextArea leaderboard;
	private JButton logout;
	private JButton exit;
	private JTable leaderboard;
	private DefaultTableModel model;
	
	// setter for the leaderboard table
	public void setLeaderboard(String[] leaderboardInfo){
		ArrayList<String> leaderboardText = new ArrayList<String>();
		
		// Create String that will be displayed to the leaderboard
		for (int i = 0; i < leaderboardInfo.length; i++) {
			//Even numbers should always be usernames
			if((i%2) == 0) {
				// Remove the bracket from the first name
				if(leaderboardInfo[i].contains("[")) {
					leaderboardText.add(leaderboardInfo[i].replace("[",""));
				}
				// Trim off any whitespace
				if(leaderboardInfo[i].startsWith(" ")){
					leaderboardText.add(leaderboardInfo[i].trim());
				}
			}else {
				// Remove the bracket from the last number
				if(leaderboardInfo[i].contains("]")) {
					leaderboardText.add(leaderboardInfo[i].replace("]",""));
				}else {
					leaderboardText.add(leaderboardInfo[i]);
				}
			}
		}
		
		// Create the columns for the table
		model.addColumn("Players:");
		model.addColumn("Wins:");
		
		// Iterate throught the leaderboardText to grab store each result within a row
		for(int i = 0; i < leaderboardText.size();i++) {
			
			// temp object to store the name and no_wins
			String temp[] = {leaderboardText.get(i), leaderboardText.get(i+1)};
			
			// insert temp at the rowNum
			model.addRow(temp);
			
			// Increment again to skip to the next row
			i++;
		}
		
		// Set changes to the table
		leaderboard.setModel(model);
	}
	// Potentially a getter for the leaderboard (don't think we need)
//	public void getLeaderboard() {
//		
//	}
	
	public void setWinLoseLabel (String result) {
		winLoseLabel.setText(result);
	}
	public String getWinLoseLabel() {
		return winLoseLabel.getText();
	}
	
	public JButton getLogoutButton() {
		return logout;
	}
	public JButton getExitButton() {
		return exit;
	}
	
	
	public LeaderboardPanel(ClientGUI parent) {
		setLayout(null);
		
		this.setBackground(new Color(200,170,130));
	
		winLoseLabel = new JLabel("");
		winLoseLabel.setHorizontalAlignment(SwingConstants.CENTER);
		winLoseLabel.setFont(new Font("Tahoma", Font.PLAIN, 12));
		winLoseLabel.setBounds(170, 45, 250, 15);
		add(winLoseLabel);
		
		titleLabel = new JLabel("Leaderboard - Top 5");
		titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
		titleLabel.setFont(new Font("Tahoma", Font.PLAIN, 12));
		titleLabel.setBounds(170, 95, 250, 15);
		add(titleLabel);
				
		logout = new JButton("Logout");
		logout.setFont(new Font("Tahoma", Font.PLAIN, 12));
		logout.setBounds(170, 295, 100, 25);
		add(logout);
		
		exit = new JButton("Exit");
		exit.setFont(new Font("Tahoma", Font.PLAIN, 12));
		exit.setBounds(320, 295, 100, 25);
		add(exit);
		
		this.parent = parent;
		this.client = parent.getChatClient();
		this.controller = new LeaderboardController(this, parent);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(145, 130, 300, 120);
		add(scrollPane);
		
		model = new DefaultTableModel();
		leaderboard = new JTable();
		leaderboard.setEnabled(false);
		leaderboard.getTableHeader().setReorderingAllowed(false);
		leaderboard.getTableHeader().setResizingAllowed(false);
		leaderboard.setFont(new Font("Tahoma", Font.PLAIN, 12));

		scrollPane.setViewportView(leaderboard);
	}
}
