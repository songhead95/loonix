package loonix.commands;

import loonix.core.*;

public class Chmod implements Command {

	public void run(Stdin in, Stdout out, Stderr err, String rawCommand,
			String[] Argv) {
		if (Argv.length > 2) {
			if (Argv[1].equals("+")) {
				Argv[2] = loonix.resolvePath(Argv[2]);
				loonix.commands.put(loonix.fs.getName(Argv[2]), new BFCommand(
						Argv[2]));
			} else {
				loonix.commands.remove(loonix.fs.getName(Argv[2]));
			}
		} else {
			out.print("Error: invalid arguments\n");
		}
	}
}