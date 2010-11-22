package loonix.commands;

import loonix.core.*;

public class Write implements Command {

	public void run(Stdin in, Stdout out, Stderr err, String rawCommand,
			String[] Argv) {
		if (Argv.length > 2) {
			String p = loonix.resolvePath(Argv[1]);
			loonix.fs.writeFile(p, rawCommand.substring(7 + Argv[1].length()));
		} else {
			out.print("Usage: write [file] [contents]\n");
		}
	}

}
