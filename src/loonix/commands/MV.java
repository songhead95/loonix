package loonix.commands;

import loonix.core.*;

public class MV implements Command {

	public void run(Stdin in, Stdout out, Stderr err, String rawCommand,
			String[] Argv) {
		if (Argv.length > 2) {
			Argv[1] = loonix.resolvePath(Argv[1]);
			Argv[2] = loonix.resolvePath(Argv[2]);
			if (loonix.fs.fileExists(Argv[1])) {
				loonix.fs.writeFile(Argv[2], loonix.fs.fsIndex.get(Argv[1]));
				loonix.fs.removeFile(Argv[1]);
			} else {
				out.print("error: file not found\n");
			}
		} else {
			out.print("Usage: mv [file] [destination]\n");
		}

	}

}
