package exceptions;

import java.util.Date;

import repositories.*;
import users.*;

/* Exception thrown by UPLOAD command */
public class MyNotEnoughSpaceException extends Exception {
	private RepositoryClass source;
	private String commandName;
	private User user;
	private Date exceptionDate;
	private int size;
	
	public MyNotEnoughSpaceException(RepositoryClass source, String commandName, User user, Date exceptionDate, int size){
		this.source = source;
		this.commandName = commandName;
		this.user = user;
		this.exceptionDate = exceptionDate;
		this.size = size;
	}
	public String toString(){
		String result = "MyNotEnoughSpaceException\n";
		/* The place where the command was given */
		String path = "";
		RepositoryClass rep = source;
		if(rep.getName().equals("/"))
			path = "/";
		else {
			while(rep.getName() != "/"){
				path = rep.getName() + "/" + path;
				rep = rep.getFather();
			}
			path = "/" + path;
		}
		result += "Current path : " + path + "\n";
		/* Name of the command */
		result += "Command : " + commandName + "\n";
		/* User executing the command */
		result += "User : " + user.getUsername() + "\n";
		/* Size of the file/dir */
		result += "Size : " + size + "\n";
		/* Date when the command was given */
		result += "Date : " + exceptionDate +"\n";
		return result;
	}
}
