package main;

import java.util.Scanner;
import javax.swing.JScrollBar;
import commands.*;
import repositories.*;
import cloud.*;

/* AAACloud Control Center */
public class AAACloud {
	public void execute(){
		/* Initialize file system, users, logger and the GUI */
		CommandPrepare prepareEnvironment = new CommandPrepare();
		prepareEnvironment.prepareUsers();
		prepareEnvironment.prepareFileHierarchy();
		prepareEnvironment.prepareLogger();
		prepareEnvironment.prepareGUI();
		/* Initialize cloud system */
		CloudService AAACloud = CloudService.getInstance();
		/* Commands */
		CommandFactory commandFactory = new CommandFactory();
		AbstractCommand ls = commandFactory.getCommand("ls");
		AbstractCommand cd = commandFactory.getCommand("cd");
		AbstractCommand cat = commandFactory.getCommand("cat");
		AbstractCommand pwd = commandFactory.getCommand("pwd");
		AbstractCommand touch = commandFactory.getCommand("touch");
		AbstractCommand mkdir = commandFactory.getCommand("mkdir");
		AbstractCommand rm = commandFactory.getCommand("rm");
		AbstractCommand echo = commandFactory.getCommand("echo");
		AbstractCommand newuser = commandFactory.getCommand("newuser");
		AbstractCommand userinfo = commandFactory.getCommand("userinfo");
		AbstractCommand showusers = commandFactory.getCommand("showusers");
		AbstractCommand login = commandFactory.getCommand("login");
		AbstractCommand logout = commandFactory.getCommand("logout");
		AbstractCommand man = commandFactory.getCommand("man");
		AbstractCommand editfile = commandFactory.getCommand("edit");	
		/* Redirect System.in to a JTextField */
		JTexfFieldStream myJTextFieldStream = new JTexfFieldStream(prepareEnvironment.myGUI.commandLine);
		prepareEnvironment.myGUI.commandLine.addActionListener(myJTextFieldStream);
		System.setIn(myJTextFieldStream);
		prepareEnvironment.myGUI.commandLine.requestFocus(); // focus keyboard input on jtextfield
		/* Begin execution of the program */
		Scanner scanner = new Scanner(System.in);
		String input = scanner.nextLine();
		while (true) {
			prepareEnvironment.myGUI.addCommand(input);
			prepareEnvironment.currentCommand = input;
			String commandType = new String();
			String arguments = new String();
			if(input.contains(" ")){/* Command with arguments */
				int index = input.indexOf(' ');
				commandType = input.substring(0, index);
				arguments = input.substring(index+1);
			} else { /* Command without arguments */
				commandType = input;
			}
			switch(commandType){
				case "ls": /* LS Command */
					ls.setParametersAndExecute(arguments);
					break;
				case "cd": /* CD Command */
					cd.setPath(arguments);
					cd.setComponentsFromPath();
					cd.commandRepository = cd.currentDirectory;
					cd.execute(cd.currentDirectory);
					break;
				case "pwd": /* PWD Command */
					pwd.execute(pwd.currentDirectory);
					break;
				case "cat": /* CAT Command */
					cat.setParametersAndExecute(arguments);
					break;
				case "touch": /* TOUCH Command */
					touch.setParametersAndExecute(arguments);
					break;
				case "mkdir": /* MKDIR Command */
					mkdir.setParametersAndExecute(arguments);
					break;
				case "rm": /* RM Command */ 
					rm.setParametersAndExecute(arguments);
					break;
				case "echo": /* ECHO Command */
					echo.setParametersAndExecute(arguments);
					break;
				case "newuser": /* NEWUSER Command */
					newuser.setParametersAndExecute(arguments);
					break;
				case "userinfo": /* USERINFO Command */
					userinfo.setParametersAndExecute(arguments);
					break;
				case "showusers": /* SHOWUSERS Command */
					showusers.setParametersAndExecute(arguments);
					break;
				case "login": /* LOGIN Command */
					login.setParametersAndExecute(arguments);
					break;
				case "logout": /* LOGOUT Command */
					logout.setParametersAndExecute(arguments);
					break;
				case "man": /* MAN Command */
					man.setParametersAndExecute(arguments);
					break;
				case "sync": /* SYNC Command */
					AAACloud.syncExecute(arguments);
					break;
				case "upload": /* UPLOAD Command */
					AAACloud.uploadExecute(arguments);
					break;
				case "cloudclear": /* CLEAR CLOUD Command */
					AAACloud.cloudClear();
					break;
				case "cloudspace": /* CLOUD SPACE Command */
					AAACloud.cloudSpace();
					break;
				case "edit": /* EDIT FILE Command */
					editfile.setParametersAndExecute(arguments);
					break;
				case "exit": /* Exit program and write logs to file */
					scanner.close();
					prepareEnvironment.systemLogger.writeToFile();
					System.exit(0);
				default:
					prepareEnvironment.myGUI.addText("Command not found.");
			}
			/* Update panel's scrollbar */
			JScrollBar vertical = prepareEnvironment.myGUI.historyConsoleScroll.getVerticalScrollBar();
			vertical.setValue(vertical.getMaximum());
			input = scanner.nextLine();
		}
	}
}
