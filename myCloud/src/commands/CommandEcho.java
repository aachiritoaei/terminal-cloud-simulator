package commands;

import repositories.*;

/* Display a message */
public class CommandEcho extends AbstractCommand {
	private String message;
	/* Set parameters and execute */
	public void setParametersAndExecute(String arguments){
		if(arguments.contains("-POO")){ // -POO parameter -> nicely-formatted in JDialog
			String splitted[] = arguments.split("-POO");
			if(arguments.startsWith("-POO"))
				if(splitted.length > 0)
					message = splitted[1];
				else
					message = "";
			else
				message = splitted[0];
			myGUI.addEchoPOO(message);
		} else {
			myGUI.addText(arguments);
		}
	}
	/* Execute */
	public void execute(RepositoryClass rep) {
		rep.accept(this);
	}
	public void execute(Directory dir) {}
	public void execute(File file) {}

}
