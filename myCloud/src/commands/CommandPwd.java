package commands;

import java.util.Date;

import exceptions.*;
import repositories.*;

/* Print working directory */
public class CommandPwd extends ReadCommand {
	/* Execute */
	public void execute(RepositoryClass rep){
		if(verifyReadable(rep) == false){
			myGUI.addText("Permission denied.");
			return;
		}
		rep.accept(this);
	}
	/* For directory */
	public void execute(Directory dir) {
		try {
			String path = new String(dir.getName());
			if(dir.getName().equals("/") == true){ //currentDirectory is rootDirectory
				myGUI.addText(path);
			} else {
				while(dir.getFather().getName().equals("/") == false){
					path = dir.getFather().getName() + "/" + path;
					dir = (Directory)dir.getFather();
				}
				path = "/" + path;
				if(path.length() > 255){ // path is too long
					myGUI.addText("Path too long.");
					throw new MyPathTooLongException(currentDirectory, currentCommand, userManager.getCurrentUser(), new Date());
				}
				else
					myGUI.addText(path);
			}
		} catch(MyPathTooLongException e) {
			systemLogger.logException(e);
		}
	}
	/* For file */
	public void execute(File file) {
		/* No pwd for file */
	}
}
