package loonix.commands;

import loonix.core.*;

public class Echo implements Command {

	@Override
	public void run(Stdin in, Stdout out, Stderr err, String rawCommand,
			String[] Argv) {
		out.print(rawCommand.substring(5) + "\n");
	}

}
