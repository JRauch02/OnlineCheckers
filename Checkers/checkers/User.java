package checkers;

public class User {
	private int id;
	private String userName;
	private String password;
	
	public void setId(int id) {
		this.id=id;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getUserName() {
		return userName;
	}
	
	public String toString() {
		return(id+","+userName+","+password);
	}
	
	public User(int id, String userName, String password) {
		this.id=id;
		this.userName = userName;
		this.password = password;
	}
	public User() {
		
	}
}
