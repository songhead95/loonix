package loonix.commands;

import loonix.core.*;

public class Cat implements Command {

	public void run(Stdin in, Stdout out, Stderr err, String rawCommand,
			String[] Argv) {
		if (Argv.length > 1) {
			Argv[1] = loonix.resolvePath(Argv[1]);
			if (loonix.fs.fileExists(Argv[1])) {
				out.print(loonix.fs.fsIndex.get((Argv[1])) + "\n");
			} else {
				out.print("error: file not found\n");
			}
		} else {
			out.print("Usage: cat [file]\n");
		}
	}

}
