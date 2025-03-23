
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

   public Grid readGrid() throws FileNotFoundException, IOException, WronglyFormatedFileException{
      BufferedReader br;
      br = new BufferedReader(new FileReader(filename));

      Scanner s = new Scanner(br.readLine());

      int nbRows, nbColumns, nbPieces;
      nbColumns = s.nextInt();
      nbRows = s.nextInt();
      nbPieces = s.nextInt();

      Grid g = new Grid(nbRows, nbColumns);

      for(int i = 0; i < nbPieces; i++){
         s = new Scanner(br.readLine());
         int width, heigth, xpos, ypos;

         width = s.nextInt();
         heigth = s.nextInt();
         xpos = s.nextInt();
         ypos = s.nextInt();

         Piece p = new Piece(heigth, width, xpos, ypos, i+1);
         try{g.addPiece(p);}
         catch(InvalidPieceException e){
            br.close();
            s.close();
            throw new WronglyFormatedFileException("Error at line" + i+1 + " describes an invalid piece");
         }
      }
      int nbGoalPieces = (new Scanner(br.readLine())).nextInt();

      for(int i = 0; i < nbGoalPieces; i++){
         s = new Scanner(br.readLine());
         int ID, xpos, ypos;

         ID = s.nextInt();
         xpos = s.nextInt();
         ypos = s.nextInt();

         Piece OGp = g.getPiece(ID);
         PieceGoal pg = new PieceGoal(OGp.getWidth(), OGp.getHeight(), OGp.getPosition(), ID, xpos, ypos);
         g.removePiece(OGp);
         try{g.addPiece(pg);}
         catch(InvalidPieceException e){
            br.close();
            s.close();
            throw new WronglyFormatedFileException("");
         }
      }

      br.close();
      s.close();

      return g;
   }
}
