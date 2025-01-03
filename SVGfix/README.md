# SVGfix - TreeMap Example
This program copies the color information from CSS classes and plugs them into the respective paths using SVG fill attribute instead of CSS.

It's an example of application using TreeMap JRE class to create a tree functioning as a symbol table to keep colors of SVG paths initially defined by CSS classes at the beginning of the SVG file as done by some versions of Illustrator when saving the file as SVG.

Once the program reads the colors between <defs><style> and </style></defs> from file input.svg, storing the the colors preceeded by a fill command in string format and using the class name as the key to access it in the TreeMap, it then prints in the command prompt (usually redirected to a file) the SVG with colors applied directly to the corresponding path (via fill attribute) that used the corresponding class to define the color.

This program is a simple hack and it may copy other commands when multiple classes with the same name are used but only the last reference will be taken into account for each path. Complex configurations, like CSS classes separated by commas sharing the same attribute (color or another attricute) will cause an error.

When an error is detected while using the TreeMap, the program says which class was not found and dumps all the keys of the tree for debugging purposes.
