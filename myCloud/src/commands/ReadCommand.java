package commands;

import repositories.*;

/* Each reading command should implement this class */

public abstract class ReadCommand extends AbstractCommand {
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
	public abstract void execute(RepositoryClass rep);
	public abstract void execute(Directory dir);
	public abstract void execute(File file);
}
