package cloud;

import repositories.*;
import commands.*;

/* When uploading a directory, shows position of a child if the directory needs to be splitted 
 * "Pointer" to a repository */
public class MachineId extends RepositoryClass {
	protected int nextMachineId;
	/* Construct a MachineId */
	public MachineId(){
		super();
	}
	/* A machine id will only keep the name, father and next StoreStation id */
	public MachineId(String name, RepositoryClass father, int nextMachineId){
		this.name = name;
		this.father = father;
		this.nextMachineId = nextMachineId;
	}
	/* Getters & Setters */
	public int getNextMachineId(){
		return this.nextMachineId;
	}
	public void setNextMachineId(int nextMachineId){
		this.nextMachineId = nextMachineId;
	}
	@Override
	public void accept(Command com) {}
}
