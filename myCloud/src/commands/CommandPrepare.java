package commands;

import java.util.Date;
import java.util.Vector;

import users.*;
import repositories.*;
import exceptions.*;
import graphics.*;

public class CommandPrepare extends AbstractCommand {
	/* Initializing the execution environment */
	public void prepareUsers(){
		userManager = UserManager.getInstance();
		allUsers = new Vector<User>();
		guest = new User("guest", "", "", "", new Date(), new Date());
		root = new User("root", "rootpass", "", "", new Date(), new Date());
		defaultUser = new User("-", "", "", "", new Date(), new Date());
		allUsers.add(guest);
		allUsers.add(root);
	}
	public void prepareFileHierarchy(){
		/* Initialize file hierarchy */
		rootDirectory = new Directory("/", 24000, "text", new Date(), defaultUser , true, true, null);
		rootDirectory.setFather(rootDirectory);
		
		/* Some directories and files*/
		RepositoryClass homeForAll = new Directory("homeForAll", 24000, "text", new Date(), defaultUser, true, true, rootDirectory);
		RepositoryClass fileForAll = new File("fileForAll", 8000, "text", "Acest fisier se numeste fileForAll.", new Date(), defaultUser, true, true, homeForAll);
		RepositoryClass dirForAll = new Directory("dirForAll", 16000, "text", new Date(), defaultUser, true, true, homeForAll);
		RepositoryClass myDir = new Directory("myDir", 8000, "text", new Date(), defaultUser, true, true, dirForAll);
		RepositoryClass myFile = new File("myFile", 8000, "text", "Acest fisier se numeste myFile.", new Date(), defaultUser, true, true, dirForAll);
		RepositoryClass simpleFile = new File("simpleFile", 8000, "text", "Acesta este un fisier simplu", new Date(), defaultUser, true, true, myDir);
		
		myDir.addChild(simpleFile);
		dirForAll.addChild(myDir);
		dirForAll.addChild(myFile);
		homeForAll.addChild(dirForAll);
		homeForAll.addChild(fileForAll);
		rootDirectory.addChild(homeForAll);
	}
	public void prepareLogger(){
		systemLogger = new Logger();
	}
	public void prepareGUI(){
		myGUI = new GUI();
		myGUI.addIntro();
	}
	public void execute(RepositoryClass rep) {}
	public void execute(Directory dir) {}
	public void execute(File file) {}
}
