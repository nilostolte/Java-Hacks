import static svg.SVGPathBoundingBox.parseD;
import svg.Path;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;
import java.util.regex.Pattern;


public class SVGPathBoundingBoxes {
    static FileInputStream fileIn;
    private static class UnpredictedPath extends RuntimeException {
       public UnpredictedPath(long i) {
          super("Path format unexpected near position " + i);
       }
    };
    private static class SVGTagMustEndFile extends RuntimeException {
       public SVGTagMustEndFile() {
          super("</svg> tag found but it must be the last string in the stream");
       }
    };
    private static class SVGCommandNotImplemented extends RuntimeException {
       public SVGCommandNotImplemented(char c) {
          super("Command \"" + c + "\" is not implemented in this parser");
       }
    };
  
    private static ArrayList<Path> paths;
      
    public static void main(String[] args) throws FileNotFoundException, IOException {
      paths = new ArrayList<Path>();
      Path found;
      Pattern path = Pattern.compile("<path");
      Pattern cpath = Pattern.compile("\"/>");
      
      fileIn = new FileInputStream("input.svg");
      Scanner in = new Scanner(fileIn);
      in.useDelimiter(path);
      in.useDelimiter(cpath);
      try {
        do {
           if ((found = parsePath(in.next())) == null) break;
           paths.add(found);
        } while (in.hasNext());
      } catch (Exception e) {
          System.out.print("Error! Last path parsed was:\n");
          System.out.println(paths.get(paths.size()-1).path);
          e.printStackTrace();
          System.exit(1);
      }
      if (fileIn.available() != 0) throw new SVGTagMustEndFile();
      System.out.print("Found " + paths.size() + " paths\n");
      System.out.print("Last path found:\n");
      Path p = paths.get(paths.size()-1);
      System.out.print(p.path);
      System.out.print("\nPath bounding box:\n");
      System.out.println("xmin = " + p.xmin);
      System.out.println("ymin = " + p.ymin);
      System.out.println("xmax = " + p.xmax);
      System.out.print("ymax = " + p.ymax);
    }
    

    static private StringBuilder sb = new StringBuilder(1024);

    static private Path parsePath(String path) throws IOException {
       int j = 0;
       while( Character.isWhitespace(path.charAt(j)) ) j++;
       if (path.charAt(j+1) == '/') return null; //</svg>
       Path p;
       int i = j;
       while( path.charAt(i) != 'd') i++;
       if ( (path.charAt(i+1) == '=') && (path.charAt(i+2) == '"') ) {
         p = parseD(path, i+3);
         sb.setLength(0);
         sb.append(path, j, path.length());
         sb.append("\"/>");
         p.path = sb.toString();
         return p;
       }
       throw new UnpredictedPath(fileIn.getChannel().position());
    }

}