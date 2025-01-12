# Java Hacks

Each directory of this repository contains a program in Java that is shown how to be compiled 
via CLI with a `build.bat` for Windows machines (in other machines just copy and paste the 
individual commands in this batch file or convert it the another shell script) to obtain a 
`jar` application that functions as a tool or example.

## SVGspecular - Phong shading for SVG radial gradients

[This](SVGspecular) is a single class program that creates an **SVG** file containing a square with a non-linear radial gradient based on Phong shading function for specular highlights (function $cos^n(angle)$​). The program produces a complete standalone **SVG** file.

The **SVG** has a number of stop colors $N$ determined by the user. Other parameters, such as the
exponent of Phong's function (the $n$ of $cos^n(angle)$, not $N$), can also be supplied by the user following the program name as usually done in **CLI** mode.

### Overall algorithm

The program takes the **initial color** $\vec{C_0}$ (which is generally white in the center of a the radial
gradient) and the **last color** $\vec{C_1}$ (which is any color to be continued outside the radial gradient),
and linearly interpolates the color $\vec{C}$ for each **stop color** based on a multidimensional parametric line equation, where the value of the parameter $t$ is given by the result of applying an $angle$ $\alpha$ to the function $cos^n(angle)$. In other words, this procedure is shown below:

```math
  \begin{aligned}
     &t = cos^n(\alpha)\\
     &\vec{C} = \vec{C_1}+ (\vec{C_0} - \vec{C_1}) \cdot t\\
     &\\
  \end{aligned}
 ```

Notice that the individual color components need to be processed independently. For simplicity and easier comprehension, the colors are dealt here as a sort of 3D (or 4D, including the alpha channel) vector representation. Indeed, colors can be represented as vectors where each color component corresponds to a different dimension.

Check the complete algorithm [here](SVGspecular/README.md)

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



