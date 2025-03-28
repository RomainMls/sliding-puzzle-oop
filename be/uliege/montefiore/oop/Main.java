package be.uliege.montefiore.oop;

import be.uliege.montefiore.oop.model.*;
import be.uliege.montefiore.oop.reader.*;
import be.uliege.montefiore.oop.GUI.*;

import java.io.IOException;

public class Main{

   public static void main(String[] args)
   {
      Puzzle puzzle;
      GraphicalInterface gui;
      try
      {
         SpecificationFileReader sf = new SpecificationFileReader(args[0]);
         puzzle = sf.readPuzzle();
      }
      catch(IOException | WronglyFormattedFileException e)
      {
         System.out.println(e);
         return;
      }

      try
      {
         gui = new GraphicalInterface(puzzle);
      }
      catch(DimensionsException | GUIException e)
      {
         System.out.println(e);
         return;
      }

      boolean quit = false;
      boolean gameWon = false;
      try
      {
         while(!quit && !gameWon)
         {
            gui.display();
            quit = gui.nextMove();
            gameWon = puzzle.goalReached();
         }
         if(gameWon)
         {
            quit = false;
            gui.displayVictory();
            // display that you won
            while(!quit)
               quit = gui.nextMove();
         }
         gui.endGame();
      }
      catch (GUIException e)
      {
         System.out.println(e);
         return;
      }
      return;
   }
}
