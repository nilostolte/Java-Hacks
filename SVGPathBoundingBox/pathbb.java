import static svg.SVGPathBoundingBox.parsePath;
import svg.Path;
import java.util.Scanner;

public class pathbb {

    public static void main(String[] args) {
      Scanner scanner = new Scanner(System.in);
      System.out.print("Paste your path and press Enter: ");
      String input = scanner.nextLine();
      scanner.close();
      Path p = null;
      if ( input.length() < 6) System.exit(1);
      try {
        p = parsePath(input); 
      }
      catch (Exception e) {
         System.exit(1);
      }
      if ( p == null) System.exit(1);
      System.out.print("Path bounding box:\n");
      System.out.println("xmin = " + p.xmin);
      System.out.println("ymin = " + p.ymin);
      System.out.println("xmax = " + p.xmax);
      System.out.print("ymax = " + p.ymax);
    }

}