package svg;

import java.text.DecimalFormat;
import java.util.*;

public class SVGPathReverse {

    private static StringBuilder sb = new StringBuilder(1024);
    private static ArrayList<Float> pf = new ArrayList<Float>(1024);
    private static DecimalFormat df = new DecimalFormat("#.###");

    static public String reverse(String path) {
       return(reverseD(path, 0));
    }

    static public String reverseD(String path, int offset) {
       int i = offset;                 // get index of the 1st command
       i++;                            // skip M command and point to 1st float
       int k;
       int j = find1st(path, i);       // find the end of the first float
       float x = Float.parseFloat(path.substring(i, j)); // initial x coordinate
       if (path.charAt(j) == ',') j++; // it's positive. Skip comma to access 2nd float
       i = j;                          // advance to initial position of 2nd float
       j = find2nd(path, i);           // find the end of the 2nd float
       float y = Float.parseFloat(path.substring(i, j)); // initial y coordinate
       float xi, yi;
       i = j;                          // advance to the 2nd command
       pf.clear();                     // reinitialize the arraylist to 
       pf.add(x);                      // move first point to arraylist
       pf.add(y);                      // 
       // grab all points in the path by interpreting path commands
       while (path.charAt(i) != 'z') {
          if (Character.isWhitespace(path.charAt(i))) {  i++; continue; }
          if (path.charAt(i) == 'C') { 
            i++;              // advance to 1st float
            for (k = 0; k < 3; k++) {
               j = find1st(path, i);
               x = Float.parseFloat(path.substring(i, j));
               if (path.charAt(j) == ',') j++;
               i = j;
               j = (k == 2) ? find2nd(path, i) : find1st(path, i);
               y = Float.parseFloat(path.substring(i, j));
               pf.add(x);
               pf.add(y);
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
               pf.add(xi);
               pf.add(yi);
               if (path.charAt(j) == ',') j++;
               i = j;
            }
            x = xi; y = yi;
            continue;
          }          
          return "Error: command \"" + path.charAt(i) + "\" is not implemented in this parser";
       }
       sb.setLength(0);
       sb.append('M');    //the last point in the last bezier curve is the new M
       i = pf.size() - 1;
       y = pf.get(i--);
       x = pf.get(i--);
       sb.append(df.format(x));
       if (y >= 0f) sb.append(',');
       sb.append(df.format(y));
       while (i >= 0) {
          sb.append('c');
          yi = pf.get(i--) - y;
          xi = pf.get(i--) - x;
          sb.append(df.format(xi));
          if (yi >= 0f) sb.append(',');
          sb.append(df.format(yi));
          yi = pf.get(i--) - y;
          xi = pf.get(i--) - x;
          if (x >= 0f) sb.append(',');
          sb.append(df.format(xi));
          if (yi >= 0f) sb.append(',');
          sb.append(df.format(yi));
          yi = pf.get(i--);
          xi = pf.get(i--);
          if ((xi - x) >= 0f) sb.append(',');
          sb.append(df.format(xi - x));
          if ((yi - y) >= 0f) sb.append(',');
          sb.append(df.format(yi - y));
          x = xi;
          y = yi;
       }
       sb.append('z');
       return sb.toString();
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