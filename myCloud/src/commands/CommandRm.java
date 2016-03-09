package commands;

import java.util.*;

import exceptions.*;
import repositories.*;

/* Remove a file or a directory */
public class CommandRm extends WriteCommand {
	private String path;
	private String parameter;
	private String rmAux;
	/* Set parameters and execute */
	public void setParametersAndExecute(String arguments){
		path = "";
		parameter = "";
		rmAux = "";
		/* Take path and parameter */
		if(arguments.contains("-r")){
			parameter = "-r";
		}
		StringTokenizer str = new StringTokenizer(arguments, " ");
		if(str.hasMoreTokens()){
			rmAux = str.nextToken();
			if(rmAux.startsWith("-r")){ //it's a parameter
				if(str.hasMoreTokens())
					rmAux = str.nextToken();
				else{
					myGUI.addText("Missing file/directory name.");
					return;
				}
			}
		} else {
			myGUI.addText("Missing file/directory name.");
			return;
		}
		path = rmAux;
		String splitted[] = path.split("/");
		String rmAux = "";
		for(int i = 0; i < splitted.length - 1; i++){
			rmAux += splitted[i] + "/";
		}
		/* Move where the command should be executed */
		CommandCd cd = new CommandCd();
		cd.setPath(rmAux);
		cd.setComponentsFromPath();
		cd.commandRepository = cd.currentDirectory;
		if(rmAux.equals("") == false)
			cd.execute(cd.currentDirectory);
		/* Execute it */
		boolean found = false;
		Directory lsDir = (Directory)cd.currentDirectory;
		Vector<RepositoryClass> temp = new Vector<RepositoryClass>();
		for(RepositoryClass repo : lsDir.getChildren()){
			if(repo.getName().matches(splitted[splitted.length - 1])){
				temp.add(repo);
				found = true;
			}
		}
		for(RepositoryClass repo: temp){//avoid concurrentModification
			execute(repo);
		}
		try {
			if(found == false){
				myGUI.addText("No such file or directory.");
				throw new MyInvalidPathException(lsDir, currentCommand, userManager.getCurrentUser(), new Date());
			}
		} catch(MyInvalidPathException e) {
			systemLogger.logException(e);
		}
		/* Back to initial position */
		cd.currentDirectory = commandRepository;
	}
	/* Execute */
	public void execute(RepositoryClass rep) {
		if(verifyWriteable(rep) == false){
			myGUI.addText("Permission denied.");
			return;
		}
		rep.accept(this);
	}
	/* For directory */
	public void execute(Directory dir) {
		if(parameter.equals("-r") == false && dir.getChildren().size() != 0){
			myGUI.addText("Missing '-r' parameter.");
			return;
		}
		Directory father = (Directory)dir.getFather();
		Vector<RepositoryClass> temp = new Vector<RepositoryClass>();
		for(RepositoryClass rep : dir.getChildren())
			temp.add(rep);
		for(RepositoryClass repo: temp){//avoid concurrentModification
			execute(repo);
		}
		father.removeChild(dir);
		/* Remove size from father directories */
		RepositoryClass rep = father;
		while(rep.getName() != "/"){//modify size
			rep.setSize(rep.getSize() - dir.getSize());
			rep = rep.getFather();
		}
		rep.setSize(rep.getSize() - dir.getSize());
		myGUI.addText("Directory removed.");
	}
	/* For file */
	public void execute(File file) {
		Directory father = (Directory)file.getFather();
		father.removeChild(file);
		/* Remove size from father directories */
		RepositoryClass rep = father;
		while(rep.getName() != "/"){//modify size
			rep.setSize(rep.getSize() - file.getSize());
			rep = rep.getFather();
		}
		rep.setSize(rep.getSize() - file.getSize());
		myGUI.addText("File removed.");
	}
}
