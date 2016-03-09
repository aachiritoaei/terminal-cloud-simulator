package exceptions;

import java.util.Date;

import repositories.*;
import users.*;

/* Exception thrown by PWD Command */
public class MyPathTooLongException extends MyInvalidPathException {
	public MyPathTooLongException(RepositoryClass source, String commandName, User user, Date exceptionDate){
		super(source, commandName, user, exceptionDate);
	}
	public String toString(){
		String result = "MyPathTooLongException\n";
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
