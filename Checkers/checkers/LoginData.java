package checkers;

public class LoginData {
	private String userName;
	private String password;
	
	
	
	public LoginData(String userName, String password) {
		this.setUserName(userName);
		this.setPassword(password);
	}



	public String getUserName() {
		return userName;
	}

	public String toString() {
		return userName +","+ password;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}



	public String getPassword() {
		return password;
	}



	public void setPassword(String password) {
		this.password = password;
	}
}
