package be.uliege.montefiore.oop.reader;

import be.uliege.montefiore.oop.model.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class SpecificationFileReader{
   private final String filename;

   public SpecificationFileReader(String filename){
      this.filename = filename;
   }

   public Puzzle readPuzzleNew() throws FileNotFoundException, InvalidFileFormatException{
      Scanner sc = new Scanner(new File(filename));
      String str = null;

      if(sc.hasNextLine())
         str = sc.nextLine();

      String[] values = str.split("\\s+");
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
         values = str.split("\\s+");

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
         } catch (InvalidPieceException e) {
            sc.close();
            System.out.println(e.getMessage());
         }
      }

      //It has stopped the loop because, we are expecting line which are not specified
      if(i != nbPieces){
         sc.close();
         throw new InvalidFileFormatException("There are missing lines (missing pieces) in the specification file");
      }
   
      if(sc.hasNextLine()){
         str = sc.nextLine();
         values = str.split("\\s+");
      }

      if(values.length != 1 || values[0].isEmpty()){
         sc.close();
         throw new InvalidFileFormatException("The line " + nbPieces+2 + " of the specification file must be of the form 'ngoals', specifying the number of goal pieces");
      }

      int nbGoalPieces = Integer.valueOf(values[0]);
      for(int j = 0; j < nbGoalPieces && sc.hasNextLine(); j++){
         str = sc.nextLine();
         values = str.split("\\s+");

         if(values.length != 3){
            sc.close();
            throw new InvalidFileFormatException("The line " + nbPieces+j+3 + " of the specification file must be of the form 'nb xpos ypos' for that goal piece");
         }

         int ID = Integer.valueOf(values[0]);
         int xpos = Integer.valueOf(values[1]);
         int ypos = Integer.valueOf(values[2]);
         
         Piece OGp = g.getPiece(ID);
         GoalPiece pg = new GoalPiece(OGp.getWidth(), OGp.getHeight(), OGp.getPosition(), ID, xpos, ypos);

         g.removePiece(pg);
         try {
            g.addPiece(OGp);
         } catch (InvalidPieceException e) {
            sc.close();
            System.out.println(e.getMessage());
         }
      }
      sc.close();
      return g;
   }
   
   public Puzzle readPuzzle() throws FileNotFoundException, IOException, InvalidFileFormatException{
      BufferedReader br = new BufferedReader(new FileReader(filename));

      String line = br.readLine();
      Scanner s = new Scanner(line);

      int nbRows, nbColumns, nbPieces;
      nbColumns = s.nextInt();
      nbRows = s.nextInt();
      nbPieces = s.nextInt();
      s.close();

      Puzzle g = new Puzzle(nbRows, nbColumns);

      for(int i = 0; i < nbPieces; i++){
         line = br.readLine();
         s = new Scanner(line);

         int width, height, xpos, ypos;
         width = s.nextInt();
         height = s.nextInt();
         xpos = s.nextInt();
         ypos = s.nextInt();
         s.close();

         Piece p = new Piece(height, width, xpos, ypos, i+1);
         try{
            g.addPiece(p);
         } catch(InvalidPieceException e){
            br.close();
            s.close();
            throw new InvalidFileFormatException("Error at line " + (i + 2) + " describes an invalid piece");
         }
      }

      s = new Scanner(br.readLine());
      int nbGoalPieces = s.nextInt();

      for(int i = 0; i < nbGoalPieces; i++){
         s = new Scanner(br.readLine());

         int ID, xpos, ypos;
         ID = s.nextInt();
         xpos = s.nextInt();
         ypos = s.nextInt();
         s.close();

         Piece OGp = g.getPiece(ID);
         GoalPiece pg = new GoalPiece(OGp.getWidth(), OGp.getHeight(), OGp.getPosition(), ID, xpos, ypos);
         g.removePiece(OGp);
         try{g.addPiece(pg);}
         catch(InvalidPieceException e){
            br.close();
            throw new InvalidFileFormatException("Error at line " + (i + nbPieces + 3) + " describes an invalid piece");
         }
      }

      br.close();
      return g;
   }
}
