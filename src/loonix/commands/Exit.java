package loonix.commands;

import loonix.core.*;

public class Exit implements Command {

	public void run(Stdin in, Stdout out, Stderr err, String rawCommand,
			String[] Argv) {
		new ExportFS().run(in, new Stdout("/dev/stdout"), err, "", new String[1]);
		System.exit(0);
	}

}
