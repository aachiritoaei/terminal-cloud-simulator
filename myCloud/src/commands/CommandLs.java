package commands;

import java.util.StringTokenizer;

import repositories.*;

/* List repository contents */
public class CommandLs extends ReadCommand {
	private String path;
	private String parameters;
	private String processedParameters;
	/* Set parameters and execute the command */
	public void setParametersAndExecute(String arguments){
		/* Initialize  */
		path = "";
		parameters = "";
		processedParameters = "";
		/* Take path and parameters */
		StringTokenizer str = new StringTokenizer(arguments, " ");
		String lsAux = new String();
		while(str.hasMoreTokens()){
			lsAux = str.nextToken();
			if(lsAux.startsWith("-")){// it is a parameter
				parameters += lsAux; 
			} else {				  // it is a path
				path = lsAux;
			}
		}
		/* Parameters processing */
		if(parameters.contains("a"))
			processedParameters += "a";
		if(parameters.contains("r"))
			processedParameters += "r";
		if(parameters.contains("l"))
			processedParameters += "l";
		if(parameters.contains("POO"))
			processedParameters += "POO";
		if(path.equals("") == false){ // ls with path
			String splitted[] = path.split("/");
			lsAux = "";
			for(int i = 0; i < splitted.length - 1; i++){
				lsAux += splitted[i] + "/";
			}
			/* Move where the command should be executed */
			CommandCd cd = new CommandCd();
			cd.setPath(lsAux);
			cd.setComponentsFromPath();
			cd.commandRepository = cd.currentDirectory;
			if(lsAux.equals("") == false)
				cd.execute(cd.currentDirectory);
			/* Execute it */
			boolean found = false;
			Directory lsDir = (Directory)cd.currentDirectory;
			for(RepositoryClass rep : lsDir.getChildren()){
				if(rep.getName().matches(splitted[splitted.length - 1])){
					execute(rep);
					found = true;
					if(processedParameters.contains("POO"))
						myGUI.addCommand("");
				}
			}
			if(found == false)
				myGUI.addText("No such file or directory.");
			/* Back to initial position */
			cd.currentDirectory = commandRepository;
		} else { // ls without path
			execute(currentDirectory);
			if(processedParameters.contains("POO"))
				myGUI.addCommand("");
		}
	}
	/* Execute */
	public void execute(RepositoryClass rep){
		if(verifyReadable(rep) == false){
			return;
		}
		rep.accept(this);
	}
	/* For directory */
	public void execute(Directory dir){
		/* Verify permission */
		if(verifyReadable(dir) == false){
			return;
		}
		/* Choose case according to the parameters  */
		String result = new String();
		switch(processedParameters){
			case "a": //detailed information about directory
				result = "";
				result += dir.getName() + " " + 
						dir.getSize() + " " + 
						dir.getOwner().getUsername() + " " + 
						dir.isReadable() + " " + 
						dir.isWriteable() + " " + 
						"[" + dir.getCreationDate() + "] directory";
				myGUI.addText(result);
				break;
			case "aPOO": //nicely-formatted detailed information about directory
				myGUI.addLsPOO(dir);
				break;
			case "r": //recursive 
				result = "";
				for(RepositoryClass rep : dir.getChildren()){
					result += rep.getName() + "  ";
				}
				if(result.equals("") == false)
					myGUI.addText(result + "\n");
				for(RepositoryClass rep : dir.getChildren()){
					if(rep instanceof Directory){
						//for a better output organization
						//could be done without 'instanceof' to have the same result as in 'comenzi.txt' example
						myGUI.addText(rep.getName() + ":");
						execute(rep);
					}
				}
				break;
			case "l": //detailed information about directory contents
				result = "";
				processedParameters = "a";
				for(RepositoryClass rep : dir.getChildren())
					execute(rep);
				myGUI.addText(result);
				break;
			case "lPOO": //nicely-formatted detailed information about directory contents
				myGUI.addLsLPOO(dir);
				break;
			case "ar": //recursive detailed information
				result = "";
				result += dir.getName() + " " + 
						dir.getSize() + " " + 
						dir.getOwner().getUsername() + " " + 
						dir.isReadable() + " " + 
						dir.isWriteable() + " " + 
						"[" + dir.getCreationDate() + "] directory";
				myGUI.addText(result);
				for(RepositoryClass rep : dir.getChildren()){
					this.execute(rep);
				}
				break;
			case "arPOO": //nicely-formatted recursive detailed information
				myGUI.addLsPOO(dir);
				for(RepositoryClass rep : dir.getChildren()){
					this.execute(rep);
				}
				break;
			case "rl":// detailed recursive
				result = "";
				for(RepositoryClass rep : dir.getChildren()){
					if(verifyReadable(rep))
						result += rep.getName() + " " + 
								rep.getSize() + " " + 
								rep.getOwner().getUsername() + " " + 
								rep.isReadable() + " " + 
								rep.isWriteable() + " " + 
								"[" + rep.getCreationDate() + "]\n";
				}
				if(result.equals("") == false)
					myGUI.addText(result);
				for(RepositoryClass rep : dir.getChildren()){
					if(rep instanceof Directory){
						if(verifyReadable(rep)){
							myGUI.addText(rep.getName() + ":");
							this.execute(rep);
						}
					}
				}
				break;
			default:
				result = "";
				for(RepositoryClass rep : dir.getChildren()){
					if(verifyReadable(rep))
						result += rep.getName() + "  ";
				}
				myGUI.addText(result);
		}
	}
	/* For file */
	public void execute(File file){
		/* Verify permission */
		if(verifyReadable(file) == false){
			return;
		}
		/* Choose case according to parameters */
		String result = new String();
		if(processedParameters.contains("a") || processedParameters.contains("l")){//detailed information
			if(processedParameters.contains("POO") == false){
				result += file.getName() + " " + 
						file.getSize() + " " + 
						file.getOwner().getUsername() + " " + 
						file.isReadable() + " " + 
						file.isWriteable() + " " + 
						"[" + file.getCreationDate() + "]" +
						" file";
				myGUI.addText(result);
			} else { //we have POO parameter -> nicely formatted
				myGUI.addLsPOO(file);
			}
		} else {
			myGUI.addText(file.getName());
		}
	}
}