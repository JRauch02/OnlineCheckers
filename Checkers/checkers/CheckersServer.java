package checkers;

import java.awt.Color;
import java.io.IOException;

import javax.swing.JLabel;
import javax.swing.JTextArea;

import ocsf.server.AbstractServer;
import ocsf.server.ConnectionToClient;

public class CheckersServer extends AbstractServer {
	private JLabel status;
	private JTextArea serverLog;
	private DatabaseFile databaseFile;
	private Database database;
	private CheckersGame game;

	public void setDatabase(Database data) {
		database = data;
	}
		
	public CheckersServer(JLabel status, JTextArea log) {
		// super class constructor with port number as param
		super(12345);
		this.setTimeout(500);
		this.status = status;
		this.serverLog = log;
		databaseFile = new DatabaseFile();
		game = new CheckersGame(this);
	}

	public void clientConnected(ConnectionToClient client) {
		try {
			client.sendToClient("username:"+client.getId());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		serverLog.append("Client " + client.getId() + " Connected\n" );
	}


	public Integer[] parseRowAndColumn(String message) {
		String[] splitString = message.split(":");
		String[] splitRowCol = splitString[1].split(",");
		
		splitRowCol[0] = splitRowCol[0].replace("(", "");
		splitRowCol[1] = splitRowCol[1].replace(")", "");
		
		Integer[] returnVals = new Integer[2];
		returnVals[0] = Integer.parseInt(splitRowCol[0]);
		returnVals[1] = Integer.parseInt(splitRowCol[1]);
		
		return returnVals;
	}
	public void handleMessageFromClient(Object arg0, ConnectionToClient arg1) {
		
		String message = arg0.toString();
		System.out.println(message);
		
		if (game.isStarted() && message.contains("DISCONNECT")) {
			game.handleDisconnect(arg1);
		}
		
		
		if (game.isStarted() && message.contains("SELECT")) {
			Integer[] rowAndCol = parseRowAndColumn(message);
			
			//this function will contain logic to process what to do with a click based on previous clicks.
				//don't look too much into it
			game.processClick(rowAndCol[0], rowAndCol[1], arg1);
			
		}
		else if (message.contains("SELECT")){
			//if input is made when the game isn't started, send error message
			if (!game.isStarted()) {
				try {
					arg1.sendToClient("ERROR: GAME NOT STARTED");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

		//if a client wants to host
		if (message.contains("HOST")) {
			game.setPlayer(arg1);
			try {
				arg1.sendToClient("HOSTED");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("Player One Found");
		}
		//if a client wants to join
		if (message.contains("JOIN")) {
			if (game.isHosted()) {
				//game will be set into a ready state when this is called
				game.setPlayer(arg1);
				try {
					arg1.sendToClient("JOINED");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				System.out.println("Player Two Found");
				System.out.println("GAME READY AND STARTED");
			}
		}
		if (message.contains("NEW")) {
			String[] splitString = message.split(":");
			String[] splitUserNameAndPassword = splitString[1].split(",");
			LoginData newLogin = new LoginData(splitUserNameAndPassword[0],splitUserNameAndPassword[1]);

			if (database.checkValidUserName(newLogin.getUserName())) {
				database.addNewUser(newLogin);
				try {
					arg1.sendToClient("NEW:TRUE");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			else {
				try {
					arg1.sendToClient("NEW:FALSE");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			//User userToAdd = new User(1,newLogin.getUserName(), newLogin.getPassword());
			//boolean added = databaseFile.addNewUser(userToAdd);

			/*
			if (added) {
				try {
					arg1.sendToClient("NEW:TRUE");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			else {
				try {
					arg1.sendToClient("NEW:FALSE");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			 */
		}
		if (message.contains("LOGIN")) {
			String[] splitString = message.split(":");
			String[] splitUserNameAndPassword = splitString[1].split(",");
			LoginData userToVerify = new LoginData(splitUserNameAndPassword[0],splitUserNameAndPassword[1]);

			if (database.checkValidLoginData(userToVerify)) {
				try {
					arg1.sendToClient("LOGIN:TRUE");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			else {
				try {
					arg1.sendToClient("LOGIN:FALSE");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
		// Check for WINNER message from client
		if(message.contains("WINNER")) {
			
			// Grab the username of the winner
			String[] splitString = message.split(":");
			
			// Increment the winners win count
			database.addWin(splitString[1]);
			
			try {
				// Send user scores to client
				arg1.sendToClient("LEADERBOARD:" + database.query("SELECT username, no_wins FROM user ORDER BY no_wins DESC"));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		// Check for LOOSER message from client
		if(message.contains("LOOSER")) {
			try {
				// Send user scores to client
				arg1.sendToClient("LEADERBOARD:" + database.query("SELECT username, no_wins FROM user ORDER BY no_wins DESC"));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public void listeningException(Throwable exception) {

		this.status.setText("Exception Occurred when Listening");
		this.status.setForeground(Color.red);
		this.serverLog.append(exception.getMessage() + "\n");
	}

	public void serverStarted() {
		this.status.setText("Listening");
		this.status.setForeground(Color.green);
		this.serverLog.append("Server Started\n");
	}

	public void serverStopped() {
		this.status.setText("Stopped");
		this.status.setForeground(Color.red);
		this.serverLog.append("Server Stopped Accepting New Clients - Press Listen to Start Accepting New Clients\n");
	}

	public void serverClosed() {
		this.status.setText("Closed");
		this.status.setForeground(Color.red);
		this.serverLog.append("Sever and all current clients are closed - Press Listen to Restart");
	}
	
	public void sendMessageToClient(String message, ConnectionToClient receiever) {
		try {
			receiever.sendToClient(message);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	

}
