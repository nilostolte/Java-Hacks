import static svg.SVGPathReverse.reverse;

public class reverse {

    public static void main(String[] args) {
      if (args.length < 1) {
         System.out.println("Error: there must be one path to invert");
         System.exit(1);
      }       
      String rp = reverse(args[0]);
      System.out.print(rp);
    }

}