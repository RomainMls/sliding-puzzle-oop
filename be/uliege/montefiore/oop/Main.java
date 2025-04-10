package be.uliege.montefiore.oop;

import be.uliege.montefiore.oop.gui.*;
import be.uliege.montefiore.oop.model.*;
import be.uliege.montefiore.oop.reader.*;

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
         System.out.println(e);
         return;
      }

      try
      {
         boolean quit = false;
         boolean gameWon = false;
         gui.display();
         while(!quit && !gameWon)
         {
            try
            {
               if(gui.nextMove())
                  gui.display();
            }
            catch(QuitGameException e)
            {
               quit = true;
            }
            gameWon = puzzle.goalReached();
         }

         if(gameWon)
         {
            System.out.println("Game won in " + puzzle.getmoveCount() + " moves!");

            // special victory screen
            gui.displayVictory();

            quit = false;
            while(!quit)
            {
               try
               {
                  gui.nextMove();
               }
               catch(QuitGameException e)
               {
                  quit = true;
               }
            }
         }
         else
            System.out.println("You will get it next time!");

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
