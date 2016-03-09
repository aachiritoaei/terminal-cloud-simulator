package repositories;

import java.util.Date;
import java.util.Vector;
import commands.Command;
import users.User;

public class Directory extends RepositoryClass {
	/* Subdirectories and files */
	protected Vector<RepositoryClass> children;
	/* Getters & setters */
	public Vector<RepositoryClass> getChildren() {
		return children;
	}
	public void setChildren(Vector<RepositoryClass> children) {
		this.children = children;
	}
	/* Directory constructor */
	public Directory(String name, int size, String type, Date creationDate, User owner, boolean read, boolean write, RepositoryClass father){
		this.name = name;
		this.size = size;
		this.type = type;
		this.creationDate = creationDate;
		this.owner = owner;
		this.readable = read;
		this.writeable = write;
		this.father = father;
		this.children = new Vector<RepositoryClass>();
	}
	/* Add child */
	public void addChild(RepositoryClass child){
		children.add(child);
	}
	/* Remove child */
	public void removeChild(RepositoryClass child){
		children.remove(child);
	}
	/* Get child using name */
	public RepositoryClass getChildFromName(String name){
		for(RepositoryClass rep : children)
			if(rep.getName().equals(name))
				return rep;
		return null;
	}
	/* Accept a command */
	public void accept(Command com){
		com.execute(this);
	}
}
