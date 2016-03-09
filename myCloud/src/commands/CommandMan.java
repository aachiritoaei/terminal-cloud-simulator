package commands;

import java.util.StringTokenizer;

import repositories.*;

/* Command manual 
 * Detailed information about each command */
public class CommandMan extends AbstractCommand {
	private String parameter;
	private String result;
	/* Set parameters and execute */
	public void setParametersAndExecute(String arguments){
		parameter = "";
		StringTokenizer str = new StringTokenizer(arguments, " ");
		if(str.hasMoreTokens())
			parameter = str.nextToken();
		result = "";
		switch(parameter){
			case "ls":
				result += "	ls - list repository contents\n" + 
						"	     Usage : ls <name> <parameter>\n" +
						"	     Parameters:\n" +
						"	          -a/-l : detailed information about repository\n" +
						"	                  name size owner read write creationDate type\n" +
						"	          -r : list directory recursively\n" +
						"	          -POO : nicely-formatted output";
				break;
			case "cd":
				result += "	cd - change directory\n" + 
						"	     Usage : cd <path>\n" +
						"	     Path may be absolute, relative, or missing, which sends user to '/' directory.";
				break;
			case "pwd":
				result += "	pwd - print working directory\n" +
						"	     Usage : pwd";
				break;
			case "cat":
				result += "	cat - concatenate files and print on the standard output\n" +
						"	     Usage : cat <name>\n" +
						"	     The name given may also contain a path.";
				break;
			case "touch":
				result += "	touch - create file\n" + 
						"	     Usage : touch <name> <size> <permissions>\n" + 
						"	     Permissions:\n" +
						"	          -r- read permission\n" +
						"	          -w- write permission\n" +
						"	          -rw- read/write permission";
				break;
			case "mkdir":
				result += "	mkdir - create directory\n" + 
						"	     Usage : touch <name> <permissions>\n" + 
						"	     Permissions:\n" +
						"	          -r- read permission\n" +
						"	          -w- write permission\n" +
						"	          -rw- read/write permission";
				break;
			case "rm":
				result += "	rm - remove files or directories\n" + 
						"	     Usage : rm <name> <parameter>\n" +
						"	     The name given may also contain a path.\n" +
						"	     For a directory you have to use '-r' parameter.";
				break;
			case "login":
				result += "	login - log into system as a user\n" +
						"	     Usage : login <username> <password>";
				break;
			case "logout":
				result += "	logout - log out of the system\n" +
						"	     Usage : logout";
				break;
			case "userinfo":
				result += "	userinfo - information about a user\n" +
						"	     Usage : userinfo <name> <parameter>\n" +
						"	     If the name is missing, it displays the current user details.\n" +
						"	     For a nicely-formatted output, use -POO parameter.";
				break;
			case "showusers":
				result += "	showusers - show all system users\n" +
						"	     Usage : showusers";
				break;
			case "newuser":
				result += "	newuser - add new user to the system\n" + 
						"	     Usage : newuser <username> <password> <first name> <last name>\n" +
						"	     Only root has this privilege.";
				break;
			case "echo":
				result += "	echo - display a line of text\n" + 
						"	     Usage : echo <message> <parameter>\n" +
						"	     For a nicely-formatted output, use -POO parameter";
				break;
			case "sync":
				result += "	sync - sync a file/directory from the cloud\n" +
						"	     Usage : sync <name>";
				break;
			case "upload" :
				result += "	upload - upload a file/directory to the cloud\n" +
						"	     Usage : upload <name>";
				break;
			case "cloudspace":
				result += "	cloudspace - show occupied space for each station\n" + 
						"	     Usage : cloudspace";
				break;
			case "cloudclear":
				result += "	cloudclear - clears each cloud station\n" + 
						"	     Usage : cloudclear";
				break;
			case "edit":
				result += "	edit - edit the contents of a file\n" +
						"     	 Usage : edit <name>";
				break;
			default:
				result += "Available commands:\n" +
						"	ls, cd, cat, pwd, touch, mkdir, rm, login, logout, userinfo, newuser, showusers, echo, edit\n" +
						"	sync, upload, cloudspace, cloudclear\n" +
						"	Usage : man <command name>";
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

