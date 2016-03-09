package commands;

import repositories.*;

/* Log user out the the system */
public class CommandLogout extends AbstractCommand {
	/* Set parameters and execute */
	public void setParametersAndExecute(String arguments){
		systemLogger.logLogout(userManager.getCurrentUser());
		userManager.setCurrentUser(guest);
		currentDirectory = rootDirectory;
		myGUI.currentUser.setText("guest");
		myGUI.addText("Logout succesful.");
	}
	/* Execute */
	public void execute(RepositoryClass rep) {
		rep.accept(this);
	}
	public void execute(Directory dir) {}
	public void execute(File file) {}
}
