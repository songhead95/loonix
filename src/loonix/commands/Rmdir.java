package loonix.commands;

import loonix.core.*;

public class Rmdir implements Command {

	public void run(Stdin in, Stdout out, Stderr err, String rawCommand,
			String[] Argv) {
		if (Argv.length > 1) {
			if (Argv[1].substring(Argv[1].length() - 1) != "/") {
				Argv[1] = Argv[1] + "/";
			}
			Argv[1] = loonix.resolvePath(Argv[1]);
			if (loonix.fs.fileExists(Argv[1] + "$")) {
				loonix.fs.removeDir(Argv[1] + "$");
			} else {
				out.print("FILE NOT FOUND\n");
			}
		} else {
			out.print("Usage: rmdir [directory]\n");
		}
	}

}
