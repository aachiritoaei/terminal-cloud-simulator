package repositories;

import java.util.Date;
import commands.Command;
import users.User;

public class RepositoryClass implements Repository{
	/* Repository descriptors */
	protected String name;
	protected int size;
	protected String type;
	protected String data;
	protected Date creationDate;
	/* Repository permissions */
	protected User owner;
	protected boolean readable;
	protected boolean writeable;
	/* Father */
	protected RepositoryClass father;
	/* Getters & Setters */
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getSize() {
		return size;
	}
	public void setSize(int size) {
		this.size = size;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}
	public Date getCreationDate() {
		return creationDate;
	}
	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}
	public User getOwner() {
		return owner;
	}
	public void setOwner(User owner) {
		this.owner = owner;
	}
	public boolean isReadable() {
		return readable;
	}
	public void setReadable(boolean read) {
		this.readable = read;
	}
	public boolean isWriteable() {
		return writeable;
	}
	public void setWriteable(boolean write) {
		this.writeable = write;
	}
	public RepositoryClass getFather() {
		return father;
	}
	public void setFather(RepositoryClass father) {
		this.father = father;
	}
	/* Add child */
	public void addChild(RepositoryClass child){}
	/* Remove child */
	public void removeChild(RepositoryClass child){}
	/* Get child using name */
	public RepositoryClass getChildFromName(String name){
		return null;
	}
	/* Accept a command */
	public void accept(Command com){
		com.execute(this);
	}
}
