package loonix.commands;

import loonix.core.*;

import java.io.*;

public class ExportFS implements Command {
	FileOutputStream fsout;
	PrintStream fsps;
	FileOutputStream bfout;
	PrintStream bfps;

	public void run(Stdin in, Stdout out, Stderr err, String rawCommand,
			String[] Argv) {
		if (Argv.length > 1) {
			out.print(loonix.fs.exportFS());
		} else {
			try {
				fsout = new FileOutputStream("loonix.img");
				fsps = new PrintStream(fsout);
				fsps.print(loonix.fs.exportFS());
				fsps.close();
			} catch (Exception e) {
				loonix.log("Error exporting filesystem.\n");
			}

		}
	}
}
