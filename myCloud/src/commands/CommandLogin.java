package commands;

import java.util.*;

import repositories.*;
import users.*;

/* Log user into system */
public class CommandLogin extends AbstractCommand {
	private String username;
	private String password;
	/* Set parameters and execute */
	public void setParametersAndExecute(String arguments){
		username = "";
		password = "";
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
		/* Get user */
		for(User elem : allUsers){
			if(elem.getUsername().equals(username)){//user is found
				if(elem.getPassword().equals(password)){//password is correct
					elem.setLastLogin(new Date());
					userManager.setCurrentUser(elem);
					currentDirectory = rootDirectory;
					systemLogger.logLogin(elem);
					myGUI.currentUser.setText(elem.getUsername());
					myGUI.addText("Login succesful.");
					return;
				} else { //password is incorrect
					myGUI.addText("Wrong or no password specified.");
					return;
				}
			}
		}
		myGUI.addText("User not found.");
	}
	/* Execute */
	public void execute(RepositoryClass rep) {
		rep.accept(this);
	}
	public void execute(Directory dir) {}
	public void execute(File file) {}
}
