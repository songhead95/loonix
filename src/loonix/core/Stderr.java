package loonix.core;

public class Stderr {
	String pipe = "";
	String ptype= "";
	public Stderr(String file) {
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
		if(ptype.equals("null")); //if the file is /dev/null, then do nothing
		if(ptype.equals("normal"))loonix.term.print(out); //
		if(ptype.equals("file")); 
	}

}
