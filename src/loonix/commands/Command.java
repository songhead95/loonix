package loonix.commands;

import loonix.core.*;

public interface Command {
	public void run(Stdin in, Stdout out, Stderr err, String rawCommand,
			String[] Argv);
}
