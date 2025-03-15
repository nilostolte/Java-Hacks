package svg;
public class Path {
   public float xmin;
   public float ymin;
   public float xmax;
   public float ymax;
   public String path;
   public Path(float x1, float y1, float x2, float y2, String s) {
      xmin = x1;
      ymin = y1;
      xmax = x2;
      ymax = y2;
      path = s;
   }
}