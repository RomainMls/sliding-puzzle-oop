package be.uliege.montefiore.oop.gui;

import be.uliege.montefiore.oop.GUIException;
import be.uliege.montefiore.oop.SlidingPuzzleGUI;
import be.uliege.montefiore.oop.model.*;

public class GraphicalInterface
{
   private SlidingPuzzleGUI sp;
   private Puzzle puzzle;

   static final int maxWidth = 1600, maxHeight = 900;
   static final int standardCellSize = 100;

   private final int width, height;
   private final int innerCellSize;
   private final int spacing;
   private final int cellSize;

   public GraphicalInterface(Puzzle p) throws GUIException, DimensionsException
   {
      int maxCellsize = Math.min((int)(maxWidth/p.getColumns()), (int)(maxHeight/p.getRows()));
      if(maxCellsize < 20)
         throw new DimensionsException("Can't fit the puzzle properly within a " + maxWidth +"x" + maxHeight+ "window");

      cellSize = Math.min(standardCellSize, maxCellsize);
      innerCellSize = (int)(cellSize * 0.95);
      spacing = cellSize - innerCellSize;

      width = cellSize * p.getColumns();
      height = cellSize * p.getRows();
      sp = new SlidingPuzzleGUI(width, height);

      puzzle = p;
   }

   private void displayPiece(Piece p) throws GUIException
   {
      Color c = new Color(220, 220,220);

      if(p instanceof GoalPiece)
      {
         c = ((GoalPiece)p).getColor();
         if(((GoalPiece)p).isAtGoalPosition())
            c = new Color(255, 191, 0);
      }

      sp.newRectangle((p.getX() - 1) * cellSize + spacing,
                      (p.getY() - 1) * cellSize + spacing,
                      (p.getWidth() * cellSize) - 2 * spacing,
                      (p.getHeight() * cellSize) - 2 * spacing,
                      c.getRed(), c.getGreen(), c.getBlue());
   }

   private void displayGoalPieceIndicator(GoalPiece p) throws GUIException
   {
      Color c = p.getColor();
      if(p.isAtGoalPosition())
         c = new Color(255, 191, 0);

      // display top border

      //top
      sp.newRectangle((p.getGoalX()-1) * cellSize,
                      (p.getGoalY()-1) * cellSize,
                      p.getWidth() * cellSize, spacing,
                      c.getRed(), c.getGreen(), c.getBlue());

      // bottom
      sp.newRectangle((p.getGoalX() - 1) * cellSize,
                      (p.getGoalY()-1 + p.getHeight()) * cellSize - spacing,
                      p.getWidth() * cellSize, spacing,
                      c.getRed(), c.getGreen(), c.getBlue());

      // left
      sp.newRectangle((p.getGoalX()-1) * cellSize,
                      (p.getGoalY()-1) * cellSize, spacing,
                      p.getHeight() * cellSize,
                      c.getRed(), c.getGreen(), c.getBlue());

      // right
      sp.newRectangle((p.getGoalX()-1 + p.getWidth()) * cellSize - spacing,
                      (p.getGoalY()-1) * cellSize,
                      spacing, p.getHeight() * cellSize,
                      c.getRed(), c.getGreen(), c.getBlue());
   }

   public void display() throws GUIException
   {
      sp.startFrame();

      for(Piece p : puzzle.getPieces())
         if(p instanceof GoalPiece)
            displayGoalPieceIndicator((GoalPiece)p);

      for(Piece p : puzzle.getPieces())
         displayPiece(p);

      sp.endFrame();
   }

   public void displayVictory() throws GUIException
   {
      sp.startFrame();

      for(Piece p : puzzle.getPieces())
         if(p instanceof GoalPiece)
            displayPiece(p);

      sp.endFrame();
   }

   private Coordinates toModelCoordinates(int xpos, int ypos)
   {
      return new Coordinates(xpos/cellSize + 1, ypos/cellSize + 1);
   }

   public boolean nextMove()
   {
      int[] newMove = sp.nextMove();

      if(newMove == null)
         return true;         // user wants to quit

      Coordinates c1 = toModelCoordinates(newMove[0], newMove[1]);
      Coordinates c2 = toModelCoordinates(newMove[2], newMove[3]);
      GeoVector v = new GeoVector(c1, c2);

      try
      {
         int pieceID = puzzle.identify(c1);
         puzzle.slidePiece(pieceID, v);
      }
      catch (InvalidPieceException e)
      {
         // user did not click on a piece, nothing to do
      }
      return false;
   }

   public void endGame()
   {
      sp.endFrame();
   }
}
