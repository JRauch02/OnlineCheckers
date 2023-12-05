package checkers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;

import ocsf.client.AbstractClient;

public class CheckersClient extends AbstractClient{


	private ImageIcon greenPiece = new ImageIcon(GamePanel.class.getResource("greenPiece.png"));
	private ImageIcon tanPiece = new ImageIcon(GamePanel.class.getResource("tanPiece.png"));
	private ImageIcon greenPieceKing = new ImageIcon(GamePanel.class.getResource("greenPieceKing.png"));
	private ImageIcon tanPieceKing = new ImageIcon(GamePanel.class.getResource("tanPieceKing.png"));
	private LoginPanel loginView;
	private CreateAccountPanel createAccountView;
	private MenuPanel menuView;
	private ClientGUI parent;
	
	private String username;

	public void setLoginView(LoginPanel loginView) {
		this.loginView = loginView;
	}
	public void setCreateAccountView(CreateAccountPanel createAccountView) {
		this.createAccountView = createAccountView;
	}
	
	public void setMenuView(MenuPanel menuView) {
		this.menuView = menuView;
	}
	public CheckersClient(ClientGUI parent) {
		super("localhost", 8300);
		this.parent = parent;
	}
	
	
	public void handleMessageFromServer(Object arg0) {
		System.out.println("Server Message Sent to Client: " + arg0);
		String test = arg0.toString();
		//System.out.println(test.contains("username"));
		if(test.contains("HOSTED")) {
			parent.changeToGameView();
		}
		if(test.contains("JOINED")) {
			parent.changeToGameView();
		}
		if(test.contains("username")) {
			System.out.println("Found Username");
			String[] temp = arg0.toString().split(":");
			String id = temp[1];
			id = id.trim();
			System.out.println("Client ID ="  + id);
			
		}
		
		if(test.equals("LOGIN:TRUE")) {
			//parent.changeToContactView();
			parent.changeToMenuView();
		}
		if (test.equals("LOGIN:FALSE")) {
			loginView.loginError();
		}
		
		if (test.equals("NEW:TRUE")) {
			//parent.changeToContactView();
			//createAccountView.loggedIntoNewAccount();
			//this is where menu will go
			parent.changeToMenuView();
		}
		if (test.equals("NEW:FALSE")) {
			createAccountView.userNameError("Username Already In Use");
		}
		if (test.equals("NOT POSSIBLE")) {
			parent.getGamePanel().getBoard().unHighlightPotentialMoves();
		}
		else if (test.contains("POSSIBLE")) {
			parent.getGamePanel().getBoard().unHighlightPotentialMoves();
			
			String[] temp = test.split(":");
			String[] coords = temp[1].split(";");
			List<Integer> cellCoords = new ArrayList<Integer>();
			
			for (int i = 0; i < coords.length; i++) {
				String[] splitCoords = coords[i].split(",");
				
				splitCoords[0] = splitCoords[0].replace("(", "");
				splitCoords[1] = splitCoords[1].replace(")", "");
				cellCoords.add(Integer.parseInt(splitCoords[0]));
				cellCoords.add(Integer.parseInt(splitCoords[1]));
			}
			
			
			parent.getGamePanel().getBoard().highLightPotentialMoves(cellCoords);
			/*
			if (test.contains(";")) {
				String[] temp = test.split(":",2);
				String[] cells = temp[1].split(";");
				
				for (int i = 0; i < cells.length; i++) {
					parent.getGamePanel().getBoard().highlightPotentialMoves(cells[i]);
				}
			}
			else {
				String[] temp = test.split(":");
				parent.getGamePanel().getBoard().highlightPotentialMoves(temp[1]);
			}
			*/
		}
		
		if (test.contains("MOVE")) {
			System.out.println("Move Detected");
			
			String[] temp = test.split(":");
			String[] coords = temp[1].split(";");
			
			List<Integer> cellCoords = new ArrayList<Integer>();
			
			for (int i = 0; i < coords.length; i++) {
				String[] splitCoords = coords[i].split(",");
				
				splitCoords[0] = splitCoords[0].replace("(", "");
				splitCoords[1] = splitCoords[1].replace(")", "");
			
				
				cellCoords.add(Integer.parseInt(splitCoords[0]));
				cellCoords.add(Integer.parseInt(splitCoords[1]));
			}
			
			parent.getGamePanel().getBoard().move(cellCoords.get(0), cellCoords.get(1), cellCoords.get(2), cellCoords.get(3));
		}
		if (test.contains("CAPTURE")) {
			String[] temp = test.split(":");
			
			String[] splitCoords = temp[1].split(",");
			
			splitCoords[0] = splitCoords[0].replace("(", "");
			splitCoords[1] = splitCoords[1].replace(")", "");
			
			
			
			parent.getGamePanel().getBoard().capture(Integer.parseInt(splitCoords[0]),Integer.parseInt(splitCoords[1]));
			
			
			//probably want to pull as little of the game logic into checkers client as possible - we can let GameBoard handle most of the logic regarding actual cells
			/*
			BoardCell cell = parent.getGamePanel().getBoard().getCell(Integer.parseInt(splitCoords[0]), Integer.parseInt(splitCoords[1]));
			boolean isPlayerOne = parent.getGamePanel().getBoard().getPlayerNumber();
			
			if (isPlayerOne == true && (cell.getIcon() == greenPiece || cell.getIcon() == greenPieceKing)) {
				parent.getGamePanel().getSidePanel().pieceTaken();
			}
			else if (isPlayerOne == false && (cell.getIcon() == tanPiece || cell.getIcon() == tanPieceKing)) {
				parent.getGamePanel().getSidePanel().pieceTaken();
			}
			parent.getGamePanel().getBoard().removePiece(cell);
			*/
		}
		if (test.contains("KING")) {
			String[] temp = test.split(":");
			
			String[] splitCoords = temp[1].split(",");
			
			splitCoords[0] = splitCoords[0].replace("(", "");
			splitCoords[1] = splitCoords[1].replace(")", "");
			
			parent.getGamePanel().getBoard().king(Integer.parseInt(splitCoords[0]), Integer.parseInt(splitCoords[1]));
			/*
			
			BoardCell cell = parent.getGamePanel().getBoard().getCell(Integer.parseInt(splitCoords[0]), Integer.parseInt(splitCoords[1]));
			boolean isPlayerOne = parent.getGamePanel().getBoard().getPlayerNumber();
			
			cell.setKing(true);
			if (isPlayerOne == true && cell.getIcon() == tanPiece) {
				cell.setIcon(tanPieceKing);
			}
			else if (isPlayerOne == false && cell.getIcon() == greenPiece) {
				cell.setIcon(greenPieceKing);
			}
			*/
		}
		if (test.equals("YOUR TURN")) {
			parent.getGameSidePanel().setTurnLabel("Your Turn");
		}
		if (test.equals("END TURN")) {
			parent.getGameSidePanel().setTurnLabel("End Turn");
		}
		if (test.equals("WIN") || test.equals("LOSS")) {
			if (test.equals("WIN")) {
				
				// Set the winlose lable to you win
				parent.getLeaderboardPanel().setWinLoseLabel("*** YOU WIN ***");
				
				// Send the username of the winner to the server
				try {
					sendToServer("WINNER:" + username);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			else {
				
				try {
					Thread.sleep(250);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				// Set the winlose label to you lose
				parent.getLeaderboardPanel().setWinLoseLabel("*** YOU LOSE ***");
				
				// Ping the server to get the leaderboard
				try {
					sendToServer("LOOSER:" + username);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
		// Check for LEADERBOARD message from server
		if(test.contains("LEADERBOARD")) {
			
			// Grab the leaderboard string
			String[] leaderboard = test.split(":");
			String[] info = leaderboard[1].split("[,]");
			
			// pass the string to the leaderboard panel

			parent.getLeaderboardPanel().setLeaderboard(info);
			// Change to the leaderboard view
			parent.changeToLeaderboardView();
		}
		
	}
	
	public int[] parseCoordinates(String cell) {
		String[] coordinates = cell.split(",",2);
		coordinates[0] = coordinates[0].replaceAll("[^0-9]", "");
		coordinates[1] = coordinates[1].replaceAll("[^0-9]", "");
		
		int[] newCoordinates = new int[]{0};
		newCoordinates[0] = Integer.parseInt(coordinates[0]);
		newCoordinates[1] = Integer.parseInt(coordinates[1]);
		
		return newCoordinates;
	}
	
	public void connectionException(Throwable exception) {
		System.out.println("Connected Exception Occurred:");
		System.out.println(exception.getMessage());
		exception.printStackTrace();
	}
	
	public void connectionEstablished() {
		
	}
	
	public void sendLoginData(LoginData loginData) {
		try {
			Object testing = loginData.toString();
			sendToServer("LOGIN:" + testing);
			
			// Grab the username
			this.username = testing.toString().split(",")[0];
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void sendHostGame() {
		try {
			sendToServer("HOST");
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	public void sendJoinGame() {
		try {
			sendToServer("JOIN");
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	public void sendNewLogin(LoginData loginData) {
		try {
			Object testing = loginData.toString();
			sendToServer("NEW:"+testing);
			
			this.username = testing.toString().split(",")[0];
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	public void sendSelectedCell(BoardCell cell) {
		try {
			sendToServer("SELECT:" + cell.toString());
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	public void sendLeaveGame() {
		try {
			sendToServer("DISCONNECT");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
