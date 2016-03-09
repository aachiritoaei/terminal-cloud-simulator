package exceptions;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;
import users.*;

/* Event logger 
 * Monitors logins, logouts, and exceptions */
public class Logger {
	public static String loggerHistory = "";
	/* Log an exception */
	public void logException(Exception e){
		loggerHistory += e.toString() + "\n";
	}
	/* Log a user's login */
	public void logLogin(User user){
		loggerHistory += "Login\n" + "User : " + user.getUsername() + "\nDate of login : " + user.getLastLogin() + "\n\n";
	}
	/* Log a user's logout */
	public void logLogout(User user){
		loggerHistory += "Logout\n" + "User : " + user.getUsername() + "\nDate of logout : " + new Date() + "\n\n";
	}
	/* Write in a file everything that has been logged */
	public void writeToFile(){
		try {
			FileWriter logFile = new FileWriter("logFile.log");
			logFile.write(loggerHistory);
			logFile.close();
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
}
