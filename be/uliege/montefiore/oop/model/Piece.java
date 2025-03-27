package be.uliege.montefiore.oop.model;

public class Piece{
   private final int height;
   private final int width;
   private Coordinates position;
   private final int ID;
   private Color color;

   public Piece(int height, int width, Coordinates position, int ID){
      this.height = height;
      this.width = width;
      this.position = position;
      this.ID = ID;
      this.color = generatePieceColor();
   }

   public Piece(int height, int width, int xpos, int ypos, int ID){
      this(height, width, new Coordinates(xpos, ypos), ID);
   }

   public int getHeight(){
      return height;
   }

   public int getWidth(){
      return width;
   }

   public Coordinates getPosition(){
      return position;
   }

   public int getX(){
      return position.getX();
   }

   public int getY(){
      return position.getY();
   }

   public int getID(){
      return ID;
   }

   public void setPosition(Coordinates position){
      this.position = position;
   }

   public void setPosition(int xpos, int ypos){
      this.position = new Coordinates(xpos, ypos);
   }

   private Color generatePieceColor(){
      int r = (ID * 123) % 256;
      int g = (ID * 321) % 256;
      int b = (ID * 231) % 256;
      return new Color(r, g, b);
   }

   public Color getColor(){
      return color;
   }
}
