package be.uliege.montefiore.oop;

import be.uliege.montefiore.oop.GUIException;
import be.uliege.montefiore.oop.reader.WronglyFormattedFileException;
import be.uliege.montefiore.oop.GUI.*;

import java.io.FileNotFoundException;
import java.io.IOException;

public class Main{

   public static void main(String[] args)
   {
      GraphicalInterface gI = null;
      try {
         gI = new GraphicalInterface(400, 500, args[0]);
      } catch (IOException | WronglyFormattedFileException | DimensionsException | GUIException e) {
         System.out.println(e);
         return;
      }

      boolean finished = false;
      try
      {
         gI.display();
         while(!finished)
         {
            finished = gI.nextMove();
            if(!finished || (finished && gI.checkIfWin()))
               gI.display();
         }
         gI.endGame();
      }
      catch (GUIException e)
      {
         System.out.println(e);
         return;
      }
      return;
   }
}
