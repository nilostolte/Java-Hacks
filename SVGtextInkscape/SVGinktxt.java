import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class SVGinktxt {
    static byte[] bytes;
    static int index;
    static byte[] matrix = {
       't', 'r', 'a', 'n', 's', 'f', 'o', 'r', 'm', '=', '"', 
       'm', 'a', 't', 'r', 'i', 'x', '(', '1', ' ', '0', ' ', 
       '0', ' ', '1', ' ' , '0', ' '
    };
	public static void main(String[] args) throws IOException {
       bytes = Files.readAllBytes(Paths.get("text.txt"));
       parse();
	}

    private static void parse() {
      String result = "";
      int i;
      double number;
      boolean numberStarted = false;
      double offset = 0.;
      int decimalPointCount = 0;
      
      for (index = 0; index < bytes.length; index++) {
        char ch = (char) bytes[index]; 

        if (ch == '-')  {
            if (index >= (bytes.length -1)) {
               result += ch;
               continue;
            }
            if ((bytes[index+1]  >= '0') && (bytes[index+1] <= '9')) {
               result += String.valueOf((float)(offset + parseNum()));
               continue;
            }
        }
        // grab offset from matrix and delete matrix
        if ((ch == 't') && ((index + matrix.length) < bytes.length))  {
           for (i = 1; i < matrix.length; i++) {
              if (bytes[index + i] != matrix[i]) break;
           }
           if (i == matrix.length) {
              index += matrix.length;
              offset = parseNum();
              if ((bytes[index + 1] == ')') && (bytes[index + 2] == '"')) {
                 index += 2; continue;
              }
              System.out.printf("Error: matrix not ending correctly here:\n\n%s\n", result);
              System.exit(1);
           } 
        }     
        result += ch;
      }   
      System.out.println(result);
   }

   private static double parseNum() {
     String currentNumber = "";
     int decimalPointCount = 0;
     char ch;
     double result;
     if (bytes[index] == '-') {
        currentNumber = "-";
        index++; // jumps over sign
     }
     for (int i = index; i < bytes.length; i++) {
        ch = (char) bytes[i]; 
        if (ch >= '0' && ch <= '9')  {
          currentNumber += ch;
          continue;
        }
        if (ch == '.') {
          if (decimalPointCount > 0) {
             System.out.printf("Error: More than one decimal point in number. %d\n", i);
             System.exit(1);
          }
          decimalPointCount++; 
          currentNumber += ch;        
          continue;       
        }
        if (ch == '-') {
          System.out.printf("Error: More than one sign in number %d\n", i);
          System.exit(1);
        }
        // ch is not part of the number. Thus the number ends here
        result = Double.parseDouble(currentNumber);
        index = i - 1;  // index points to last digit of number
        return(result);
     }
     System.out.printf("Error: number ended with eof: %d\n", bytes.length);
     System.exit(1);
     return(0.);  // required by Java     
   }
}