package commands;

import repositories.*;

/* Command interface */
public interface Command {
	public void execute(RepositoryClass rep);
	public void execute(Directory dir);
	public void execute(File file);
}
