package checkers;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class DatabaseFile {
	private FileInputStream fileInput;
	private FileOutputStream fileOutput;
	//private BufferedReader fileInput;
	//private BufferedWriter fileOutput;
	
	public boolean addNewUser(User newUser) {
		File file = new File("./lab5out/users.txt");
		//need to check if username is already in use - if it isn't add it and send confirmation. return true if added, false if otherwise.
		if (!checkUserNameDuplicate(newUser)) {
			try {
				FileWriter fr = new FileWriter(file, true);
				BufferedWriter tempWriter = new BufferedWriter(fr);
				//tempWriter.append(newUser.toString());
				tempWriter.write("\n"+newUser.toString());
				
				//fr.close();
				tempWriter.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return true;
		}
		return false;
	}
	
	public boolean checkUserNameDuplicate(User newUser) {
		File file = new File("./lab5out/users.txt");
		try {
			Scanner scanner = new Scanner(file);
			boolean found = false;
			int lineI =0;
			while(scanner.hasNextLine()) {
				String line = scanner.nextLine();
				lineI++;
				if (line.contains(newUser.getUserName())) {
					found = true;
				}
			}
			scanner.close();
			return found;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	
	public boolean verifyLoginInformation(LoginData loginData) {
		File file = new File("./lab5out/users.txt");
		try {
			Scanner scanner = new Scanner(file);
			boolean found = false;
			int lineI =0;
			while(scanner.hasNextLine()) {
				String line = scanner.nextLine();
				lineI++;
				if (line.contains(loginData.toString())) {
					found = true;
				}
			}
			scanner.close();
			return found;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
		/*
		try {
			String line;
			//fileInput = new FileInputStream("./lab5out/users.txt");
			BufferedReader tempReader = new BufferedReader(new InputStreamReader(new FileInputStream("./lab5out/users.txt")));
			boolean found = false;
			while(tempReader.readLine() != null) {
				line = tempReader.readLine();
				if (line.contains(loginData.toString())) {
					found = true;
				}
				System.out.println("line");
			}
			System.out.println("about to return");
			tempReader.close();
			return found;
			
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		*/
	}
	
	public DatabaseFile() {
		/*
		try {
			fileInput = new FileInputStream("./lab5out/users.txt");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		*/
	}
	
}
