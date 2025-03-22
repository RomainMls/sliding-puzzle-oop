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

   public void addPiece(Piece piece) throws InvalidPieceException{
      pieces.add(piece);
      if(!verifyPosition(piece))
         throw new InvalidPieceException();

      for(int i = piece.getX(); i < piece.getX() + piece.getWidth(); i++){
         for(int j = piece.getY(); j < piece.getY() + piece.getHeight(); j++){
            occupiedPositions[j-1][i-1] = true;
         }
      }
   }

   private Boolean verifyPosition(Piece piece){
      // verify the piece fits in the grid
      if(piece.getX() < 1 || piece.getY() < 1 || piece.getX() + piece.getWidth() > nbColumns + 1 || piece.getY() + piece.getHeight() > nbRows + 1)
         return false;

      // verify the piece's position is free
      for(int i = piece.getX(); i < piece.getX() + piece.getWidth(); i++){
         for(int j = piece.getY(); j < piece.getY() + piece.getHeight(); j++){
            if(occupiedPositions[i-1][j-1] == true)
               return false;
         }
      }

      return true;
   }

   public Boolean canSlide(Piece piece, int newXpos, int newYpos){
      if(piece.getX() != newXpos && piece.getY() != newYpos)
         return false;

      if(piece.getX() != newXpos)
         return canSlideHorizontaly(piece, newXpos);

      return canSlideVerticaly(piece, newYpos);
   }

   public Boolean canSlide(Piece piece, Coordinates newPos){
      return canSlide(piece, newPos.getX(), newPos.getY());
   }

   private Boolean canSlideHorizontaly(Piece piece, int newXpos){
      if(newXpos < piece.getX())
         return canSlideLeft(piece, piece.getX() - newXpos);

      return canSlideRight(piece, newXpos - piece.getX());
   }

   private Boolean canSlideLeft(Piece piece, int moveLength){

      if(piece.getX() - moveLength < 0)
         return false;

      for(int i = piece.getX() - moveLength; i < piece.getX(); i++){
         for(int j = piece.getY(); j < piece.getY() + piece.getHeight(); j++){
            if(occupiedPositions[j-1][i-1] == true)
               return false;
         }
      }

      return true;
   }

   private Boolean canSlideRight(Piece piece, int moveLength){

      if(piece.getX() + moveLength > nbColumns)
         return false;

      for(int i = piece.getX() + piece.getWidth(); i < piece.getX() + piece.getWidth() + moveLength; i++){
         for(int j = piece.getY(); j < piece.getY() + piece.getHeight(); j++){
            if(occupiedPositions[j-1][i-1] == true)
               return false;
         }
      }

      return true;
   }

   private Boolean canSlideVerticaly(Piece piece, int newYpos){
      if(newYpos < piece.getY())
         return canSlideUp(piece, piece.getY() - newYpos);

      return canSlideDown(piece, newYpos - piece.getY());
   }

   private Boolean canSlideUp(Piece piece, int moveLength){

      if(piece.getY() - moveLength < 1)
         return false;

      for(int i = piece.getY() - moveLength; i < piece.getY(); i++){
         for(int j = piece.getX(); j < piece.getX() + piece.getWidth(); j++){
            if(occupiedPositions[i-1][j-1] == true)
               return false;
         }
      }

      return true;
   }

   private Boolean canSlideDown(Piece piece, int moveLength){

      if(piece.getY() + moveLength > nbRows)
         return false;


      for(int i = piece.getY() + piece.getHeight(); i < piece.getY() + piece.getHeight() + moveLength; i++){
         for(int j = piece.getX(); j < piece.getX() + piece.getWidth(); j++){
            if(occupiedPositions[i-1][j-1] == true)
               return false;
         }
      }

      return true;
   }

   public Boolean movePiece(Piece piece, int newXpos, int newYpos) throws InvalidPieceException{
      if(!pieces.contains(piece))
         throw new InvalidPieceException();

      if(!canSlide(piece, newXpos, newYpos))
         return false;

      for(int i = piece.getX(); i < piece.getX() + piece.getWidth(); i++){
         for(int j = piece.getY(); j < piece.getY() + piece.getHeight(); j++){
            occupiedPositions[j-1][i-1] = false;
         }
      }

      piece.setPosition(newXpos, newYpos);

      for(int i = piece.getX(); i < piece.getX() + piece.getWidth(); i++){
         for(int j = piece.getY(); j < piece.getY() + piece.getHeight(); j++){
            occupiedPositions[j-1][i-1] = true;
         }
      }

      return true;
   }

   public Boolean movePiece(Piece piece, Coordinates newPos) throws InvalidPieceException{
      return movePiece(piece, newPos.getX(), newPos.getY());
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
}
