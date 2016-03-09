package commands;

import java.util.*;

import users.*;
	
import javax.swing.SpringLayout.Constraints;

import repositories.*;
/* Create a file */
public class CommandTouch extends WriteCommand {
	private String name;
	private int size;
	private String type;
	private String data;
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
		/* Initialize new file descriptors */
		size = 0;
		type = "text";
		data = "Congrats, you've created a file! To edit this text, use command 'edit <filename>'!\nAlexandru-Adrian Achiritoaei";
		owner = currentDirectory.getOwner();
		readable = currentDirectory.isReadable();
		writeable = currentDirectory.isWriteable();
		/* Parsing arguments */
		StringTokenizer str = new StringTokenizer(arguments, " ");
		/* Get name */
		if(str.hasMoreTokens())
			name = str.nextToken();
		else{
			myGUI.addText("Missing filename.");
			return;
		}
		/* Get permissions or size */
		if(str.hasMoreTokens()){
			String aux = str.nextToken();
			if(aux.startsWith("-")){// It's a permission
				if(aux.contains("r"))
					readable = true;
				else 
					readable = false;
				if(aux.contains("w"))
					writeable = true;
				else
					writeable = false;
				owner = userManager.getCurrentUser();
				
			} else {
				try {
					size = Integer.parseInt(aux);
				} catch(NumberFormatException e) {//if it's not integer
					myGUI.addText("Please add a number as file size.");
					return;
				}
			}
		}
		if(str.hasMoreTokens()){
			String aux = str.nextToken();
			if(aux.startsWith("-")){// It's a permission
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
		/* Add file if it doesn't exit */
		File newFile = new File(name, size, type, data, new Date(), owner, readable, writeable, currentDirectory);
		if(currentDirectory.getChildFromName(name) == null){
			currentDirectory.addChild(newFile);
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
		myGUI.addText("File created.");
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
