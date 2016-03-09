package repositories;

import commands.Command;

/* Repository interface */
public interface Repository {
	public void accept(Command com);
}
