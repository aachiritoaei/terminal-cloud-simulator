package commands;

import repositories.*;

public class CommandEditfile extends AbstractCommand{
	/* Verify read permission */
	public boolean verifyReadable(RepositoryClass rep){
		if(userManager.getCurrentUser().getUsername().equals("guest"))
			return false;
		if(userManager.getCurrentUser().getUsername().equals("root"))
			return true;
		if(rep.getOwner().getUsername().equals("-"))
			return true;
		if(userManager.getCurrentUser().getUsername().equals(rep.getOwner().getUsername()) == true && rep.isReadable() == true)
			return true;
		return false;
	}
	/* Verify write permission */
	public boolean verifyWriteable(RepositoryClass rep){
		if(userManager.getCurrentUser().getUsername().equals("guest"))
			return false;
		if(userManager.getCurrentUser().getUsername().equals("root"))
			return true;
		if(rep.getOwner().getUsername().equals("-"))
			return true;
		if(userManager.getCurrentUser().getUsername().equals(rep.getOwner().getUsername()) && rep.isWriteable() == true)
			return true;
		return false;
	}
	public void setParametersAndExecute(String arguments){
		if(arguments.equals("")){ //missing repository name
			myGUI.addText("Missing file/directory name.");
			return;
		}
		String[] splitted = arguments.split(" ");
		String name = splitted[0];
		RepositoryClass rep = currentDirectory.getChildFromName(name);
		if(rep == null){
			myGUI.addText("No such file.");
			return;
		}
		execute(rep);
	}
	/* Execute */
	public void execute(RepositoryClass rep) {
		if(verifyReadable(rep) == false || verifyWriteable(rep) == false){
			myGUI.addText("Permission denied.");
			return;
		}
		rep.accept(this);
	}
	/* Directory */
	public void execute(Directory dir) {
		myGUI.addText("A directory cannot be edited.");
	}
	/* File */
	public void execute(File file) {
		myGUI.addEditfile(file);
	}
}
