package be.uliege.montefiore.oop.GUI;

import be.uliege.montefiore.oop.GUIException;
import be.uliege.montefiore.oop.SlidingPuzzleGUI;
import be.uliege.montefiore.oop.model.*;
import be.uliege.montefiore.oop.reader.*;

import java.io.FileNotFoundException;
import java.io.IOException;

public class GraphicalInterface {
   private SlidingPuzzleGUI sp;
   private Grid grid;
   private final int width, height;
   private int columnSize, rowSize;
   private int offsetX;
   private int offsetY;

   public GraphicalInterface(int width, int height, String specificationFile) throws DimensionsException, GUIException, FileNotFoundException, IOException, WronglyFormattedFileException{
      sp = new SlidingPuzzleGUI(width, height);

      SpecificationFileReader sf = new SpecificationFileReader(specificationFile);
      grid = sf.readGrid();

      if(height < grid.getRows() || width < grid.getColumns())
         throw new DimensionsException("Wrong values for window size");

      this.width = width;
      this.height = height;
      int cellSize = Math.min(width / grid.getColumns(), height / grid.getRows());
      columnSize = cellSize;
      rowSize = cellSize;
      offsetX = (width - (columnSize * grid.getColumns())) / 2;
      offsetY = (height - (rowSize * grid.getRows())) / 2;
   }

   public void display() throws GUIException{
      sp.startFrame();

      int space = Math.min((width / 100) * 2, (height / 100) * 2);


      for(int y = 1; y <= grid.getRows(); y++){
         for(int x = 1; x <= grid.getColumns(); x++){
            Piece p = grid.identify(x, y);
            if(p != null){
               Color c = p.getColor();
               sp.newRectangle((p.getX() - 1) * columnSize + offsetX + space / 2, (p.getY() - 1) * rowSize + offsetY + space / 2,
               p.getWidth() * columnSize - space, p.getHeight() * rowSize - space,
               c.getRed(), c.getGreen(), c.getBlue());
            }
         }
      }
      sp.endFrame();
   }

   public boolean nextMove(){
      int[] newMove = sp.nextMove();
      if(newMove == null)
      return true;

      Coordinates c1 = new Coordinates(((newMove[0] - offsetX) / columnSize) + 1, ((newMove[1] - offsetY)/rowSize) + 1);
      Coordinates c2 = new Coordinates(((newMove[2] - offsetX) / columnSize) + 1, ((newMove[3] - offsetY) / rowSize) + 1);

      Piece p = grid.identify(c1);

      if(p == null)
      return false;

      GeoVector v = new GeoVector(c1, c2);
      try {
         grid.slidePiece(p, v);
      } catch (InvalidPieceException e) {
      }
      return false;
   }


   public boolean checkIfWin(){
      return grid.goalReached();
   }

   public void endGame(){
      sp.endFrame();
   }
}
