package loonix.core;
import loonix.commands.*;

import java.util.HashMap;
public class loonix{
	public static tty term = new tty();          /* Comment this line out to run the os in graphic mode */
	//static Terminal term = new Terminal();  /* Comment this line out to run the os in text based mode */

	public static bfi mybfi = new bfi();
	public static int loggedIn=0;
	public static String rawCommand = "";
	public static String command = "";
	public static String PWD = "/";
	public static String Argv[] = new String[10];
	public static String passwd = "";
	public static String prompt = "";
	public static filesystem fs = new filesystem();
	public static HashMap <String,Command> commands = new HashMap<String,Command>();
	public static String logbuffer = "";

	public static void main(String args[]) {

		term.setVisible(true);

		buildCommands();
		login();

		while (loggedIn != 0){
			rawCommand = term.getInput();
			paseCommand();
		}
	}    

	private static void paseCommand() {
		Stdin inpass = new Stdin("/dev/stdin");
		Stdout outpass = new Stdout("/dev/stdout");
		Stderr errpass = new Stderr("/dev/stderr");
		if(rawCommand.contains(" > ")) {
			outpass = new Stdout(rawCommand.split(" > ")[1]);
			rawCommand = rawCommand.split(" > ")[0];
		}
		if(rawCommand.contains(" < ")) {
			inpass = new Stdin(rawCommand.split(" < ")[1]);
			rawCommand = rawCommand.split(" < ")[0];
		}
		if(rawCommand.contains(" 2> ")) {
			errpass = new Stderr(rawCommand.split(" 2> ")[1]);
			rawCommand = rawCommand.split(" 2> ")[0];
		}
		Argv = rawCommand.split(" ");
		command = Argv[0];
		term.setPrompt(prompt);		
		log("command entered: " + rawCommand);
		if (commands.containsKey(command)) {
		commands.get(command).run(inpass, outpass, errpass, rawCommand, Argv);
		} else {
			term.print(command + ": command not found\n");
		}		
	}

	public static void buildCommands() {
		commands.put("ls", new Ls());
		commands.put("echo", new Echo());
		commands.put("write", new Write());
		commands.put("bfc", new Chmod());
		commands.put("exit", new Exit() );
		commands.put("uname", new Print("Loonix\n"));
		commands.put("pwd", new Pwd());
		commands.put("sh", new Print(""));
		commands.put("cd", new Cd());
		commands.put("bfi", new Bfi());
		commands.put("cat", new Cat());
		commands.put("cd", new Cd());
		commands.put("clear", new Clear());
		commands.put("exit", new Exit());
		commands.put("mkdir", new Mkdir());
		commands.put("rm", new Rm());
		commands.put("rmdir", new Rmdir());
		commands.put("touch", new Touch());
		commands.put("log", new Log());
		commands.put("exportfs", new ExportFS());
		commands.put("importfs", new ImportFS());
		commands.put("initfs", new InitFS());
		commands.put("mv", new MV());
		commands.put("cp", new CP());
		commands.put("cpdir", new cpDir());
		commands.put("mvdir", new Mvdir());
		commands.put("", new Print(""));
	}
	
	public static void importCommands() {
		String importCommands[] = loonix.fs.fsIndex.get("/bin/$").substring(2).split(" ");
		for(int i = 0; i < importCommands.length; i++) {
			commands.put(importCommands[i], new BFCommand("/bin/" + importCommands[i]));
		}
	}

	public static void login() {
		term.setPrompt("load filesystem? (Y/n) ");
		if( ! term.getInput().contains("n"))
			new ImportFS().run(null, null, null,"", new String[1]);
		while (loggedIn < 1) {
			term.setPrompt("Login:");
			String userName = term.getInput();
			if ( userName.equals("root")) {
				term.setPrompt("Password:");
				passwd = term.getInput();
				if ( passwd.equals("12345") ) { 	
					term.print("logged into root\n");
					log("root login");
					prompt= "root#";
					loggedIn= 2;
				}
			} else {
				term.print("Sucessfully logged in as " +userName +"\n");
				prompt=userName + "$";
				loggedIn = 1;
			}
			term.setPrompt(prompt);
			importCommands();
		}
	}

	public static String resolvePath(String Path) { // equates local files/directories to global ones
		if ( Path.substring(0, 1).equals("/") ) {
			return Path;
		} else {
			return PWD + Path;
		}
	}
	

	public static void log(String contents) {
		logbuffer = logbuffer + contents + '\n';
	}
}

