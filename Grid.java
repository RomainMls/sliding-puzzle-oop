import java.util.Vector;

public class Grid{
   private final int nbRows;
   private final int nbColumns;
   private Vector<Piece> pieces;
   private Boolean[][] occupiedPositions;

   public Grid(int nbRows, int nbColumns){
      this.nbRows = nbRows;
      this.nbColumns = nbColumns;
      pieces = new Vector<>();
      occupiedPositions = new Boolean[nbRows][nbColumns];
      for(int i = 0; i < nbRows; i++){
         for(int j = 0; j < nbColumns; j++)
            occupiedPositions[i][j] = false;
      }
   }

   public void addPiece(Piece p) throws InvalidPieceException{
      pieces.add(p);
      if(!verifyPosition(p))
         throw new InvalidPieceException();

      for(int i = p.getX(); i < p.getX() + p.getWidth(); i++){
         for(int j = p.getY(); j < p.getY() + p.getHeigth(); j++){
            occupiedPositions[j-1][i-1] = true;
         }
      }
   }

   private Boolean verifyPosition(Piece p){

      // verify the piece fits in the grid
      if(p.getX() < 1 || p.getY() < 1 || p.getX() + p.getWidth() > nbColumns + 1 || p.getY() + p.getHeigth() > nbRows + 1)
         return false;
      // verify the piece's position is free
      for(int i = p.getX(); i < p.getX() + p.getWidth(); i++){
         for(int j = p.getY(); j < p.getY() + p.getHeigth(); j++){
            if(occupiedPositions[j-1][i-1] == true){
               return false;
            }
         }
      }
      return true;
   }

   public Boolean canSlide(Piece p, int newXpos, int newYpos){
      if(p.getX() != newXpos && p.getY() != newYpos)
         return false;

      if(p.getX() != newXpos)
         return canSlideHorizontaly(p, newXpos);

      return canSlideVerticaly(p, newYpos);
   }

   public Boolean canSlide(Piece p, Coordinates newPos){
      return canSlide(p, newPos.getX(), newPos.getY());
   }

   private Boolean canSlideHorizontaly(Piece p, int newXpos){
      if(newXpos < p.getX())
         return canSlideLeft(p, p.getX() - newXpos);

      return canSlideRight(p, newXpos - p.getX());
   }

   private Boolean canSlideLeft(Piece p, int moveLength){

      if(p.getX() - moveLength < 0)
         return false;

      for(int i = p.getX() - moveLength; i < p.getX(); i++){
         for(int j = p.getY(); j < p.getY() + p.getHeigth(); j++){
            if(occupiedPositions[j-1][i-1] == true)
               return false;
         }
      }

      return true;
   }

   private Boolean canSlideRight(Piece p, int moveLength){

      if(p.getX() + moveLength > nbColumns)
         return false;

      for(int i = p.getX() + p.getWidth(); i < p.getX() + p.getWidth() + moveLength; i++){
         for(int j = p.getY(); j < p.getY() + p.getHeigth(); j++){
            if(occupiedPositions[j-1][i-1] == true)
               return false;
         }
      }

      return true;
   }

   private Boolean canSlideVerticaly(Piece p, int newYpos){
      if(newYpos < p.getY())
         return canSlideUp(p, p.getY() - newYpos);

      return canSlideDown(p, newYpos - p.getY());
   }

   private Boolean canSlideUp(Piece p, int moveLength){

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

   private Boolean canSlideDown(Piece p, int moveLength){

      if(p.getY() + moveLength > nbRows)
         return false;


      for(int i = p.getY() + p.getHeigth(); i < p.getY() + p.getHeigth() + moveLength; i++){
         for(int j = p.getX(); j < p.getX() + p.getWidth(); j++){
            if(occupiedPositions[i-1][j-1] == true)
               return false;
         }
      }

      return true;
   }

   public Boolean movePiece(Piece p, int newXpos, int newYpos) throws InvalidPieceException{
      if(!pieces.contains(p))
         throw new InvalidPieceException();

      if(!canSlide(p, newXpos, newYpos))
         return false;

      for(int i = p.getX(); i < p.getX() + p.getWidth(); i++){
         for(int j = p.getY(); j < p.getY() + p.getHeigth(); j++){
            occupiedPositions[j-1][i-1] = false;
         }
      }

      p.setPosition(newXpos, newYpos);

      for(int i = p.getX(); i < p.getX() + p.getWidth(); i++){
         for(int j = p.getY(); j < p.getY() + p.getHeigth(); j++){
            occupiedPositions[j-1][i-1] = true;
         }
      }

      return true;
   }

   public Boolean movePiece(Piece p, Coordinates newPos) throws InvalidPieceException{
      return movePiece(p, newPos.getX(), newPos.getY());
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
         if(xpos >= p.getX() && xpos < p.getX() + p.getWidth() && ypos >= p.getY() && ypos < p.getY() + p.getHeigth())
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
         for(int j = p.getY(); j < p.getY() + p.getHeigth(); j++){
            occupiedPositions[j-1][i-1] = false;
         }
      }
      pieces.remove(p);
   }

   public Boolean goalReached(){
      for(Piece p : pieces){
         if (p instanceof PieceGoal) {
            if(!((PieceGoal) p).isAtGoalPosition())
               return false;
         }
      }
      return true;
   }
}
