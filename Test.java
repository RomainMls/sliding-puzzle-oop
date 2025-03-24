import java.io.FileNotFoundException;
import java.io.IOException;

import be.uliege.montefiore.oop.GUIException;

public class Test{

   public static void main(String[] args){
      SpecificationFileReader sfr = new SpecificationFileReader("klotski.spzl");
      Grid g;
      try {
          g = sfr.readGrid();
      } catch (Exception e){
         System.out.println(e.toString());
         return;
      }

      g.printGrid();

      if(g.goalReached())
         System.out.println("YES");
      else
         System.out.println("NON");


         GraphicalInterface gI = null;
         try {
            try {
               gI = new GraphicalInterface(400, 500, "klotski.spzl");
            } catch (DimensionsException e) {

            } catch (GUIException e) {

            }
         } catch (FileNotFoundException e) {

         } catch (IOException e) {

         } catch (WronglyFormattedFileException e) {

         }

         boolean finished = false;
         try {
            gI.display();
            while(!finished){
               finished = gI.nextMove();
               if(!finished || (finished && gI.checkIfWin())){
                  gI.display();
               }
      
            }
            gI.endGame();
         } catch (GUIException e) {

         }
         return;

   }
}
