package loonix.core;
import java.awt.List;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
@SuppressWarnings("unused")

public class filesystem {
	public  String logbuffer = "";
	public HashMap <String,String> fsIndex = new HashMap<String,String>();	
	String CurrentDir = "/";
	public String[] Dirs = new String[8];
	String currentKey;
	int i = 0;
	int i2 = 0;
	String FS[] = new String[100];

	public filesystem() {
		initFS();
	}
	public void moveDir(String Path, String NewPath) {
		copyDir(Path, NewPath);
		removeDir(Path);
	}
	public boolean copyDir(String Path, String NewPath) {
		String Dir = getParentDir(Path + "/");
		String Name = getName(Path);
		String NewDir = getParentDir(NewPath + "/");
		String NewName = getName(NewPath.substring(0,NewPath.length()-2));
		makeDir(NewDir + NewName);
		String Swap;
		Set <String> files = fsIndex.keySet();
		Object[] names = files.toArray();
		if(fsIndex.containsKey(Path)) {
			for (int i = 0; i < names.length; i++) {
				String o = "$" + names[i];
				if(o.contains("$" + Path.substring(0, Path.length()-1))) {
					Swap=fsIndex.get(o.substring(1));
					o = o.replaceAll(Path.substring(1, Path.length()-1), NewPath.substring(1, NewPath.length()-1));
					fsIndex.put(o.substring(1), Swap);
				}
			}
			fsIndex.put(NewPath, fsIndex.get(Path));
			fsIndex.put(getParentDir(NewPath)+"$", fsIndex.get(getParentDir(NewPath)+"$") +" "+ NewName+"/");
		}
		return false;
	}
	
	public boolean writeFile(String Path, String Contents) { //Adds a file to the system
		String Dir = getParentDir(Path + "/");
		String Name = getName(Path);
		if(fsIndex.containsKey(Dir +"$")) {
			if ( ! fsIndex.containsKey(Dir + Name)) {
				fsIndex.put(Dir +"$", fsIndex.get(Dir +"$") +" " +Name + " ");
			}
			fsIndex.put(Dir +Name, Contents);
			return false;
		} else { return true;}
	}

	public boolean touchFile(String Path) {
		String Dir = getParentDir(Path + "/");
		String Name = getName(Path);
		if(fsIndex.containsKey(Dir +"$")) {
			fsIndex.put(Dir +Name, " ");
			fsIndex.put(Dir +"$", fsIndex.get(Dir +"$") +Name + " ");
			return false;
		} else { return true;}
	}

	public void removeFile(String Path) {
		log(Path);
		if (fsIndex.containsKey(Path)){	
			String s = getParentDir(Path + "/");
			fsIndex.put(s + "$", fsIndex.get(s + "$").replaceAll(" "+getName(Path)+" ", " "));
			fsIndex.remove(Path);
		}
	}

	public boolean makeDir(String Path) { 
		String Dir = getParentDir(Path) + "$";
		String Name = getName(Path) + "/";
		if(fsIndex.containsKey(Dir)) {
			fsIndex.put(Dir.substring(0, Dir.length() - 1) +Name + "$", "_g " );
			fsIndex.put(Dir, fsIndex.get(Dir) +Name + " ");
			return false;
		} else { return true;}
	}

	public void removeDir(String Path) {		
		log(Path);
		if (fsIndex.containsKey(Path)){
			getName(Path.substring(0, Path.length()-1));
			String s = getParentDir(Path);
			fsIndex.put(s + "$", fsIndex.get(s + "$").replaceAll((" "+Path.substring(1, Path.length()-1)+" "), " "));
			fsIndex.remove(Path);	
			log(fsIndex.get(s + "$").replaceAll((Path.substring(1, Path.length()-1))+" ", " "));
		}
	}
	
	public String readFile(String Dir, String Name) {
		if(fsIndex.containsKey(Dir +"$")){
			return fsIndex.get(Dir +Name );
		} else { return "FILE ERROR\n"; }
	}
	
	public String listDir(String Dir) {
		log(Dir +"$" );
		if(fsIndex.containsKey(Dir +"$")){
			return fsIndex.get(Dir +"$" ).substring(2);
		} else { return "Directory not found \n"; }
	}
	
	public boolean fileExists(String Path) {
		return (fsIndex.containsKey(Path));
	}
	
 	public boolean dirExists(String Path) {
		return ( fsIndex.containsKey(Path + "$") );
	}
	
	public String getParentDir(String dir) {
		String parentDir = "";
		String aDir[] = dir.substring(0, dir.length()-1).split("/");
		for(int z = 0; z != aDir.length - 1; ++z) {
			parentDir = parentDir  +aDir[z] +"/";
		}
		return parentDir;
	}
	
	public String getDirectory(String file) {
		String dir = "";
		String aDir[] = file.split("/");
		for(int z = 0; z != aDir.length - 1; ++z) {
			dir = dir  +aDir[z] +"/";
		}
		return dir.substring(0, dir.length()-1);
	}
	
	
	public String getName(String Path) {
		String aDir[] = Path.split("/");
		return aDir[aDir.length-1];
	}
	
	public void log(String msg) {
		logbuffer = logbuffer + msg + '\n';
	}
	
	public String exportFS ( ) {
		String fsImage = "";
		Set<String> names =  fsIndex.keySet();
		for (Iterator<String> iterator = names.iterator(); iterator.hasNext();) {
			Object o = iterator.next();
			fsImage = fsImage + (o + "\n" + (fsIndex.get(o).replaceAll("\n", "(newline)") + "\n"));
			}
		return fsImage;
	}
	
	public void importFS(String fsImage) {
		fsIndex.clear();
		String files[] = fsImage.split("\n");
		String fname = "";
		String fcontent = "";
		i = 0;
		while(i < files.length - 1) {
			fname = files[i];
			i++;
			fcontent = files[i];
			i++;
			fsIndex.put(fname, fcontent);
		}
	}
	
	public void initFS() {
		fsIndex.put("/bin/ls", "primitive");
		fsIndex.put("/home/documents/readme.txt", "You read me!\n");
		fsIndex.put("/home/documents/dummy.txt", "Hello World!\n");
		fsIndex.put("/home/documents/dummy2.txt", "In soviet russia, world greets you!\n");
		fsIndex.put("/bin/helloworld", ">+++++++++[<++++++++>-]<.>+++++++[<++++>-]<+.+++++++..+++.[-]>++++++++[<++++>-] <.>+++++++++++[<++++++++>-]<-.--------.+++.------.--------.[-]>++++++++[<++++>- ]<+.[-]++++++++++.");
		fsIndex.put("/dev/hda1", " ");
		fsIndex.put("/dev/stdin", " ");
		fsIndex.put("/dev/stdout", " ");
		fsIndex.put("/dev/stderr", " ");    
		fsIndex.put("/dev/random", " ");
		fsIndex.put("/dev/zero", " ");
		fsIndex.put("/dev/memory", " ");
		//Declare Directories
		fsIndex.put("/$", "_ dev/ home/ root/ bin/ ");
		fsIndex.put("/dev/$", "_ hda1 memory random stdin stdout stderr zero ");
		fsIndex.put("/root/$", "_ documents/ ");
		fsIndex.put("/bin/$", "_ helloworld ");
		fsIndex.put("/home/$", "_ documents/ ");
		fsIndex.put("/home/documents/$", "_ readme.txt dummy.txt dummy2.txt ");
		fsIndex.put("/root/documents/$", "_ ");
		}
	
}


