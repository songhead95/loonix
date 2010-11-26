package loonix.core;

import java.io.PrintStream;
import java.io.InputStream;



/** This class is a brainfuck interpreter written by Sven Stucki.<br>
 *  It lets you execute a bf program step-by-step for easy debugging or visualization or it can interpret the whole program at once.<br>
 *  Release date: 2.7.2009
 *  @author Sven Stucki
 */

public class bfi {

	public String logbuffer = "";
	public Stdin in;
	public Stdout out;
	public Stderr err;
	

	/** Output of the brainfuck programm goes here, default = System.out **/
	public PrintStream ps;  // Output stream
	/** Input of the brainfuck programm is read from here, default = System.in **/
	public InputStream is;  // Input stream

	/** This points to the currently selected memory cell **/
	public int mp;          // Memory pointer
	/** This array represents the memory of the brainfuck program **/
	public int[] cell;      // Memory

	/** This is the index of the char in the code, which will be executed next **/
	private int cp;         // Code pointer
	/** The code is stored in this String **/
	private String cmd;     // Code


	/** This String contains all in the code allowed chars **/
	public final String CHARS = "<>+-[].,";


	/** Standard constructor, sets everything to default **/
	public bfi() {
		init( 30000 );
	}
	/** With this constructor you can specify the number of memory cells to provide, I/O uses default **/
	public bfi( int cnt ) {
		init( cnt );
	}
	/** This constructor lets you specify the number of memory cells, and the I/O Streams **/
	public bfi( int cnt, PrintStream p, InputStream i ) {
		ps = p;
		is = i;
		init( cnt );
	}


	/** Initialises / Resets the interpreter **/

	public void init( int cnt ) {
		cell = new int[cnt];
		mp = 0;
		if( cmd==null )
			cmd = "";
		if( ps==null )
			ps = System.out;
		if( is==null )
			is = System.in;
	}


	/** Resets the memory field and the pointers **/

	public void reset() {
		cell = new int[cell.length];
		mp = 0;
		cp = 0;
	}


	/** Set In- / PrintStream **/

	public void setInputStream( InputStream ips, PrintStream ops ) {
		is = ips;
		ps = ops;
	}


	/** Usable for running multiple programs in the same environment **/

	public void setPointer( int pos ) {
		mp = pos;
	}

	/** Loads brainfuck program, filters every char not in the CHARS constant or after a \ out of the String **/

	public void setProgram( String prg ) {

		boolean take = false;
		cmd = "";
		for( int i=0; i<prg.length(); i++ ) {
			if( take ) {
				cmd += prg.charAt(i);
				take = false;
			} else {
				if( CHARS.indexOf( prg.charAt(i) ) != -1 ) {
					cmd += prg.charAt(i);
				} else if( prg.charAt(i) == '\\' ) {
					cmd += "\\";
					take = true;
				}
			}
		}

		log( "Parsed program: " + cmd );

	}


	/** Returns next char **/

	public char getChar() {
		return getChar( this.cp );
	}

	/** Returns char from command string at specified position **/

	public char getChar( int nr ) {
		return cmd.charAt( nr );
	}


	/** Get a slice of the programm **/

	public String getChars( int start, int end ) {

		String tmp = "";
		for( int i=start-1; i<end; i++ ) {
			tmp += cmd.charAt(i);
		}
		return tmp;

	}


	/** Interpret program, set start address **/ 

	public boolean interpret( int spos ) {
		this.cp = spos;
		return interpret();
	}

	/** Start interpretation of programm **/

	public boolean interpret() {

		char inst;

		// Fetch next instruction
		try {
			inst = getChar();
		} catch( Exception e ) {
			return false;
		}

		// Parse instruction
		switch( inst ) {
		case '<':
			mp = (mp>0) ? mp-1 : 0;
			break;
		case '>':
			mp = (mp<cell.length) ? mp+1 : mp;
			break;
		case '+':
			cell[mp]++;
			break;
		case '-':
			cell[mp]--;
			break;
		case '[':
			if( cell[mp] == 0 )
				return jumpWhileEnd();
			break;
		case ']':
			return jumpWhileStart();
			// break;
		case '\\':
			try {
				cell[mp] = getChar( cp+1 );
				cp++;
			} catch( Exception e ) {
				return false;
			}
			break;
		case '*':
			if( cell[mp]==13 || cell[mp]==10){
				err.printc("");
			} else {err.printc(""+cell[mp]);
			}
		case '.':
			if( cell[mp]==13 || cell[mp]==10 ) {
				// Printing out char 13 or 10 causes an exception (don't know why)
				out.print( "" );
			} else {
				out.printc( (char)cell[mp] );
			}
			break;
		case ',':
			try {
				cell[mp] = in.read();
			} catch( Exception e ) {
				return false;
			}
			break;
		}
		cp++;

		return true;

	}


	/** This method searches the corresponding [ sign **/

	public boolean jumpWhileStart() {

		int level = 0;

		for( int i=cp-1; i>=0; i-- ) {
			switch( cmd.charAt(i) ) {
			case '[':
				if( level > 0 ) {
					level--;
				} else {
					cp = i;
					return true;
				}
				break;
			case ']':
				level++;
				break;
			}
		}
		return false;

	}

	/** This method searches trailing ] sign **/

	public boolean jumpWhileEnd() {

		int level = 0;

		for( int i=cp+1; i<cmd.length(); i++ ) {
			switch( cmd.charAt(i) ) {
			case '[':
				level++;
				break;
			case ']':
				if( level <= 0 ) {
					cp = i+1;
					return true;
				} else {
					level--;
				}
				break;
			}
		}
		return false;

	}


	/** This method goes through the whole code **/

	public void start(Stdin input, Stdout output, Stderr error) {
		in=input;
		out=output;
		err=error;
		start(0);
	}
	


	/** This method goes through the whole code starting at a certain point in the code **/

	public void start( int off ) {

		if( interpret(off) ) {
			while( interpret() );
			out.printc(-1);
		}

		try {
			log( "Programm terminated at instructions: " + getChars( cp-1, cp+2 ) );
			log( "Code before exception: " + getChars( 1, cp+2 ) );
		} catch( Exception e ) {
			log( "Programm terminated. " );
			if( cp >= cmd.length() ) {
				log( "(End of Program)" );
			} else {
				log( "(Exception, no debuggin info available)" );
			}
		}

	}



	/** If class is run directly, execute the argument as brainfuck program **/

	public static void main( String[] args ) {

		if( args.length < 1 ) {

			System.out.println( "Usage: " );
			System.out.println( "java bfi <brainfuck program>" );
			System.out.println();
			System.exit( -1 );

		}

		bfi bfi = new bfi();
		bfi.setProgram( args[0] );
		bfi.start(0);

		System.out.println();

	}

	public void log(String msg) {
		logbuffer = logbuffer + msg + "\n";
	}

}
