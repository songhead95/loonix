package loonix.commands;

import loonix.core.*;

public class Ls implements Command {

	public void run(Stdin in, Stdout out, Stderr err, String rawCommand,
			String[] Argv) {
		if (Argv.length > 1) {
			if (Argv[1].charAt(Argv[1].length() - 1) != '/')
				Argv[1] = Argv[1] + "/";
			Argv[1] = loonix.resolvePath(Argv[1]);
			out.print(loonix.fs.fsIndex.get(Argv[1] + '$').substring(2) + "\n");
		} else {
			out.print(loonix.fs.listDir(loonix.PWD) + "\n");
		}
	}

}
