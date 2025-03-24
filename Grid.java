import java.util.Vector;

public class Grid{
   private final int nbRows;
   private final int nbColumns;
   private Vector<Piece> pieces;
   private boolean[][] occupiedPositions;

   public Grid(int nbRows, int nbColumns){
      this.nbRows = nbRows;
      this.nbColumns = nbColumns;
      pieces = new Vector<>();
      occupiedPositions = new boolean[nbRows][nbColumns];
   }

   public int getRows(){
      return nbRows;
   }

   public int getColumns(){
      return nbColumns;
   }

   public void addPiece(Piece p) throws InvalidPieceException{
      pieces.add(p);
      if(!verifyPosition(p))
         throw new InvalidPieceException();

      for(int i = p.getX(); i < p.getX() + p.getWidth(); i++){
         for(int j = p.getY(); j < p.getY() + p.getHeight(); j++){
            occupiedPositions[j-1][i-1] = true;
         }
      }
   }

   private boolean verifyPosition(Piece p){

      // verify the piece fits in the grid
      if(p.getX() < 1 || p.getY() < 1 || p.getX() + p.getWidth() > nbColumns + 1 || p.getY() + p.getHeight() > nbRows + 1)
         return false;
      // verify the piece's position is free
      for(int i = p.getX(); i < p.getX() + p.getWidth(); i++){
         for(int j = p.getY(); j < p.getY() + p.getHeight(); j++){
            if(occupiedPositions[j-1][i-1] == true){
               return false;
            }
         }
      }
      return true;
   }

   public boolean canSlide(Piece p, int newXpos, int newYpos){
      if(p.getX() != newXpos && p.getY() != newYpos)
         return false;

      if(p.getX() != newXpos)
         return canSlideHorizontaly(p, newXpos);

      return canSlideVerticaly(p, newYpos);
   }

   public boolean canSlide(Piece p, Coordinates newPos){
      return canSlide(p, newPos.getX(), newPos.getY());
   }

   private boolean canSlideHorizontaly(Piece p, int newXpos){
      if(newXpos < p.getX())
         return canSlideLeft(p, p.getX() - newXpos);

      return canSlideRight(p, newXpos - p.getX());
   }

   private boolean canSlideLeft(Piece p, int moveLength){

      if(p.getX() - moveLength < 0)
         return false;

      for(int i = p.getX() - moveLength; i < p.getX(); i++){
         for(int j = p.getY(); j < p.getY() + p.getHeight(); j++){
            if(occupiedPositions[j-1][i-1] == true)
               return false;
         }
      }

      return true;
   }

   private boolean canSlideRight(Piece p, int moveLength){

      if(p.getX() + + p.getWidth() - 1 + moveLength > nbColumns)
         return false;

      for(int i = p.getX() + p.getWidth(); i < p.getX() + p.getWidth() + moveLength; i++){
         for(int j = p.getY(); j < p.getY() + p.getHeight(); j++){
            if(occupiedPositions[j-1][i-1] == true)
               return false;
         }
      }

      return true;
   }

   private boolean canSlideVerticaly(Piece p, int newYpos){
      if(newYpos < p.getY())
         return canSlideUp(p, p.getY() - newYpos);

      return canSlideDown(p, newYpos - p.getY());
   }

   private boolean canSlideUp(Piece p, int moveLength){

      if(p.getY() - moveLength < 1)
         return false;

      for(int i = p.getY() - moveLength; i < p.getY(); i++){
         for(int j = p.getX(); j < p.getX() + p.getWidth(); j++){
            if(occupiedPositions[i-1][j-1] == true)
               return false;
         }
      }

      return true;
   }

   private boolean canSlideDown(Piece p, int moveLength){

      if(p.getY() + p.getHeight() - 1 + moveLength> nbRows)
         return false;

      for(int i = p.getY() + p.getHeight(); i < p.getY() + p.getHeight() + moveLength; i++){
         for(int j = p.getX(); j < p.getX() + p.getWidth(); j++){
            if(occupiedPositions[i-1][j-1] == true)
               return false;
         }
      }

      return true;
   }

   public boolean movePiece(Piece p, int newXpos, int newYpos) throws InvalidPieceException{
      if(!pieces.contains(p))
         throw new InvalidPieceException();
      if(!canSlide(p, newXpos, newYpos))
         return false;

      for(int i = p.getX(); i < p.getX() + p.getWidth(); i++){
         for(int j = p.getY(); j < p.getY() + p.getHeight(); j++){
            occupiedPositions[j-1][i-1] = false;
         }
      }
      p.setPosition(newXpos, newYpos);

      for(int i = p.getX(); i < p.getX() + p.getWidth(); i++){
         for(int j = p.getY(); j < p.getY() + p.getHeight(); j++){
            occupiedPositions[j-1][i-1] = true;
         }
      }
      return true;
   }

   public boolean movePiece(Piece p, Coordinates newPos) throws InvalidPieceException{
      return movePiece(p, newPos.getX(), newPos.getY());
   }

   public boolean slidePieceUp(Piece p, int d){
      for(int k = d; k > 0; k--){
         if(this.canSlideUp(p, k)){
            for(int i = p.getX(); i < p.getX() + p.getWidth(); i++){
               for(int j = p.getY(); j < p.getY() + p.getHeight(); j++){
                  occupiedPositions[j-1][i-1] = false;
               }
            }
            p.setPosition(p.getX(), p.getY() - k);

            for(int i = p.getX(); i < p.getX() + p.getWidth(); i++){
               for(int j = p.getY(); j < p.getY() + p.getHeight(); j++){
                  occupiedPositions[j-1][i-1] = true;
               }
            }
            return true;
         }
      }

      return false;
   }

   public boolean slidePieceDown(Piece p, int d){
      for(int k = d; k > 0; k--){
         if(this.canSlideDown(p, k)){
            for(int i = p.getX(); i < p.getX() + p.getWidth(); i++){
               for(int j = p.getY(); j < p.getY() + p.getHeight(); j++){
                  occupiedPositions[j-1][i-1] = false;
               }
            }
            p.setPosition(p.getX(), p.getY() + k);

            for(int i = p.getX(); i < p.getX() + p.getWidth(); i++){
               for(int j = p.getY(); j < p.getY() + p.getHeight(); j++){
                  occupiedPositions[j-1][i-1] = true;
               }
            }
            return true;
         }
      }

      return false;
   }

   public boolean slidePieceLeft(Piece p, int d){
      for(int k = d; k > 0; k--){
         if(this.canSlideLeft(p, k)){
            for(int i = p.getX(); i < p.getX() + p.getWidth(); i++){
               for(int j = p.getY(); j < p.getY() + p.getHeight(); j++){
                  occupiedPositions[j-1][i-1] = false;
               }
            }
            p.setPosition(p.getX() - k, p.getY());

            for(int i = p.getX(); i < p.getX() + p.getWidth(); i++){
               for(int j = p.getY(); j < p.getY() + p.getHeight(); j++){
                  occupiedPositions[j-1][i-1] = true;
               }
            }
            return true;
         }
      }

      return false;
   }

   public boolean slidePieceRight(Piece p, int d){
      for(int k = d; k > 0; k--){
         if(this.canSlideRight(p, k)){
            for(int i = p.getX(); i < p.getX() + p.getWidth(); i++){
               for(int j = p.getY(); j < p.getY() + p.getHeight(); j++){
                  occupiedPositions[j-1][i-1] = false;
               }
            }
            p.setPosition(p.getX() + k, p.getY());

            for(int i = p.getX(); i < p.getX() + p.getWidth(); i++){
               for(int j = p.getY(); j < p.getY() + p.getHeight(); j++){
                  occupiedPositions[j-1][i-1] = true;
               }
            }
            return true;
         }
      }

      return false;
   }

   public void printGrid(){
      for(int row = 1; row <= nbRows; row++){
         System.out.print("|");
         for(int column = 1; column <= nbColumns; column++){
            if(occupiedPositions[row-1][column-1])
               System.out.print("1|");
            else
               System.out.print("0|");
         }
         System.out.println();
      }
   }

   public Piece identify(int xpos, int ypos){
      for(Piece p : pieces)
         if(xpos >= p.getX() && xpos < p.getX() + p.getWidth() && ypos >= p.getY() && ypos < p.getY() + p.getHeight())
            return p;

      return null;
   }

   public Piece identify(Coordinates c){
      return identify(c.getX(), c.getY());
   }

   public Piece getPiece(int ID){
      for(Piece p : pieces)
         if(p.getID() == ID)
            return p;

      return null;
   }

   public void removePiece(Piece p){
      for(int i = p.getX(); i < p.getX() + p.getWidth(); i++){
         for(int j = p.getY(); j < p.getY() + p.getHeight(); j++){
            occupiedPositions[j-1][i-1] = false;
         }
      }
      pieces.remove(p);
   }

   public boolean goalReached(){
      for(Piece p : pieces){
         if (p instanceof PieceGoal) {
            if(!((PieceGoal) p).isAtGoalPosition())
               return false;
         }
      }
      return true;
   }
}
