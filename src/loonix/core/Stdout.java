package loonix.core;

public class Stdout {
	String pipe = "";
	String ptype= "";
	public Stdout(String file) {
		pipe=file;
		if(pipe.equals("/dev/stderr") || pipe.equals("/dev/stdout")) {
			ptype="normal";
		} else if(pipe.equals("/dev/null")) {
			ptype="null";
		} else {
			ptype="file";
		}
	}
	
	public void print(String out) {
		String fwrite = "";
		if(ptype.equals("null")); //if the file is /dev/null, then do nothing
		if(ptype.equals("normal"))loonix.term.print(out); //
		if(ptype.equals("file")) {
			loonix.fs.touchFile(loonix.resolvePath(pipe));
			fwrite=loonix.fs.fsIndex.get(loonix.resolvePath(pipe));
			loonix.fs.fsIndex.put(loonix.resolvePath(pipe), fwrite + out);
		}
	}
}
