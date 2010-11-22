package loonix.commands;

import loonix.core.*;

public class BFCommand implements Command {
	String code = "";
	String mypath = "";

	public BFCommand(String path) {
		mypath = path;
	}

	public void run(Stdin in, Stdout out, Stderr err, String rawCommand,
			String[] Argv) {
		code = loonix.fs.fsIndex.get(loonix.resolvePath(mypath));
		loonix.mybfi.init(3000);
		loonix.mybfi.setProgram(code);
		loonix.mybfi.start(in, out, err);
	}
}
