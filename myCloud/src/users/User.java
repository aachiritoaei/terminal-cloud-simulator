package users;

import java.util.Date;

public class User {
	/* User descriptors */
	private String username;
	private String password;
	private String firstName;
	private String lastName;
	private Date creationDate;
	private Date lastLogin;
	/* Getters & Setters */
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public Date getCreationDate() {
		return creationDate;
	}
	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}
	public Date getLastLogin() {
		return lastLogin;
	}
	public void setLastLogin(Date lastLogin) {
		this.lastLogin = lastLogin;
	}
	/* User constructor */
	public User(String username, String password, String firstName, String lastName, Date creationDate, Date lastLogin){
		this.username = username;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
		this.creationDate = creationDate;
		this.lastLogin = lastLogin;
	}
}
