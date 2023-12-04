package checkers;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Properties;

import ocsf.server.ConnectionToClient;

public class Database {
	private Connection conn;
	private String username;
	private String password;
	private String url;

	public Database() {
		//Add your code here
		//Create a properties object
		Properties test = new Properties();
		//Use a FileInputStream as input to the properties object for reading the db.properties file
		try {
			FileInputStream input = new FileInputStream("./checkers/db.properties");
			try {
				test.load(input);

				username = test.getProperty("user");
				password = test.getProperty("password");
				url = test.getProperty("url");
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//Set the conn object
		try {
			conn = DriverManager.getConnection(url, username, password);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println(username +"," +password+"," + url);
			e.printStackTrace();
		}
	}
	
	private ResultSet runQuery(String query) {
		Statement test;
		try {
			test = conn.createStatement();
			ResultSet results = test.executeQuery(query);
			
			return results;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
		
	}
	
	public boolean checkValidLoginData(LoginData loginData) {
		try {
			//ResultSet results = runQuery("select username, aes_decrypt(password, 1) from chat_user where username='" + loginData.getUserName() + "' and aes_decrypt(password, 1) = '" + loginData.getPassword()+"'");
			ResultSet results = runQuery("select * from user where username='" + loginData.getUserName() + "' and aes_decrypt(password, 'key') = '" + loginData.getPassword()+"'"); //UNCOMMENT BEFORE TURNING THIS IN
			
			if (results.next()) {
				return true;
			}
			else if (!results.next()) {
				return false;
			}
		} catch(SQLException e) {
			e.printStackTrace();
			return false;
		}
		return false;
	}
	
	
	
	public boolean checkValidUserName(String username) {
		//try to query for username if there are results.next() then the name already exists, return false
		//if it is an empty query, then we know that it isn't used in the table, return true
		try  {
			//ResultSet results = runQuery("select * from chat_user where username ='" + username + "'");
			ResultSet results = runQuery("select * from user where username = '"+username+"'"); //NEED TO SWAP THE ABOVE LINE WITH THIS BEFORE TURNING IN
			
			//if we have results, the username already exists, so we return that the username is already in use (false)
			if (results.next()) {
				return false;
			}
			//if we don't have results, then the username is not already in use, so we can return that the username is valid (true)
			else if (!results.next()) {
				return true;
			}
			
		} catch (SQLException e) {	
			e.printStackTrace();
			//return false if we get an error
			return false;
		}
		return false;
	}
	
	public void addNewUser(LoginData loginData) {
		try {
			//executeDML("insert into chat_user values('" + loginData.getUserName() + "', aes_encrypt('"+loginData.getPassword()+"', 1))");
			executeDML("insert into user values('" + loginData.getUserName() + "', aes_encrypt('"+loginData.getPassword()+"', 'key'), 0)");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public ArrayList<String> query(String query)
	{
		ArrayList<String> result = new ArrayList<String>();
		String username;
		String no_wins;
		String record;
		
		try {
			//Using the Conn object create a Statement object
	        Statement test = conn.createStatement();  
	        
			//Using the statement object execute using the input query (Return the ResultSet)
	        ResultSet rs = test.executeQuery(query);  
	        
	        //Use a while loop to process the rows - Create a , delimited record from each field

	        if(rs.isBeforeFirst()) {
	        	while(rs.next()) 
	    	    {
	        		//Add each , delimited record to the ArrayList
	        		username = rs.getString("username");
	        		no_wins = rs.getString("no_wins");
	        		record = username + "," + no_wins;
				
	        		result.add(record);
	        	}
	        }else {	      
	        	return null;
	        }
	      } 
	      catch (SQLException e)
	      {
	        // TODO Auto-generated catch block
	        e.printStackTrace();
	      } 
		return result;
	}
	
	public void addWin(String username) {
		
		// Add 1 to the number of wins
		try {
			executeDML("UPDATE `user` SET no_wins = no_wins + 1 WHERE username = \"" + username + "\"");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void executeDML(String dml) throws SQLException
	  {
	    //1. Use the conn object to create a statement object
		  Statement dmlStatement = conn.createStatement();
		  // 2. run dml on the execute method of statement
		  dmlStatement.execute(dml);
	  }
}
