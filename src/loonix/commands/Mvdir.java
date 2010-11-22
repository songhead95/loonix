package loonix.commands;

import loonix.core.Stderr;
import loonix.core.Stdin;
import loonix.core.Stdout;

public class Mvdir implements Command {

	public void run(Stdin in, Stdout out, Stderr err, String rawCommand,
			String[] Argv) {
		String[] ArgPass = { Argv[0], Argv[1] };
		new cpDir().run(null, null, null, "", Argv);
		new Rmdir().run(null, null, null, "", ArgPass);
	}

}
