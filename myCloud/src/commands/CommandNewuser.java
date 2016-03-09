package commands;

import java.util.*;

import repositories.*;
import users.*;

/* Create a new user */
public class CommandNewuser extends AbstractCommand {
	private String username;
	private String password;
	private String firstName;
	private String lastName;
	/* Set parameters and execute */
	public void setParametersAndExecute(String arguments){
		/* Verify permission for creating a new user */
		if(userManager.getCurrentUser().getUsername().equals("root") == false){
			myGUI.addText("Only the root user is allowed to create new users.");
			return;
		}
		username = "";
		password = "";
		firstName = "";
		lastName = "";
		StringTokenizer str = new StringTokenizer(arguments, " ");
		/* Get username */
		if(str.hasMoreTokens()){
			username = str.nextToken();
		} else {
			myGUI.addText("Missing username.");
			return;
		}
		/* Get password */
		if(str.hasMoreTokens()){
			password = str.nextToken();
		}
		/* Get firstName */
		if(str.hasMoreTokens()){
			firstName = str.nextToken();
		}
		/* Get LastName */
		if(str.hasMoreTokens()){
			lastName = str.nextToken();
		}
		/* Create user and add it to system */
		User newUser = new User(username, password, firstName, lastName, new Date(), new Date());
		for(User elem : allUsers)
			if(elem.getUsername().equals(username)){
				myGUI.addText("Existing user, pick another username.");
				return;
		}
		allUsers.add(newUser);
		myGUI.addText("User created.");
	}
	/* Execute */
	public void execute(RepositoryClass rep) {
		rep.accept(this);
	}
	public void execute(Directory dir) {}
	public void execute(File file) {}
}
