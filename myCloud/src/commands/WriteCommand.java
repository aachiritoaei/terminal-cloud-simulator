package commands;

import repositories.*;

/* Each writing command should implement this class */

public abstract class WriteCommand extends AbstractCommand {
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
	public abstract void execute(RepositoryClass rep);
	public abstract void execute(Directory dir);
	public abstract void execute(File file);
}
