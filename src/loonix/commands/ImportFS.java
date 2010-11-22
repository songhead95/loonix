package loonix.commands;

import loonix.core.*;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class ImportFS implements Command {

	String Image = "";

	@SuppressWarnings("deprecation")
	public void run(Stdin in, Stdout out, Stderr err, String rawCommand,
			String[] Argv) {

		File file = new File("loonix.img");
		FileInputStream fis = null;
		BufferedInputStream bis = null;
		DataInputStream dis = null;

		try {

			fis = new FileInputStream(file);

			// Here BufferedInputStream is added for fast reading.
			bis = new BufferedInputStream(fis);
			dis = new DataInputStream(bis);

			// dis.available() returns 0 if the file does not have more lines.
			while (dis.available() != 0) {

				// this statement reads the line from the file and adds it to
				// the fs image
				Image = Image + (dis.readLine());
				Image = Image + "\n";
			}

			// dispose all the resources after using them.
			fis.close();
			bis.close();
			dis.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		loonix.fs.log("Imported FileSystem: \n" + Image);
		loonix.fs.importFS(Image);

	}

}
