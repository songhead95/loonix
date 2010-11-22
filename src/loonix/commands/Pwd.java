package loonix.commands;

import loonix.core.*;

public class Pwd implements Command {

	public void run(Stdin in, Stdout out, Stderr err, String rawCommand,
			String[] Argv) {
		out.print(loonix.PWD + '\n');
	}

}
