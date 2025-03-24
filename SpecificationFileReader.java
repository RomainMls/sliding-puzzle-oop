import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class SpecificationFileReader{
   private final String filename;

   public SpecificationFileReader(String filename){
      this.filename = filename;
   }

   public Grid readGrid() throws FileNotFoundException, IOException, WronglyFormattedFileException{
      BufferedReader br = new BufferedReader(new FileReader(filename));

      String line = br.readLine();
      Scanner s = new Scanner(line);

      int nbRows, nbColumns, nbPieces;
      nbColumns = s.nextInt();
      nbRows = s.nextInt();
      nbPieces = s.nextInt();
      s.close();

      Grid g = new Grid(nbRows, nbColumns);

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
            throw new WronglyFormattedFileException("Error at line " + (i + 2) + " describes an invalid piece");
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
         PieceGoal pg = new PieceGoal(OGp.getWidth(), OGp.getHeight(), OGp.getPosition(), ID, xpos, ypos);
         g.removePiece(OGp);
         try{g.addPiece(pg);}
         catch(InvalidPieceException e){
            br.close();
            throw new WronglyFormattedFileException("Error at line " + (i + nbPieces + 3) + " describes an invalid piece");
         }
      }

      br.close();
      return g;
   }
}
