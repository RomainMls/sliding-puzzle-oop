package be.uliege.montefiore.oop;

import be.uliege.montefiore.oop.model.*;
import be.uliege.montefiore.oop.reader.*;
import be.uliege.montefiore.oop.GUI.*;

import java.io.FileNotFoundException;

public class Main
{
   public static void main(String[] args)
   {
      if(args.length == 0 || args[0] == null)
      {
         System.out.println("ERROR: No specification file given");
         return;
      }

      Puzzle puzzle;
      GraphicalInterface gui;
      try
      {
         SpecificationFileReader sf = new SpecificationFileReader(args[0]);
         puzzle = sf.readPuzzle();

         gui = new GraphicalInterface(puzzle);
      }
      catch(FileNotFoundException | InvalidFileFormatException | GUIException | DimensionsException e)
      {
         System.out.println(e.getMessage());
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
            // special victory screen
            quit = false;
            gui.displayVictory();

            while(!quit)
               quit = gui.nextMove();
         }
         gui.endGame();
      }
      catch (GUIException e)
      {
         System.out.println(e.getMessage());
         return;
      }
      return;
   }
}
