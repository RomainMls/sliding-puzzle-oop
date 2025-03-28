package be.uliege.montefiore.oop.reader;

import be.uliege.montefiore.oop.model.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class SpecificationFileReader{
   private final String filename;

   public SpecificationFileReader(String filename){
      this.filename = filename;
   }

   public Puzzle readPuzzle() throws FileNotFoundException, InvalidFileFormatException{
      Scanner sc = new Scanner(new File(filename));
      String str = null;

      if(sc.hasNextLine())
         str = sc.nextLine();

      String[] values = str.split(" ");
      if(values.length != 3){
         sc.close();
         throw new InvalidFileFormatException("The first line of the specification file must be of the form 'nbColumns nbRows nbPieces'");
      }

      int nbColumns = Integer.valueOf(values[0]);
      int nbRows = Integer.valueOf(values[1]);
      int nbPieces = Integer.valueOf(values[2]);
      Puzzle g = new Puzzle(nbRows, nbColumns);

      int i;
      for(i = 0; i < nbPieces && sc.hasNextLine(); i++){
         str = sc.nextLine();
         values = str.split(" ");

         if(values.length != 4){
            sc.close();
            throw new InvalidFileFormatException("The line " + i+2 + " of the specification file must be of the form 'width height xpos ypos' for that piece");
         }

         int width = Integer.valueOf(values[0]);
         int height = Integer.valueOf(values[1]);
         int xpos = Integer.valueOf(values[2]);
         int ypos = Integer.valueOf(values[3]);

         Piece p = new Piece(height, width, xpos, ypos, i+1);
         try {
            g.addPiece(p);
         } catch (PuzzleFullException | InvalidPieceException e) {
            sc.close();
            System.out.println(e.getMessage());
            return null;
         }
      }

      if(i != nbPieces){
         sc.close();
         throw new InvalidFileFormatException("Error reading pieces, expected " + nbPieces + " pieces, read: " + i+1);
      }

      if(sc.hasNextLine()){
         str = sc.nextLine();
         values = str.split(" ");
      }

      if(values.length != 1 || values[0].isEmpty()){
         sc.close();
         throw new InvalidFileFormatException("The line " + nbPieces+2 + " of the specification file must be of the form 'ngoals', specifying the number of goal pieces");
      }

      int nbGoalPieces = Integer.valueOf(values[0]);
      int j;
      for(j = 0; j < nbGoalPieces && sc.hasNextLine(); j++){
         str = sc.nextLine();
         values = str.split(" ");

         if(values.length != 3){
            sc.close();
            throw new InvalidFileFormatException("The line " + nbPieces+j+3 + " of the specification file must be of the form 'nb xpos ypos' for that goal piece");
         }

         int ID = Integer.valueOf(values[0]);
         int xpos = Integer.valueOf(values[1]);
         int ypos = Integer.valueOf(values[2]);

         Piece OGp = g.getPiece(ID);
         GoalPiece pg = new GoalPiece(OGp.getWidth(), OGp.getHeight(), OGp.getPosition(), ID, xpos, ypos);

         g.removePiece(OGp);
         try {
            g.addPiece(pg);
         } catch (PuzzleFullException | InvalidPieceException e) {
            sc.close();
            System.out.println(e.getMessage());
            return null;
         }
      }

      if(j != nbGoalPieces){
         sc.close();
         throw new InvalidFileFormatException("Error reading pieces, expected " + nbPieces + " pieces, read: " + i+1);
      }

      sc.close();
      return g;
   }
}
