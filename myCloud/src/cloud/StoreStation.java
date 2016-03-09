package cloud;

import java.util.HashSet;
import repositories.*;

public class StoreStation {
	protected int id;
	protected int maximumSize;
	protected int currentSize;
	protected MachineId nextMachine;
	protected HashSet<RepositoryClass> repositoryHash;
	/* StoreStation Constructor */
	public StoreStation(int id, int maximumSize, int currentSize){
		this.id = id;
		this.maximumSize = maximumSize;
		this.currentSize = currentSize;
		nextMachine = new MachineId();
		repositoryHash = new HashSet<RepositoryClass>();
	}
	/* Store a repository */
	public void store(RepositoryClass rep){
		repositoryHash.add(rep);
	}
	/* Search for a repository */
	public RepositoryClass search(RepositoryClass rep){
		for(RepositoryClass elem : repositoryHash){
			if(elem.getName().equals(rep.getName()) == true &&
			   elem.getFather().getName().equals(rep.getFather().getName()) == true)
				return elem;
		}
		return null;
	}
	/* Getters & Setters */
	public int getId(){
		return this.id;
	}
	public void setId(int id){
		this.id = id;
	}
	public int getMaximumSize(){
		return this.maximumSize;
	}
	public void setMaximumSize(int maximumSize){
		this.maximumSize = maximumSize;
	}
	public int getCurrentSize(){
		return this.currentSize;
	}
	public void setCurrentSize(int currentSize){
		this.currentSize = currentSize;
	}
}
