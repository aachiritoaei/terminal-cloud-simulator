package commands;

import java.util.*;

import exceptions.*;
import repositories.*;

/* Change current directory */
public class CommandCd extends ReadCommand {
	private static String path;
	private static Vector<String> pathComponents = new Vector<String>();
	private static int index = 0;
	private static int pathType;
	/* Set path for CD */
	public void setPath(String path){
		this.path = path;
	}
	/* Get the path components */
	public void setComponentsFromPath(){
		/* Prepare path - if it has more elements than a single path */
		if(path.contains(" ")){
			int indexOfSpace;
			indexOfSpace = path.indexOf(' ');
			path = path.substring(0, indexOfSpace);
		}
		/* Clear vector of path components */
		index = 0;
		pathComponents.clear();
		/* Path type */
		if(path.equals("")) //No path
			pathType = 0;
		else if(path.startsWith("/")){ //Absolute path
			pathType = 1;
			currentDirectory = rootDirectory;
			StringTokenizer str = new StringTokenizer(path, "/");
			while(str.hasMoreTokens())
				pathComponents.add(str.nextToken());
			pathComponents.add("");
		} 
		else{ //Relative path
			pathType = 2;
			StringTokenizer str = new StringTokenizer(path, "/");
			while(str.hasMoreTokens())
				pathComponents.add(str.nextToken());
			pathComponents.add("");
		}
	}
	/* Execute */
	public void execute(RepositoryClass rep) {
		/* Verify permission */
		if(verifyReadable(rep) == false){
			currentDirectory = commandRepository;
			myGUI.addText("Permission denied.");
			return;
		}
		rep.accept(this);
	}
	/* For directory */
	public void execute(Directory dir) {
		try {
			switch(pathType){
			case 0: /* No path - go to rootDirectory */
				currentDirectory = rootDirectory;
				break;
			case 1: /* Absolute path */
				if(index < pathComponents.size()){
					currentDirectory = dir;
					String nextDir = pathComponents.get(index);
					index++;
					RepositoryClass aux = dir.getChildFromName(nextDir);
					if(aux != null && nextDir != ""){
						this.execute(aux);

					} else if (nextDir != ""){
						currentDirectory = commandRepository;
						myGUI.addText("Directory not found.");
						throw new MyInvalidPathException(dir, currentCommand, userManager.getCurrentUser(), new Date());
					}
				}
				break;
			case 2: /* Relative path */
				if(index < pathComponents.size()){
					currentDirectory = dir;
					String nextDir = pathComponents.get(index);
					index++;
					if(nextDir.equals("..")){ //special case ".."
						currentDirectory = currentDirectory.getFather();
						this.execute(currentDirectory);
					} else {
						RepositoryClass aux = dir.getChildFromName(nextDir);
						if(aux != null && nextDir != ""){
							this.execute(aux);
						} else if (nextDir != ""){
							currentDirectory = commandRepository;
							myGUI.addText("Directory not found.");
							throw new MyInvalidPathException(currentDirectory, currentCommand, userManager.getCurrentUser(), new Date());
						}
					}
				}
				break;
			}
		} catch(MyInvalidPathException e) {
			systemLogger.logException(e);
		}
	}
	/* For file */
	public void execute(File file) {
		try {
			currentDirectory = commandRepository;
			myGUI.addText(file.getName() + " is not a directory.");
			throw new MyInvalidPathException(currentDirectory, currentCommand, userManager.getCurrentUser(), new Date());
		} catch(MyInvalidPathException e) {
			systemLogger.logException(e);
		}
	}
}