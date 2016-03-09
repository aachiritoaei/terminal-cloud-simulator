package commands;

import repositories.*;
import users.*;

/* Show system users */
public class CommandShowusers extends AbstractCommand { 
	private String result;
	/* Set parameters and execute */
	public void setParametersAndExecute(String arguments){
		result = "";
		for(User elem : allUsers){
			result += elem.getUsername() + " ";
		}
		myGUI.addText(result);
	}
	/* Execute */
	public void execute(RepositoryClass rep) {
		rep.accept(this);
	}
	public void execute(Directory dir) {}
	public void execute(File file) {}
}
