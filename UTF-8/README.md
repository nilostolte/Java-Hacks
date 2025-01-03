# UTF-8

This project allows to read UTF-8 encoded UNICODE files in Java. It implements an infinite buffer file reader where one can recover the text word by word, which is converted from UTF-8, skipping blanks. The blanks can be recovered since they are all counted (feature used in the test program). By skipping blanks the reader is able to identify the start and the end of the words because they are different than blank. The fact that the text is encoded is totally transparent to the reader. The test program reads an UTF-8 testing file and prints it over the console. A batch file is provided to help running the jar. Another batch file to build the source files and generate the jar using ava CLI in console window is also given. 

## Building the JAR

Due to incompatibilities between different versions of Java, it's better to build the jar from the source files. In order to build the jar in a Windows platform one should open a command window and set it to the directory containing the contents of this repertory. One should not use a Powershell (Powershell causes a bug with the flag `-Dfile.encoding=UTF-8`), but just a normal Command Prompt. In this window just type:

```
build
```

## Running the JAR

To run the JAR one needs the files: `utf8.jar`, `UTF8.txt` and `run.bat`. These three files should be in the same directory. If running on Windows it is sufficient to type `run` in a console in this directory. An `out.txt` should be created and should be identical to the one supplied in this repository. Alternatively, just double click the `run` file.

## The Source Files

The source files and their locations in the file structure are given by the file `sources.txt`. This files is used for building the jar.

### InputFile.java and Input.java

These files are inside package com.filereader. 

### Input.java

Input.java is an interface encapsulating the file. It can be used to encapsulate any source of bytes of any kind including Strings:

```java
public interface Input {
	public int nextword();
	public String getWord();
	public int getnblanks();
	public boolean end();
}
```

The method `nextword()` reads the words (assumed to be separated by spaces), one by one, until end of file is reached. It returns an integer indicating the number of characters in the word or **-1** in the case of end of file. The method `getWord()` returns a string with the word read. Since blanks are just separators they are generally discarded, but in the case they need to be recovered, as in the case of `UTF8.txt`, the method `getnblanks()` returns the number of blanks skipped. 
In the example, blanks are cached into an array that is used to generate a string with the number of required blanks. This caching scheme seems to be more efficient than looping over `System.out.print(' ')` to print each blank individually. Finally, the method `end()` returns true if the end of the file has already been reached.

### InputFile.java

<dd><b><code>InputFile</code></b> is an infinite source of input of bytes    
from a file. It repeatedly reads the file to a buffer, skipping blanks (here, 
blanks are considered as word separators - another word separator can be configured).       
It then scans the file while it contains non blank characters. If the buffer was 
totally scanned without a blank being found, the word probably continues in the 
contents still not read in the file. The non blank content is then saved in an 
Arraylist of bytes. Next, the file is read to fill the buffer again and the 
scanning process continues in this manner until a blank character is found, 
when the cycle starts over. The number of blanks skipped is maintained (as explained
above), in case an exact copy of the file is required.
  
This word by word parsing method is focused on free style formats as we usually find in programming 
languages and text data file formats, thus being able to fit to any application using data in text or mixed
file formats. To avoid confusion with the UTF-8 encoding, the binary information is recommended to 
be dealt using escape sequences of ASCII characters.

This class implements and is supposed to be accessed through the interface
<b><i>Input</i></b> to allow scanning other different sources of bytes. The other methods
that can be found in this class, besides those of Input.java, are the ones below.

#### Method `setFile(String file)`

This method passes a string containing the name of the file to be read. It opens the file and reads
the first chunk of data into the buffer, as shown, and assigning zero to the initial `position` of the buffer.
It also sets the charset for decoding UTF-8 contents. This was placed here for future versions of this 
method, where the encoding name is passed as a parameter. Since the parser implemented here is generic
and essentially reads only raw bytes it can be easily adapted to read files with other encodings. 

```java
	public void setFile(String file) throws IOException {
		position = 0;
		UTF8 = Charset.forName("UTF-8");
		filein = new FileInputStream(file);
		nbytes = filein.read(buffer);
	}
```

#### Method `readword()`

This is the method that really reads the file and performs what is described above. The actual 
code is commented in almost every line, as one can see below:

```java
	private boolean readword() {
		if ( end = (nbytes == -1) ) return false;
		int i, j;
		nblanks = 0;					// there were no blanks skipped yet
		rest.clear();					// list should be empty here
		//
		// Loops while reading buffer, jumping over all blanks
		//
		for ( ; ; ) {						// loops while it finds blanks
			// first skip all the blanks already read
			for ( i = position; i < nbytes && buffer[i] == ' '; i++ );
			nblanks += i - position;		// records number of blanks skipped
			if (i < nbytes) break;			// if still inside the buffer, get out
			try {					// otherwise read the buffer from file
				nbytes = filein.read(buffer);
				if (end = (nbytes == -1))	// end of file -> return false
					return false;
			} catch (IOException e) {		// on a exception, call exit
				e.printStackTrace();
				System.exit(0);
			}
			position = 0;				// starts from beginning of buffer again
		}						// go back and continue checking new buffer
		//
		// Loops while reading buffer, jumping over everything that is not blank
		//
		for ( ; ; ) {					// loops while it doesn't find a blank
			// first skip everything that is not blank inside the buffer
			for ( j = i; j < nbytes && buffer[j] != ' '; j++);
			if ( j < nbytes ) {			// if still inside the buffer, get in
				position = j;			// record last position checked
				if (rest.length() == 0) {	// if there was nothing from previous buffer
                                    init = i;
                                    return true;		// just get the word and return true
				}
				rest.append(buffer, i, j);	// otherwise, append new content to
				init = -1;                      // real length is in rest
				return true; 			// return true because word was found
			}
			// we reached the end of the buffer without finding a blank
			if ( j != i ) rest.append(buffer, i, nbytes); // saves previous contents
			try {					// reads new content from file into buffer
				nbytes = filein.read(buffer);
				if (end = (nbytes == -1)) {	// end of file and no previous content
					if (rest.length() == 0) return false; //-> return false
					init = -1;              // real length is in rest
					return true;		// return true because previous content
				}				// was the last word found
			} catch (IOException e) {
				e.printStackTrace();
				System.exit(0);
			}
			i = 0;					// starts over from the start
		}						// go back and continue checking new buffer
	}
```

    
