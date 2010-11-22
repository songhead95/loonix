package loonix.commands;

import loonix.core.*;

public class Mkdir implements Command {

	public void run(Stdin in, Stdout out, Stderr err, String rawCommand,
			String[] Argv) {
		if (Argv.length > 1) {
			if (Argv[1].substring(Argv[1].length() - 1) != "/")
				Argv[1] = Argv[1] + "/";
			Argv[1] = loonix.resolvePath(Argv[1]);
			loonix.fs.makeDir(Argv[1]);
		} else {
			out.print("Usage: mkdir [dir]\n");
		}
	}

}
