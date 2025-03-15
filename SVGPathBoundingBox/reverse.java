import static svg.SVGPathReverse.reverse;
import java.util.Scanner;

public class reverse {

    public static void main(String[] args) {
      Scanner scanner = new Scanner(System.in);
      System.out.print("Paste your path and press Enter: ");
      String input = scanner.nextLine();
      scanner.close();
      String p = null;
      try {
        p = reverse(input);
        System.out.println(p);
      }
      catch (Exception e) {
         //e.printStackTrace();
         System.exit(1);
      }
    }

}