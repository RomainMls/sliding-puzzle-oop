package be.uliege.montefiore.oop.model;

import java.util.Vector;

public class Puzzle
{
   private final int nbRows;
   private final int nbColumns;
   private Vector<Piece> pieces;
   private boolean[][] occupiedPositions;
   private int moveCounter = 0;

   public Puzzle(int nbRows, int nbColumns)
   {
      this.nbRows = nbRows;
      this.nbColumns = nbColumns;
      pieces = new Vector<>();
      occupiedPositions = new boolean[nbRows][nbColumns];
   }

   public int getRows()
   {
      return nbRows;
   }

   public int getColumns()
   {
      return nbColumns;
   }

   public int getmoveCount()
   {
      return moveCounter;
   }

   private Piece identify(int xpos, int ypos)
   {
      for(Piece p : pieces)
         if(xpos >= p.getX() && xpos < p.getX() + p.getWidth() && ypos >= p.getY() && ypos < p.getY() + p.getHeight())
            return p;

      return null;
   }

   private Piece identify(Coordinates c)
   {
      return identify(c.getX(), c.getY());
   }

   private Piece identify(int id)
   {
      for(Piece p : pieces)
         if(p.getID() == id)
            return p;

      return null;
   }

   public Piece getPiece(int id)
   {
      Piece p = identify(id);
      if(p != null)
         return (Piece)(p.clone());
         // we return a clone of the piece in order to preserve
         // the puzzle's integrity. We do not trust the user of the puzzle

      return null;
   }

   public Piece getPiece(int xpos, int ypos)
   {
      Piece p = identify(xpos, ypos);
      if(p != null)
         return (Piece)(p.clone());
         // we return a clone of the piece in order to preserve
         // the puzzle's integrity. We do not trust the user of the puzzle

      return null;
   }

   public Piece getPiece(Coordinates c)
   {
      return getPiece(c.xpos, c.ypos);
   }

   public int getID(int xpos, int ypos) throws InvalidPieceException
   {
      Piece p = identify(xpos, ypos);
      if(p != null)
         return p.getID();

      throw new InvalidPieceException();
   }

   public int getID(Coordinates c) throws InvalidPieceException
   {
      return getID(c.xpos, c.ypos);
   }

   public Piece[] getPieces()
   {
      int size = pieces.size();
      Piece[] array = new Piece[size];

      for(int i = 0; i < size; i++)
         array[i] = (Piece)(pieces.get(i)).clone();
         // we return a clone of the piece in order to preserve
         // the puzzle's integrity. We do not trust the user of the puzzle

      return array;
   }

   private void addPiece(Piece p) throws InvalidPieceException, PuzzleFullException
   {
      if(pieces.contains(p))
         return;

      for(Piece p2 : pieces)
         if(p.getID() == p2.getID())
            throw new InvalidPieceException("Cannot add given piece to the puzzle: id(" + p.getID() + ") already used");

      if(!verifyPosition(p))
         throw new InvalidPieceException("Cannot add piece to the puzzle: piece given does not fit in the puzzle");

      pieces.add(p);

      for(int i = p.getX(); i < p.getX() + p.getWidth(); i++)
         for(int j = p.getY(); j < p.getY() + p.getHeight(); j++)
            occupiedPositions[j-1][i-1] = true;

      if(isFull())
         throw new PuzzleFullException();
   }

   public void addNewPiece(int xpos, int ypos, int width, int height, int id) throws InvalidPieceException, PuzzleFullException
   {
      addPiece(new Piece(height, width, xpos, ypos, id));
   }

   public void transformPieceToGoalPiece(int id, int goalPositionX, int goalPositionY) throws InvalidPieceException, PuzzleFullException
   {
      Piece p = identify(id);
      if(p == null)
         throw new InvalidPieceException("Transforming piece to goal piece: id given refers to no piece");

      GoalPiece gp = new GoalPiece(p.getWidth(), p.getHeight(), p.getPosition(), id, goalPositionX, goalPositionY);
      removePiece(id);
      addPiece(gp);
   }

   public boolean isFull()
   {
      for(int i = 0; i < nbRows; i++)
         for(int j = 0; j < nbColumns; j++)
            if(occupiedPositions[i][j] == false)
               return false;

      return true;
   }

   public void removePiece(int id)
   {
      Piece p = identify(id);
      if(p == null)
         return;

      for(int i = p.getX(); i < p.getX() + p.getWidth(); i++)
         for(int j = p.getY(); j < p.getY() + p.getHeight(); j++)
            occupiedPositions[j-1][i-1] = false;

      pieces.remove(p);
   }

   public boolean goalReached()
   {
      for(Piece p : pieces)
         if (p instanceof GoalPiece)
            if(!((GoalPiece) p).isAtGoalPosition())
               return false;

      return true;
   }

   private boolean verifyPosition(Piece p)
   {
      // verify the piece fits in the Puzzle
      if(p.getX() < 1 || p.getY() < 1 || p.getX() + p.getWidth() - 1 > nbColumns || p.getY() + p.getHeight() - 1> nbRows)
         return false;

      // verify the piece's position is free
      for(int i = p.getX(); i < p.getX() + p.getWidth(); i++)
         for(int j = p.getY(); j < p.getY() + p.getHeight(); j++)
            if(occupiedPositions[j-1][i-1] == true)
               return false;

      return true;
   }

   private boolean canSlideLeft(Piece p, int d) throws InvalidPieceException
   {
      if(!pieces.contains(p))
         throw new InvalidPieceException("Piece doesn't belong to the Puzzle");

      // verify end position
      if(p.getX() - d < 1)
         return false;

      // for each position the piece will take during the sliding, verify collisions
      for(int i = p.getX() - d; i < p.getX(); i++)
         for(int j = p.getY(); j < p.getY() + p.getHeight(); j++)
            if(occupiedPositions[j-1][i-1] == true)
               return false;

      return true;
   }

   private boolean canSlideRight(Piece p, int d) throws InvalidPieceException
   {
      if(!pieces.contains(p))
         throw new InvalidPieceException("Piece doesn't belong to the Puzzle");

      // verify end position
      if(p.getX() + + p.getWidth() - 1 + d > nbColumns)
         return false;

      // for each position the piece will take during the sliding, verify collisions
      for(int i = p.getX() + p.getWidth(); i < p.getX() + p.getWidth() + d; i++)
         for(int j = p.getY(); j < p.getY() + p.getHeight(); j++)
            if(occupiedPositions[j-1][i-1] == true)
               return false;

      return true;
   }

   private boolean canSlideUp(Piece p, int d) throws InvalidPieceException
   {
      if(!pieces.contains(p))
         throw new InvalidPieceException("Piece doesn't belong to the Puzzle");

      // verify end position
      if(p.getY() - d < 1)
         return false;

      // for each position the piece will take during the sliding, verify collisions
      for(int i = p.getY() - d; i < p.getY(); i++)
         for(int j = p.getX(); j < p.getX() + p.getWidth(); j++)
            if(occupiedPositions[i-1][j-1] == true)
               return false;

      return true;
   }

   private boolean canSlideDown(Piece p, int d) throws InvalidPieceException
   {
      if(!pieces.contains(p))
         throw new InvalidPieceException("Piece doesn't belong to the Puzzle");

      // verify end position
      if(p.getY() + p.getHeight() - 1 + d> nbRows)
         return false;

      // for each position the piece will take during the sliding, verify collisions
      for(int i = p.getY() + p.getHeight(); i < p.getY() + p.getHeight() + d; i++)
         for(int j = p.getX(); j < p.getX() + p.getWidth(); j++)
            if(occupiedPositions[i-1][j-1] == true)
               return false;

      return true;
   }

   public boolean slideUp(int id, int d) throws InvalidPieceException
   {
      Piece p = identify(id);
      if(p == null)
         throw new InvalidPieceException("Piece doesn't belong to the Puzzle");

      for(int k = d; k > 0; k--)    // let's find the furthest up the piece can slide
      {
         if(this.canSlideUp(p, k))
         {
            for(int i = p.getX(); i < p.getX() + p.getWidth(); i++)
               for(int j = p.getY(); j < p.getY() + p.getHeight(); j++)
                  occupiedPositions[j-1][i-1] = false;

            p.setPosition(p.getX(), p.getY() - k);

            for(int i = p.getX(); i < p.getX() + p.getWidth(); i++)
               for(int j = p.getY(); j < p.getY() + p.getHeight(); j++)
                  occupiedPositions[j-1][i-1] = true;

            moveCounter++;
            return true;
         }
      }

      return false;
   }

   public boolean slideDown(int id, int d) throws InvalidPieceException
   {
      Piece p = identify(id);
      if(p == null)
         throw new InvalidPieceException("Piece doesn't belong to the Puzzle");

      for(int k = d; k > 0; k--)    // let's find the furthest down the piece can slide
      {
         if(this.canSlideDown(p, k))
         {
            for(int i = p.getX(); i < p.getX() + p.getWidth(); i++)
               for(int j = p.getY(); j < p.getY() + p.getHeight(); j++)
                  occupiedPositions[j-1][i-1] = false;

            p.setPosition(p.getX(), p.getY() + k);

            for(int i = p.getX(); i < p.getX() + p.getWidth(); i++)
               for(int j = p.getY(); j < p.getY() + p.getHeight(); j++)
                  occupiedPositions[j-1][i-1] = true;

            moveCounter++;
            return true;
         }
      }

      return false;
   }

   public boolean slideLeft(int id, int d) throws InvalidPieceException{
      Piece p = identify(id);
      if(p == null)
         throw new InvalidPieceException("Piece doesn't belong to the Puzzle");

      for(int k = d; k > 0; k--)    // let's find the furthest left the piece can slide
      {
         if(this.canSlideLeft(p, k))
         {
            for(int i = p.getX(); i < p.getX() + p.getWidth(); i++)
               for(int j = p.getY(); j < p.getY() + p.getHeight(); j++)
                  occupiedPositions[j-1][i-1] = false;

            p.setPosition(p.getX() - k, p.getY());

            for(int i = p.getX(); i < p.getX() + p.getWidth(); i++)
               for(int j = p.getY(); j < p.getY() + p.getHeight(); j++)
                  occupiedPositions[j-1][i-1] = true;

            moveCounter++;
            return true;
         }
      }

      return false;
   }

   public boolean slideRight(int id, int d) throws InvalidPieceException
   {
      Piece p = identify(id);
      if(p == null)
         throw new InvalidPieceException("Piece doesn't belong to the Puzzle");

      for(int k = d; k > 0; k--)    // let's find the furthest right the piece can slide
      {
         if(this.canSlideRight(p, k))
         {
            for(int i = p.getX(); i < p.getX() + p.getWidth(); i++)
               for(int j = p.getY(); j < p.getY() + p.getHeight(); j++)
                  occupiedPositions[j-1][i-1] = false;

            p.setPosition(p.getX() + k, p.getY());

            for(int i = p.getX(); i < p.getX() + p.getWidth(); i++)
               for(int j = p.getY(); j < p.getY() + p.getHeight(); j++)
                  occupiedPositions[j-1][i-1] = true;

            moveCounter++;
            return true;
         }
      }

      return false;
   }

   public boolean slidePiece(int id, GeoVector v) throws InvalidPieceException
   {
      if(!v.isSingleAxis())
         return false;

      if(v.getY() > 0)
         return slideDown(id, v.getY());

      if(v.getY() < 0)
         return slideUp(id, -v.getY());

      if(v.getX() > 0)
         return slideRight(id, v.getX());

      return slideLeft(id, -v.getX());
   }
}
