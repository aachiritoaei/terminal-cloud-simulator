package commands;

import java.util.StringTokenizer;

import repositories.*;
import users.*;

/* Get info about a user */
public class CommandUserinfo extends AbstractCommand { 
	private String username;
	/* Set parameters and execute */
	public void setParametersAndExecute(String arguments){
		if(arguments.contains("-POO")){ // -POO parameter
			String splitted[] = arguments.split(" ");
			if(splitted.length == 1){// - only the -POO parameter was given
				myGUI.addUserinfoPOO(userManager.getCurrentUser());
				return;
			}
			if(arguments.startsWith("-POO"))//parameter first
				username = splitted[1];
			else
				username = splitted[0]; //parameter second
			for(User elem : allUsers){
				if(elem.getUsername().equals(username)){
					myGUI.addUserinfoPOO(elem);
					return;
				}
			}
			myGUI.addText("Username not found.");
		} else { // no POO parameter
			StringTokenizer str = new StringTokenizer(arguments, " ");
			if(str.hasMoreTokens()){
				username = str.nextToken();
				for(User elem : allUsers){
					if(elem.getUsername().equals(username)){
						myGUI.addText("Username : " + elem.getUsername() + 
								"\nFirstname : " + elem.getFirstName() + 
								"\nLastname : " + elem.getLastName() + 
								"\nCreated : " + elem.getCreationDate() + 
								"\nLast login : " + elem.getLastLogin());
						return;
					}
				}
				myGUI.addText("Username not found.");
			} else {
				User elem = userManager.getCurrentUser();
				myGUI.addText("Username : " + elem.getUsername() + 
						"\nFirstname : " + elem.getFirstName() + 
						"\nLastname : " + elem.getLastName() + 
						"\nCreated : " + elem.getCreationDate() + 
						"\nLast login : " + elem.getLastLogin());
			}
		}
	}
	/* Execute */
	public void execute(RepositoryClass rep) {
		rep.accept(this);
	}
	public void execute(Directory dir) {}
	public void execute(File file) {}
}
