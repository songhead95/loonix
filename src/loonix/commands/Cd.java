package loonix.commands;

import loonix.core.*;

public class Cd implements Command {

	public void run(Stdin in, Stdout out, Stderr err, String rawCommand,
			String[] Argv) {
		if (Argv.length > 1) {
			if (!Argv[1].substring(Argv[1].length() - 1).equals("/")) {
				Argv[1] = Argv[1] + "/";
			}
			if (Argv[1].equals("../")) {
				loonix.PWD = loonix.fs.getParentDir(loonix.PWD);
			} else if (Argv[1].equals("~/")) {
				if (loonix.loggedIn == 1) {
					loonix.PWD = "/home/";
				} else {
					loonix.PWD = "/root/";
				}
			} else if (Argv[1].substring(0, 1).equals("/")) {
				if (loonix.fs.dirExists(Argv[1])) {
					loonix.PWD = Argv[1];
				} else {
					out.print("Directory not existant\n");
				}
			} else {
				if (loonix.fs.dirExists(loonix.PWD + Argv[1])) {
					loonix.PWD = loonix.PWD + Argv[1];
				} else {
					out.print("Directory not existant\n");
				}
			}
		}
	}

}
