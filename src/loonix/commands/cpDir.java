package loonix.commands;

import loonix.core.*;

public class cpDir implements Command {

	public void run(Stdin in, Stdout out, Stderr err, String rawCommand,
			String[] Argv) {
		if (Argv.length > 2) {
			Argv[1] = loonix.resolvePath(Argv[1]);
			Argv[2] = loonix.resolvePath(Argv[2]);
			if (!(Argv[1].charAt(Argv[1].length() - 1) == '/'))
				Argv[1] = Argv[1] + '/';
			if (!(Argv[2].charAt(Argv[2].length() - 1) == '/'))
				Argv[2] = Argv[2] + '/';
			Argv[1] = Argv[1] + "$";
			Argv[2] = Argv[2] + "$";
			loonix.fs.copyDir(Argv[1], Argv[2]);
		} else {
			out.print("usage: cpdir [dir] [destination]\n");
		}
	}

}
