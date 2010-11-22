package loonix.commands;

import loonix.core.*;

public class Log implements Command {

	public void run(Stdin in, Stdout out, Stderr err, String rawCommand,
			String[] Argv) {
		if (Argv.length > 1) {

			if (Argv[1].equals("fs")) {
				out.print(loonix.fs.logbuffer);
			} else if (Argv[1].equals("bf")) {
				out.print(loonix.mybfi.logbuffer);
			} else if (Argv[1].equals("system")) {
				out.print(loonix.logbuffer);
			}
		}

	}
}
