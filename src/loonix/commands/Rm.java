package loonix.commands;

import loonix.core.*;

public class Rm implements Command {

	public void run(Stdin in, Stdout out, Stderr err, String rawCommand,
			String[] Argv) {
		if (Argv.length > 1) {
			Argv[1] = loonix.resolvePath(Argv[1]);
			if (loonix.fs.fileExists(Argv[1])) {
				loonix.fs.removeFile(Argv[1]);
			} else {
				out.print("error: file not found\n");
			}
		} else {
			out.print("Usage: rm [file]\n");
		}

	}

}
