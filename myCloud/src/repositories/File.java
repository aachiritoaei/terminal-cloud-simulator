package repositories;

import java.util.Date;
import commands.Command;
import users.User;

public class File extends RepositoryClass {
	/* File constructor */
	public File(String name, int size, String type, String data, Date creationDate, User owner, boolean read, boolean write, RepositoryClass father){
		this.name = name;
		this.size = size;
		this.type = type;
		this.data = data;
		this.creationDate = creationDate;
		this.owner = owner;
		this.readable = read;
		this.writeable = write;
		this.father = father;
	}
	/* Accept a command */
	public void accept(Command com){
		com.execute(this);
	}
}
