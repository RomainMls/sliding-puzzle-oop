package be.uliege.montefiore.oop.model;

public class Piece{
   private final int height;
   private final int width;
   private Coordinates position;
   protected final int ID;

   public Piece(int height, int width, Coordinates position, int ID){
      this.height = height;
      this.width = width;
      this.position = position;
      this.ID = ID;
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

   public Coordinates getPosition2(){
      return (Coordinates)(position.clone());
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

   public void setPosition(int xpos, int ypos){
      this.position = new Coordinates(xpos, ypos);
   }

   public void setPosition(Coordinates position){
      this.position = position;
   }
}
