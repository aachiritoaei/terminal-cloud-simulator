package cloud;

import java.util.Date;
import commands.*;
import repositories.*;
import exceptions.*;

/* CloudService implementations 
 * Contains the stations and the cloud-required commands 
 * In order to have only one cloud service, we use Singleton Pattern */

public class CloudService extends AbstractCommand {
	protected StoreStation[] station;
	protected static final CloudService INSTANCE = new CloudService();
	/* Private constructor - create 3 storing stations */
	private CloudService(){
		station = new StoreStation[4];
		station[1] = new StoreStation(1, 10000, 0);
		station[2] = new StoreStation(2, 10000, 0);
		station[3] = new StoreStation(3, 10000, 0);
	}
	/* Get this instance */
	public static final CloudService getInstance(){
		return INSTANCE;
	}
	/* Clone a repository(deep copy) */
	public RepositoryClass cloneRepo(RepositoryClass rep){
		if(rep instanceof File){//cloning a file
			RepositoryClass clone = new File(rep.getName(), rep.getSize(), rep.getType(), rep.getData(), rep.getCreationDate(), 
					rep.getOwner(), rep.isReadable(), rep.isWriteable(), rep.getFather());
			return clone;
		} else if (rep instanceof Directory) { //cloning a directory
			RepositoryClass clone = new Directory(rep.getName(), rep.getSize(), rep.getType(), rep.getCreationDate(),
					rep.getOwner(), rep.isReadable(), rep.isWriteable(), rep.getFather());
			for(RepositoryClass child : ((Directory) rep).getChildren()){
				RepositoryClass childClone = cloneRepo(child);
				clone.addChild(childClone);
			}
			return clone;
		} else if (rep instanceof MachineId) { // cloning a MachineId
			MachineId next = (MachineId) rep;
			RepositoryClass searchedRep = station[next.getNextMachineId()].search(next);
			return cloneRepo(searchedRep);
		}
		return null;
	}
	/* Execute sync */
	public void syncExecute(String arguments){
		if(arguments.equals("")){ //missing repository name
			myGUI.addText("Missing file/directory name.");
			return;
		}
		String[] splitted = arguments.split(" ");
		String name = splitted[0];
		RepositoryClass rep = currentDirectory.getChildFromName(name);
		if(rep == null){
			myGUI.addText("File or directory not found.\n" +
					"If you have deleted the file/dir you want to sync, please recreate it using touch/mkdir first.\n" +
					"Then, use sync <name of this file/dir> again.");
			return;
		}
		/* Verify writing permission */
		if(verifyWriteable(rep) == false){
			myGUI.addText("Permission denied.");
			return;
		}
		sync(rep);
	}
	/* Sync */
	public void sync(RepositoryClass rep){
		/* Sync a file */
		if(rep instanceof File){
			/* Search file on cloud */
			for(int i = 1; i <= 3; i++){
				RepositoryClass searchedFile = station[i].search(rep);
				if(searchedFile != null){ // it was found in the cloud
					RepositoryClass clone = cloneRepo(searchedFile);
					Directory father = (Directory)rep.getFather();
					father.removeChild(rep);
					father.addChild(clone);
					father.setSize(father.getSize() - rep.getSize() + clone.getSize());
					myGUI.addText("File synchronized from the cloud.");
					return;
				}
			}
			myGUI.addText("File not found in the cloud.");
		/* Sync a directory */
		} else if (rep instanceof Directory){
			/* Search dir on cloud */
			for(int i = 1; i <= 3; i++){
				RepositoryClass searchedDir = station[i].search(rep);
				if(searchedDir != null){// it was found in the cloud
					RepositoryClass clone = cloneRepo(searchedDir);
					Directory father = (Directory)rep.getFather();
					father.removeChild(rep);
					father.addChild(clone);
					father.setSize(father.getSize() - rep.getSize() + clone.getSize());
					myGUI.addText("Directory synchronized from the cloud.");
					return;
				}
			}
			myGUI.addText("Directory not found in the cloud.");
		}
	}
	/* Execute upload */
	public void uploadExecute(String arguments){
		if(arguments.equals("")){ //missing repository name
			myGUI.addText("Missing file/directory name.");
			return;
		}
		String[] splitted = arguments.split(" ");
		String name = splitted[0];
		RepositoryClass rep = currentDirectory.getChildFromName(name);
		if(rep == null){
			myGUI.addText("File or directory not found.");
			return;
		}
		/* Verify read permission */
		if(verifyReadable(rep) == false){
			myGUI.addText("Permission denied.");
			return;
		}
		upload(rep);
	}
	/* Upload a file or a directory */
	public void upload(RepositoryClass rep){
		try {
			if(rep instanceof File){
				uploadFile(rep);
			} else if(rep instanceof Directory){
				/* Verify available space */
				int availableSpace = 0;
				for(int i = 1; i <= 3; i++){
					availableSpace += station[i].getMaximumSize() - station[i].getCurrentSize();
				}
				if(availableSpace < rep.getSize()){
					throw new MyNotEnoughSpaceException(rep.getFather(), currentCommand, userManager.getCurrentUser(), new Date(), rep.getSize());
				}
				uploadDirectory(rep, 1);
				myGUI.addText("Directory stored.");
			}
		} catch(MyNotEnoughSpaceException e){
			myGUI.addText("Insufficient cloud space.\n" +
					"Try clearing it using cloudclear, then reupload the file/directory.");
			Logger systemLogger = new Logger();
			systemLogger.logException(e);
		}
	}
	/* Upload a given file  */
	public void uploadFile(RepositoryClass rep) throws MyNotEnoughSpaceException{
		if(rep instanceof File){
			for(int i = 1; i <= 3; i++){//verify available space & upload
				if(rep.getSize() <= (station[i].getMaximumSize() - station[i].getCurrentSize())){//there's enough space
					RepositoryClass clone = cloneRepo(rep);
					station[i].store(clone);
					station[i].setCurrentSize(station[i].getCurrentSize() + rep.getSize());
					myGUI.addText("File stored.");
					return ;
				}
			}
			throw new MyNotEnoughSpaceException(rep.getFather(), currentCommand, userManager.getCurrentUser(), new Date(), rep.getSize());
		}
	}
	/* Upload a given directory */
	public void uploadDirectory(RepositoryClass rep, int currentStation) throws MyNotEnoughSpaceException{
		if(rep instanceof Directory){
			RepositoryClass dirClone = new Directory(rep.getName(), rep.getSize(), rep.getType(), rep.getCreationDate(),
					rep.getOwner(), rep.isReadable(), rep.isWriteable(), rep.getFather());
			station[currentStation].repositoryHash.add(dirClone);
			for(RepositoryClass child : ((Directory) rep).getChildren()){// try uploading every child
				if(child.getSize() <= (station[currentStation].getMaximumSize() - station[currentStation].getCurrentSize())){//there's space on the station for a child
					RepositoryClass childClone = cloneRepo(child);
					dirClone.addChild(childClone);
					station[currentStation].setCurrentSize(station[currentStation].getCurrentSize() + childClone.getSize());
				} else { // there's no space on the current station for a child
					if(child instanceof File){//if child is a file, search for space on another station
						boolean spaceFound = false;
						for(int i = 1; i <= 3; i++){
							if(child.getSize() <= (station[i].getMaximumSize() - station[i].getCurrentSize())){
								MachineId tempMachine = new MachineId(child.getName(), child.getFather(), i);
								dirClone.addChild(tempMachine);
								RepositoryClass fileClone = cloneRepo(child);
								station[i].store(fileClone);
								station[i].setCurrentSize(station[i].getCurrentSize() + child.getSize());
								spaceFound = true;
								break;
							}
						}
						if(spaceFound == false){//space not found
							throw new MyNotEnoughSpaceException(rep.getFather(), currentCommand, userManager.getCurrentUser(), new Date(), rep.getSize());
						}
					} else if(child instanceof Directory){//daca e director
						boolean spaceFound = false;
						for(int i = 1; i <= 3; i++){ //if child is a dir, search for space on another station
							if(child.getSize() <= (station[i].getMaximumSize() - station[i].getCurrentSize())){
								MachineId tempMachine = new MachineId(child.getName(), child.getFather(), i);
								dirClone.addChild(tempMachine);
								RepositoryClass childClone = cloneRepo(child);
								station[i].store(childClone);
								station[i].setCurrentSize(station[i].getCurrentSize() + child.getSize());
								spaceFound = true;
								break;
							}
						}
						if(spaceFound == false){//space not found, we need to split it again
							MachineId tempMachine = new MachineId(child.getName(), child.getFather(), 1);
							dirClone.addChild(tempMachine);
							uploadDirectory(child, 1);
						}
					}
				}
			}
		}
	}
	/* Show current occupied space of each station */
	public void cloudSpace(){
		for(int i = 1; i <= 3; i++)
			myGUI.addText("Station " + i + " has " + station[i].getCurrentSize() + " occupied space.");
	}
	/* Clear cloud stations */
	public void cloudClear(){
		for(int i = 1; i <= 3; i++){
			station[i].repositoryHash.clear();
			station[i].setCurrentSize(0);
		}
		myGUI.addText("Cloud cleared.");
	}
	/* Verify read permission */
	public boolean verifyReadable(RepositoryClass rep){
		if(userManager.getCurrentUser().getUsername().equals("guest"))
			return false;
		if(userManager.getCurrentUser().getUsername().equals("root"))
			return true;
		if(rep.getOwner().getUsername().equals("-"))
			return true;
		if(userManager.getCurrentUser().getUsername().equals(rep.getOwner().getUsername()) == true && rep.isReadable() == true)
			return true;
		return false;
	}
	/* Verify write permission */
	public boolean verifyWriteable(RepositoryClass rep){
		if(userManager.getCurrentUser().getUsername().equals("guest"))
			return false;
		if(userManager.getCurrentUser().getUsername().equals("root"))
			return true;
		if(rep.getOwner().getUsername().equals("-"))
			return true;
		if(userManager.getCurrentUser().getUsername().equals(rep.getOwner().getUsername()) && rep.isWriteable() == true)
			return true;
		return false;
	}
	public void execute(RepositoryClass rep) {}
	public void execute(Directory dir) {}
	public void execute(File file) {}
}
