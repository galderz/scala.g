package g.scala.demux;

/**
 * // TODO: Document this
 *
 * @author Galder Zamarreño
 * @since // TODO
 */
public class TruthTableJava {

   private static void printTruthTable(int n) {
      int rows = (int) Math.pow(2,n);

      for (int i=0; i<rows; i++) {
         for (int j=n-1; j>=0; j--) {
            System.out.print((i/(int) Math.pow(2, j))%2 + " ");
         }
         System.out.println();
      }
   }
   public static void main(String[] args) {
      printTruthTable(2); //enter any natural int
   }

}
