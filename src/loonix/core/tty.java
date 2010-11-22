package loonix.core;
import java.io.IOException;
import java.util.Scanner;
public class tty {
	String prompt = "";
	final static String ESC = "\033[";
	void setVisible(boolean x){
		//put in here for compatibility with the swing based terminal
	}
	public void print(String X) {
		System.out.print(X);
	}
	public void clearScreen() throws IOException {
		Runtime.getRuntime().exec("clear");
	}
	public void setPrompt(String P) {
		prompt = P;
	}

	public String getInput() {
		System.out.print(prompt + " ");
		return (new Scanner(System.in)).nextLine();

}
}