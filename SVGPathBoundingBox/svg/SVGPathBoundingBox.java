package svg;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;
import java.util.regex.Pattern;


public class SVGPathBoundingBox {

    static public Path parsePath(String path) {
       int j = 0;
       while( Character.isWhitespace(path.charAt(j)) ) j++;
       if (
          path.charAt(j) != '<' &&
          path.charAt(j + 1) != 'p' &&
          path.charAt(j + 2) != 'a' &&
          path.charAt(j + 3) != 't' &&
          path.charAt(j + 4) != 'h' &&
          path.charAt(j + 5) != ' '
       ) return null; // No path in the string
       Path p;
       int i = j;
       while( path.charAt(i) != 'd') i++;
       if (path.charAt(++i) == '=') {
         if (path.charAt(++i) == '"') {
           i++;
           p = parseD(path, i);
           if (p != null) {
             p.path = path.substring(j);
             return p;
           }
         }
       }
       return null;
    }
    
    static public Path parseD(String path, int i) {
       if (path.charAt(i) != 'M') return null;      
       i++;                  // skip M command and point to 1st float
       int k;
       int j = find1st(path, i);    // find the end of the first float
       float x = Float.parseFloat(path.substring(i, j)); // initial x coordinate
       if (path.charAt(j) == ',') j++; // it's positive. Skip comma to access 2nd float
       i = j;                          // advance to initial position of 2nd float
       j = find2nd(path, i);           // find the end of the 2nd float
       float y = Float.parseFloat(path.substring(i, j)); // initial y coordinate
       float xmin = x;        // assume xmin is the first x coordinate 
       float ymin = y;        // assume ymin is the first y coordinate
       float xmax = x;        // assume xmax is the first x coordinate 
       float ymax = y;        // assume ymax is the first y coordinate
       float xi, yi;
       i = j;                 // advance to the 2nd command
       // calculates bounding box by interpreting path commands
       while (path.charAt(i) != 'z') {
          if (Character.isWhitespace(path.charAt(i))) {  i++; continue; }
          if (path.charAt(i) == 'C') { 
            i++;              // advance to 1st float
            for (k = 0; k < 3; k++) {
               j = find1st(path, i);
               x = Float.parseFloat(path.substring(i, j));
               x = Float.parseFloat(path.substring(i, j));
               if (path.charAt(j) == ',') j++;
               i = j;
               j = (k == 2) ? find2nd(path, i) : find1st(path, i);
               y = Float.parseFloat(path.substring(i, j));
               xmin = Math.min(x, xmin); xmax = Math.max(x, xmax);
               ymin = Math.min(y, ymin); ymax = Math.max(y, ymax);
               if (path.charAt(j) == ',') j++;
               i = j;
            }
            continue;
          }
          if (path.charAt(i) == 'c') {
            i++;              // advance to 1st float
            xi = x; yi = y;
            for (k = 0; k < 3; k++) {
               j = find1st(path, i);
               xi = x + Float.parseFloat(path.substring(i, j));
               if (path.charAt(j) == ',') j++;
               i = j;
               j = (k == 2) ? find2nd(path, i) : find1st(path, i);
               yi= y + Float.parseFloat(path.substring(i, j));
               xmin = Math.min(xi, xmin); xmax = Math.max(xi, xmax);
               ymin = Math.min(yi, ymin); ymax = Math.max(yi, ymax);
               if (path.charAt(j) == ',') j++;
               i = j;
            }
            x = xi; y = yi;
            continue;
          }          
          System.out.print("Command \"" + path.charAt(i) + "\" is not implemented in this parser\n");
          return null;
       }
       return new Path(xmin, ymin, xmax, ymax, null);
    }
    
    static private int find1st(String path, int i) {
       int j = i + 1;         // jump over first digit, since it could be a sign
       while( path.charAt(j) != '-' && path.charAt(j) != ',' ) j++;
       return j;
    }
    static private int find2nd(String path, int i) {
       int j = i + 1;         // jump over first digit, since it could be a sign
       while(  
          path.charAt(j) != 'c' &&
          path.charAt(j) != 'C' &&
          path.charAt(j) != ' ' &&
          path.charAt(j) != 'z'
       ) j++;
       return j;
    }   
}