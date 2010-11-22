package loonix.commands;

import loonix.core.*;

public class Touch implements Command {

	public void run(Stdin in, Stdout out, Stderr err, String rawCommand,
			String[] Argv) {
		if (Argv.length > 1) {
			Argv[1] = loonix.resolvePath(Argv[1]);
			loonix.fs.touchFile(Argv[1]);

		} else {
			out.print("Usage: touch [file]\n");
		}

	}

}
