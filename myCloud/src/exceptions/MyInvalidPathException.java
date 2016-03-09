package exceptions;

import java.util.Date;

import repositories.*;
import users.*;

/* Exception thrown by CD Command */
public class MyInvalidPathException extends Exception {
	protected RepositoryClass source;
	protected String commandName;
	protected User user;
	protected Date exceptionDate;
	
	public MyInvalidPathException(RepositoryClass source, String commandName, User user, Date exceptionDate){
		this.source = source;
		this.commandName = commandName;
		this.user = user;
		this.exceptionDate = exceptionDate;
	}
	public String toString(){
		String result = "MyInvalidPathException\n";
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
		/* Date when the command was given */
		result += "Date : " + exceptionDate +"\n";
		return result;
	}
}
