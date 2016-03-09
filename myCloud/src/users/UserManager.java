package users;

import java.util.Date;

/* UserManager - Singleton Pattern */
public class UserManager {
	private static final UserManager INSTANCE = new UserManager();
	
	private UserManager(){
		this.currentUser = new User("guest", "", "", "", new Date(), new Date());
	}
	public static final UserManager getInstance(){
		return INSTANCE;
	}
	/* User who is currently logged in */
	private User currentUser;
	/* Get and set this user */
	public User getCurrentUser(){
		return this.currentUser;
	}
	public void setCurrentUser(User currentUser){
		this.currentUser = currentUser;
	}
}
