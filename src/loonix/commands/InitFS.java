package loonix.commands;

import loonix.core.*;

public class InitFS implements Command {

	public void run(Stdin in, Stdout out, Stderr err, String rawCommand,
			String[] Argv) {
		loonix.fs.initFS();
	}

}
