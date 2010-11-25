package loonix.core;
import java.io.IOException;
import java.io.InputStream;

public class Stdin {
	public InputStream is = System.in;
	String pipe = "";
	String ptype= "";
	int readBuffer = 0;
	public Stdin(String file) {
		pipe=file;
		if(pipe.equals("/dev/stdin")) {
			ptype="normal";
		} else if(pipe.equals("/dev/null")) {
			ptype="null";
		} else {
			ptype="file";
		}

	}
	
	public int read() throws IOException {
		int x = 0;
		if(ptype.equals("normal"))
			x= is.read();
		if(ptype.equals("null"));
		if(ptype.equals("file")) {
			String file = loonix.fs.fsIndex.get(loonix.resolvePath(pipe));
			if(readBuffer >= file.length() )
				x = 0;
			else
				x = file.charAt(readBuffer++);
		}
		return x;
	}
}
