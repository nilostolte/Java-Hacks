import java.lang.Math;
public class SVGspecular {
   static String xml = "<?xml version=\"1.0\" encoding=\"utf-8\"?>";
   static String dtd = "<!DOCTYPE svg PUBLIC \"-//W3C//DTD SVG 1.1//EN\" \"http://www.w3.org/Graphics/SVG/1.1/DTD/svg11.dtd\">";
   static String svg = "<svg xmlns=\"http://www.w3.org/2000/svg\" "; 

   static String name = "GRAD1"; 
   static String color0 = "#FFFFFF"; 
   static String color1 = "#2F3E51"; 
   static int n = 10;
   static int exp = 2;
   static double a90 = Math.PI/2.;
   static double inc = 1./((double) n);
   static double inca = a90/((double) n);
   static float c0[]= {0f, 0f, 0f};
   static float c1[]= {0f, 0f, 0f};
   static char color[] = {'#', 0, 0, 0, 0, 0, 0 };
   
   public static void main(String[] args) {
       int w = 512;
       int h = 512;
       float r = (w < h)? (((float) w)/2f) : (((float) h)/2f);
       parseArgs(args);
       System.out.println(xml);
       System.out.println(dtd);
       System.out.print(svg + "width=\"" + w + "\" height=\"" + h+ "\" ");
       System.out.println("viewBox=\"0 0 " + w + " " + h + "\" " + "overflow=\"visible\">");
       System.out.print("<radialGradient id=\"" + name + "\" ");
       System.out.print("cx = \"" + (((float) w)/2f) + "\" cy=\"" + (((float) h)/2f) + "\" ");
       System.out.println("r=\"" + r + "\" gradientUnits=\"userSpaceOnUse\">" );
       calculate_steps();
       System.out.println("</radialGradient>");
       System.out.print("<path fill=\"url(#" + name + ")\" ");
       System.out.println("d=\"M0,0L0,"+ h + "L" + w + "," + h + "L" + w + ",0z\"/>" );
       System.out.println("</svg>");
    }
    
    private static void parseArgs(String[] args) {
       int i;
       if (args.length == 0) return;
       // First argument is the number of subdivions (stops)
       if (args[0].matches("[0-9]+")) {
          i = Integer.parseInt(args[0]);
          if (i > 2) {
              n = i;
              inca = a90/((double) i);
              inc = 1./((double) i);
           }
       }
       if (args.length == 1) return;
       // Second argument is the exponent, as n in cos^n
       if (args[1].matches("[0-9]+")) {
          i = Integer.parseInt(args[1]);
          if (i > 0) exp = i;
       }   
       if (args.length == 2) return;
    }
    
    private static void calculate_steps() {
       int i;
       for (i = 0; i < 3; i++) {
          c0[i] = (float) h2i(color0.charAt((i<<1)+1), color0.charAt((i<<1)+2));
          c1[i] = (float) h2i(color1.charAt((i<<1)+1), color1.charAt((i<<1)+2));
       }
       System.out.println(
          "   <stop offset=\"0\"     style=\"stop-color:" + color0 + "\"/>"
       );
       double intensity, cos, x, a, r, g, b;
       x = a = r = g = b = 0.;
       for (i = 2; i < n; i++ ) {
          System.out.print("   <stop offset=\"");
          cos = Math.cos(a += inca);
          System.out.printf("%5.3f\" style=\"stop-color:", x+= inc);
          cos = intensity(cos);
          r = c1[0] + ((c0[0] - c1[0]) * cos);
          g = c1[1] + ((c0[1] - c1[1]) * cos);
          b = c1[2] + ((c0[2] - c1[2]) * cos);
          System.out.println(c2s((int) r, (int) g, (int) b) + "\"/>");
          
       }
       System.out.println(
          "   <stop offset=\"1\"     style=\"stop-color:" + color1 + "\"/>"
       );
    }
    
    private static double intensity(double cos) {
       double intensity = cos ; 
       for (int i = 1; i < exp; i++) intensity *= cos;
       return intensity;
    }
    
    private static String c2s(int r, int g, int b) {
       color[1] = (char) ((r >> 4) + (((r >> 4) > 9) ? 55 : '0'));
       color[2] = (char) ((r & 15) + (((r & 15) > 9) ? 55 : '0'));
       color[3] = (char) ((g >> 4) + (((g >> 4) > 9) ? 55 : '0'));
       color[4] = (char) ((g & 15) + (((g & 15) > 9) ? 55 : '0'));
       color[5] = (char) ((b >> 4) + (((b >> 4) > 9) ? 55 : '0'));
       color[6] = (char) ((b & 15) + (((b & 15) > 9) ? 55 : '0'));
       return new String(color);
    }
    private static int h2i(char c1, char c0) {
       int i0 = (c0 - '0');
       if (i0 > 9) i0 -= 7;
       int i1  = (c1 - '0');
       if (i1 > 9) i1 -= 7;
       return ((i1<<4)+i0);
    }

}
