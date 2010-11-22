package loonix.commands;

import loonix.core.*;

public class Print implements Command {
	String message = "";

	public Print(String msg) {
		message = msg;
	}

	public void run(Stdin in, Stdout out, Stderr err, String rawCommand,
			String[] Argv) {
		out.print(message);
	}

}
