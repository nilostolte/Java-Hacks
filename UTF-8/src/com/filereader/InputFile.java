package com.filereader;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.Charset;

import com.list.FastByteArrayList;

/**
 * 
 * <dd><b><code>InputFile</code></b> is an infinite source of input of bytes from 
 * a file. It repeatedly reads to a buffer, skipping blanks (here blank is considered
 * a word separator - another word separator can be configured). It
 * then scans the file while it contains non blank characters. If the buffer was 
 * totally scanned without finding a blank the word continues in the contents 
 * still not read in the file. if the file ended the word from the point it started scanning is saved in a
 * an Arraylist of bytes. It then reads the file to fill the buffer again and continues 
 * scanning in this manner until a blank character is found, when the cycle starts
 * over. The number of blanks skipped is maintained, in case an exact copy of the 
 * file is required. <br><br>
 * This class is supposed to be accessed through the interface <b><i>Input</i></b>
 * to be able to scan different sources of bytes. The public methods of this class 
 * are the following:<br><br>
 *
 * <ul> 
 * 		<li><b><code>{@link #setFile(String file)}</code></b>
 * 		<li><b><code>{@link #nextword()}</code></b>
 * 		<li><b><code>{@link #getWord()}</code></b>
 * 		<li><b><code>{@link #getnblanks()}</code></b>
 * 		<li><b><code>{@link #end()}</code></b>    
 * </ul>
 * 
 * Most of the work is done by the private method:
 * <ul> 
 * 		<li><b><code>{@link #readword()}</code></b>  
 * </ul> 
 * @author Nilo Stolte
 * </dd>
 */
public class InputFile implements Input {
	private int position;
    private int init;
	private FileInputStream filein;
	private final int buffer_size = 1024;
	private byte[] buffer = new byte[buffer_size];
	private int nbytes;
	private Charset UTF8;
	private int nblanks;
	private FastByteArrayList rest = new FastByteArrayList(200);
	private boolean end;
	
	/**
	 * <ul>
	 * This method passes a string containing the name of the file to be read<br><br>
	 * @param file - string containing the name of the file to be read<br><br>
	 * @throws IOException
	 * </ul>
	 */
	public void setFile(String file) throws IOException {
		position = 0;
		UTF8 = Charset.forName("UTF-8");
		filein = new FileInputStream(file);
		nbytes = filein.read(buffer);
	}

	/**<ul>
	 * This method just calls <b><code>{@link #readword()}</code></b>. If
	 * it reads a word it returns the length of the word read, otherwise it
	 * returns -1.
	 * <br><br>
	 * 
	 * @return the length of the word read or -1 in the case of end of file
	 * </ul>
	 */
	@Override
	public int nextword() {
		if (!readword()) return -1;
		if (init == -1) return rest.length();
		return position - init;
	}

	/**<ul>Once the word is read with the method 
	 * <b><code>{@link #nextword()}</code></b> this method returns the word read
	 * <br><br>
	 * @return a string containing the word read
	 * </ul>
	 */
	@Override
	public String getWord() {
		if (init == -1) return rest.toString();
		return new String(buffer, init, position - init, UTF8);
	}
	
	/**<ul>This is the method that really reads the file and performs what is 
	 * described here: {@link #InputFile}<br><br>
	 * @return a <code>true</code> if a word was read or <code>false</code> if the 
	 * file ended 
	 * </ul>
	 */
	private boolean readword() {
		if ( end = (nbytes == -1) ) return false;
		int i, j;
		nblanks = 0;						// there were no blanks skipped yet
		rest.clear();						// list should be empty here
		//
		// Loops while reading buffer, jumping over all blanks
		//
		for ( ; ; ) {						// loops while it finds blanks
			// first skip all the blanks already read
			for ( i = position; i < nbytes && buffer[i] == ' '; i++ );
			nblanks += i - position;		// records number of blanks skipped
			if (i < nbytes) break;			// if still inside the buffer, get out
			try {							// otherwise read the buffer from file
				nbytes = filein.read(buffer);
				if (end = (nbytes == -1))	// end of file -> return false
					return false;
			} catch (IOException e) {		// on a exception, call exit
				e.printStackTrace();
				System.exit(0);
			}
			position = 0;					// starts from beginning of buffer again
		}									// go back and continue checking new buffer
		//
		// Loops while reading buffer, jumping over everything that is not blank
		//
		for ( ; ; ) {						// loops while it doesn't find a blank
			// first skip everything that is not blank inside the buffer
			for ( j = i; j < nbytes && buffer[j] != ' '; j++);
			if ( j < nbytes ) {				// if still inside the buffer, get in
				position = j;				// record last position checked
				if (rest.length() == 0) {	// if there was nothing from previous buffer
                    init = i;
					return true;			// just get the word and return true
				}
				rest.append(buffer, i, j);	// otherwise, append new content to
				init = -1;                  // real length is in rest
				return true; 				// return true because word was found
			}
			// we reached the end of the buffer without finding a blank
			if ( j != i ) rest.append(buffer, i, nbytes); // saves previous contents
			try {							// reads new content from file into buffer
				nbytes = filein.read(buffer);
				if (end = (nbytes == -1)) {	// end of file and no previous content
					if (rest.length() == 0) return false; //-> return false
					init = -1;              // real length is in rest
					return true;			// return true because previous content
				}							// was the last word found
			} catch (IOException e) {
				e.printStackTrace();
				System.exit(0);
			}
			i = 0;							// starts over from the start
		}									// go back and continue checking new buffer
	}

	/**<ul>This method return the number of blanks skipped
	 * <br><br>
	 * @return the number of blanks skipped
	 * </ul>
	 */
	@Override
	public int getnblanks() {
		return nblanks;
	}

	/**<ul>Returns a boolean indicating if the file has ended 
	 * <br><br>
	 * @return a <code>true</code> if the file ended or <code>false</code> if the 
	 * file still has bytes to be read 
	 * </ul>
	 */
	@Override
	public boolean end() {
		return end;
	}
}
