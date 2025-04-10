package be.uliege.montefiore.oop;

import be.uliege.montefiore.oop.gui.*;
import be.uliege.montefiore.oop.model.*;
import be.uliege.montefiore.oop.reader.*;

import java.io.FileNotFoundException;

public class Main
{
   static final String gameWonString = "  ____                       __        __          _\n" + //
            " / ___| __ _ _ __ ___   ___  \\ \\      / /__  _ __ | |\n" + //
            "| |  _ / _` | '_ ` _ \\ / _ \\  \\ \\ /\\ / / _ \\| '_ \\| |\n" + //
            "| |_| | (_| | | | | | |  __/   \\ V  V / (_) | | | |_|\n" + //
            " \\____|\\__,_|_| |_| |_|\\___|    \\_/\\_/ \\___/|_| |_(_)\n" + //
            "";

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
         while(!quit && !gameWon)
         {
            gui.display();
            quit = gui.nextMove();
            gameWon = puzzle.goalReached();
         }

         if(gameWon)
         {
            System.out.println("Game won in " + puzzle.getmoveCount() + " moves!");
            System.out.println(gameWonString);

            // special victory screen
            quit = false;
            gui.displayVictory();

            while(!quit)
               quit = gui.nextMove();
         }
         else
            System.out.println("You'll eventually get it!");

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
