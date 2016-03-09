package commands;

import java.util.Vector;

import exceptions.Logger;
import graphics.GUI;
import repositories.*;
import users.User;
import users.UserManager;

/* Abstract class that each command should extend */
public abstract class AbstractCommand implements Command {
	/* File system record */
	public static RepositoryClass commandRepository;
	public static RepositoryClass currentDirectory;
	public static RepositoryClass rootDirectory;
	/* Current command record */
	public static String currentCommand;
	/* User record */
	protected static UserManager userManager;
	protected static Vector<User> allUsers;
	protected static User guest;
	protected static User root;
	protected static User defaultUser;
	/* Logger */
	public static Logger systemLogger;
	/* Graphical User Interface */
	public static GUI myGUI;
		/* Execute */
	public abstract void execute(RepositoryClass rep);
	public abstract void execute(Directory dir);
	public abstract void execute(File file);
	/* CD */
	public void setPath(String path){}
	public void setComponentsFromPath(){}
	/* Other commands */
	public void setParametersAndExecute(String arguments){}
}
