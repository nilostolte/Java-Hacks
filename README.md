# Java Hacks

Each directory of this repository contains a program in Java that is shown how to be compiled 
via CLI with a `build.bat` for Windows machines (in other machines just copy and paste the 
individual commands in this batch file or convert it the another shell script) to obtain a 
`jar` application that functions as a tool or example.

## SVGfix - TreeMap Example

[This program](SVGfix) copies the color information from CSS classes and plugs them into the
respective paths using SVG `fill` attribute instead of CSS.

It's an example of application using `TreeMap` JRE class to create a tree
functioning as a symbol table to keep colors of SVG paths initially defined by CSS classes at 
the beginning of the SVG file as done by some versions of Illustrator when saving the file as SVG.

Once the program reads the colors between `<defs><style>` and  `</style></defs>` from file `input.svg`,
storing the the colors preceeded by a `fill` command in string format and using the class name as the key 
to access it in the `TreeMap`, it then prints in the command prompt (usually redirected to a file) the SVG
with colors applied directly to the corresponding path (via `fill` attribute) that used the corresponding 
class to define the color.

This program is a simple hack and it may copy other commands when multiple classes with the same name are
used but only the last reference will be taken into account for each path. Complex configurations, like 
CSS classes separated by commas sharing the same attribute (color or another attribute) will cause an error.

When an error is detected while using the `TreeMap`, the program says which class was not found and dumps all 
the keys of the tree for debugging purposes.

## UTF-8

[This project](UTF-8) allows to read UTF-8 encoded UNICODE files. It implements an infinite buffer file 
reader where one can recover the text word by word, which is converted from UTF-8, skipping blanks. 
The blanks can be recovered since they are all counted (feature used in the test program). By skipping 
blanks the reader is able to identify the start and the end of the words because they are different 
than blank. The fact that the text is encoded is totally transparent to the reader. The test program 
reads an UTF-8 testing file and prints it over the console. A batch file is provided to help running
the jar. The source files are also given and they are all for free with no license of any kind. 



