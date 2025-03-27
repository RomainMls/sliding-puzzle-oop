package be.uliege.montefiore.oop;

import java.io.FileNotFoundException;
import java.io.IOException;

import be.uliege.montefiore.oop.GUIException;
import be.uliege.montefiore.oop.reader.WronglyFormattedFileException;
import be.uliege.montefiore.oop.GUI.*;

public class Main{

   public static void main(String[] args){
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
