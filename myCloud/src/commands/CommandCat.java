package commands;

import java.util.StringTokenizer;

import repositories.*;

/* Show content of a repository */
public class CommandCat extends ReadCommand {
	private String path;
	/* Set parameters and execute */
	public void setParametersAndExecute(String arguments){
		/* Verify permission */
		if(verifyReadable(currentDirectory) == false){
			myGUI.addText("Permission denied.");
			return;
		}
		path = "";
		/* Take path */
		StringTokenizer str = new StringTokenizer(arguments, " ");
		if(str.hasMoreTokens()){
			path = str.nextToken();
			String splitted[] = path.split("/");
			String catAux = "";
			for(int i = 0; i < splitted.length - 1; i++){
				catAux += splitted[i] + "/";
			}
			/* Move where the command should be executed */
			CommandCd cd = new CommandCd();
			cd.setPath(catAux);
			cd.setComponentsFromPath();
			cd.commandRepository = cd.currentDirectory;
			if(catAux.equals("") == false)
				cd.execute(cd.currentDirectory);
			/* Execute it */
			RepositoryClass rep = cd.currentDirectory.getChildFromName(splitted[splitted.length - 1]);
			if(rep != null)
				execute(rep);
			else
				myGUI.addText("No such file.");
			/* Back to initial position */
			cd.currentDirectory = commandRepository;
		} else {
			myGUI.addText("Missing 'cat' arguments.");
		}
	}
	/* Execute */
	public void execute(RepositoryClass rep){
		/* Verify permission */
		if(verifyReadable(rep) == false){
			myGUI.addText("Permission denied.");
			return;
		}
		rep.accept(this);
	}
	/* For directory */
	public void execute(Directory dir){
		myGUI.addText("Command 'cat' cannot be used on a directory.");
	}
	/* For file */
	public void execute(File file){
		myGUI.addText(file.getName() + " : \n" + file.getData());
	}
}