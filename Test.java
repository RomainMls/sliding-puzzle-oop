public class Test{

   public static void main(String[] args){
      int nbRows = 4, nbColumns = 4;
      Piece p1 = new Piece(1, 2, 1, 1, 1);
      Piece p2 = new Piece(1, 2, 1, 4, 2);
      Piece p3 = new Piece(1, 2, 1, 4, 3);

      Grid g = new Grid(nbRows, nbColumns);

      g.printGrid();
      System.out.println("adding piece 1");
      try {
         g.addPiece(p1);
      } catch (InvalidPieceException e) {
         System.out.println("non1");
         return;
      }
      g.printGrid();
      System.out.println("adding piece 2");
      try {
         g.addPiece(p2);
      } catch (InvalidPieceException e) {
         System.out.println("non2");
         return;
      }
      g.printGrid();
      System.out.println("adding piece 3");

      try{g.movePiece(p3, 1,1);}
      catch(InvalidPieceException e)
      {System.out.println("non");}
      g.printGrid();
      System.out.println("moving p2 to 1, 2");
      try{g.movePiece(p2, 1, 2);}
      catch(InvalidPieceException e)
      {System.err.println("non");}
      g.printGrid();

   }

}
