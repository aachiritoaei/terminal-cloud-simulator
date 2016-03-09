package commands;
/* Generate commands */

public class CommandFactory {
	public AbstractCommand getCommand(String commandType){
		switch(commandType){
			case "ls":
				return new CommandLs();
			case "cd":
				return new CommandCd();
			case "cat":
				return new CommandCat();
			case "pwd":
				return new CommandPwd();
			case "touch":
				return new CommandTouch();
			case "mkdir":
				return new CommandMkdir();
			case "rm":
				return new CommandRm();
			case "echo":
				return new CommandEcho();
			case "newuser":
				return new CommandNewuser();
			case "userinfo":
				return new CommandUserinfo();
			case "showusers":
				return new CommandShowusers();
			case "login":
				return new CommandLogin();
			case "logout":
				return new CommandLogout();
			case "man":
				return new CommandMan();
			case "edit":
				return new CommandEditfile();
			default:
				return null;
		}
	}
}
