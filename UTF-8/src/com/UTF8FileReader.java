package com;


import java.io.IOException;
import com.filereader.Input;
import com.filereader.InputFile;

public class UTF8FileReader {
	
	static byte[] spaces = { (byte) ' ', (byte) ' ', (byte) ' ', (byte) ' ' };
	
	public static void printblanks(int blanks) {
		if ( spaces.length < blanks ) {
			spaces = new byte[blanks << 1];
			for ( int i = 0; i < spaces.length; i++ ) spaces[i] = ' ';
		}
		System.out.print(new String(spaces, 0, blanks));
	}

	public static void main(String[] args) throws IOException {
//		infile.setFile("../output/UTF8.txt");
		infile.setFile("./UTF8.txt");
		in = infile;
		while ( in.nextword() != -1) {
			printblanks(in.getnblanks());
			System.out.print(in.getWord());
		}

	}
	
	static Input in;
	static InputFile infile = new InputFile();

}




