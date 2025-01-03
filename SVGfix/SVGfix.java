import java.util.TreeMap;
import java.util.Set;
import java.util.Iterator;
import java.util.ArrayList;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.regex.Pattern;

public class SVGfix {
    static private String msgNoFix = "File \"input.svg\" doesn't have colors in CSS format - no output generated";
    static private TreeMap<String,String> tree = new TreeMap<String,String>();

	public static void main(String[] args) throws FileNotFoundException {
       Pattern path = Pattern.compile("<path");
       FileInputStream fileIn = new FileInputStream("input.svg");
       Scanner in = new Scanner(fileIn);
       String previous = parseSymbols(in);
       System.out.println(previous);
       in.useDelimiter(path);
       System.out.println(in.next());
       do {
           in.skip(path);
           System.out.println(fixPath(in.next()));
       } while (in.hasNext());
	}

    static private String parseSymbols(Scanner in) {
       Pattern defs = Pattern.compile("<defs>");
       Pattern style = Pattern.compile("</style>");
       String previous = null;
       String styles = null;
       in.useDelimiter(defs);
       try {
          previous = in.next();
          in.skip(defs);
          in.skip(Pattern.compile("<style>"));
          in.useDelimiter(style);
          styles = in.next();
          in.skip(style);
          in.skip(Pattern.compile("</defs>"));
       }
	   catch (java.util.NoSuchElementException e) {
          System.out.println(msgNoFix);
          System.exit(1);
       }
       fillTree(styles);  
       return previous; 
    }

    static private void fillTree(String styles) {
       String name;
       String value;
       int j;
       for ( int i = 0; i < styles.length(); i++) {
           while( styles.charAt(i) == ' ') i++;
           if (styles.charAt(i) != '.' ){
              System.out.println(
                  "Expected '.' but found '" + styles.charAt(i) + 
                  "' at position " + (i) + " in <styles>"
              );
              System.exit(1);
           }
           i++;
           for (j = i;  styles.charAt(j) != '{'; j++);
           name = styles.substring(i, j);
           for (i = j+1;  styles.charAt(i) != '}'; i++);
           value = styles.substring(j+1, i);
           tree.put(name, value);
       } 
    }
    static private String lass = "lass=\"";
    static private String fixPath(String path) {
       String name;
       String value; 
       int i = 0;
       int j, k;
       while( path.charAt(i) == ' ') i++;      
       if ( (path.charAt(i) == 'c') && (path.charAt(i+5) == '=') ) {
          for (j= i + 8; path.charAt(j) != '"'; j++ );
          name = path.substring(i+7, j);
		  if (!tree.containsKey(name)) {
              System.out.println(
                  "Class named \"" + name + "\" doesn't exit in <styles>"
              );
              Set<String> keys = tree.keySet();
              Iterator<String> s = keys.iterator();
              while ( s.hasNext() ) System.out.println(s.next());
              System.exit(1);           
          }
          value = tree.get(name);
          for (k = 0; value.charAt(k) != ':'; k++ );
          return "<path " + value.substring(0,k) + "=\"" + value.substring(k+1) +
                 "\"" + path.substring(j+1);
       }
       return "<path " + path.substring(i);
    }
}