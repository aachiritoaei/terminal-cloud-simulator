package commands;

import java.util.*;

import repositories.*;
import users.*;

/* Createa a directory */
public class CommandMkdir extends WriteCommand {
	private String name;
	private int size;
	private String type;
	private User owner;
	private boolean readable;
	private boolean writeable;
	
	/* Set parameters and execute */
	public void setParametersAndExecute(String arguments){
		/* Verify permission */
		if(verifyWriteable(currentDirectory) == false){
			myGUI.addText("Permission denied.");
			return;
		}
		/* Initialize new directory descriptors */
		size = 0;
		type = "text";
		owner = currentDirectory.getOwner();
		readable = currentDirectory.isReadable();
		writeable = currentDirectory.isWriteable();
		/* Parsing arguments */
		StringTokenizer str = new StringTokenizer(arguments, " ");
		/* Get name */
		if(str.hasMoreTokens())
			name = str.nextToken();
		else{
			myGUI.addText("Missing directory name.");
			return;
		}
		/* Get permissions */
		if(str.hasMoreTokens()){
			String aux = str.nextToken();
			if(aux.startsWith("-")){// It's permission
				if(aux.contains("r"))
					readable = true;
				else 
					readable = false;
				if(aux.contains("w"))
					writeable = true;
				else
					writeable = false;
				owner = userManager.getCurrentUser();
			}
		}
		/* Add directory if it doesn't exit */
		Directory newDirectory = new Directory(name, size, type, new Date(), owner, readable, writeable, currentDirectory);
		if(currentDirectory.getChildFromName(name) == null){
			currentDirectory.addChild(newDirectory);
		} else {
			myGUI.addText("Name is already in use.");
			return;
		}
		/* Add size to father directories */
		RepositoryClass rep = currentDirectory;
		while(rep.getName() != "/"){
			rep.setSize(rep.getSize() + size);
			rep = rep.getFather();
		}
		rep.setSize(rep.getSize() + size);
		myGUI.addText("Directory created.");
	}
	/* Execute */
	public void execute(RepositoryClass rep) {
		rep.accept(this);
	}
	/* For directory */
	public void execute(Directory dir) {}
	/* For file */
	public void execute(File file) {}
}
