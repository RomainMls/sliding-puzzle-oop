package be.uliege.montefiore.oop.model;

public class Piece implements Cloneable{
   protected final int height;
   protected final int width;
   protected Coordinates position;    // coordinates of the top left corner
   protected final int id;

   public Piece(int height, int width, Coordinates position, int id){
      this.height = height;
      this.width = width;
      this.position = position;
      this.id = id;
   }

   public Piece(int height, int width, int xpos, int ypos, int id){
      this(height, width, new Coordinates(xpos, ypos), id);
   }

   public int getHeight(){
      return height;
   }

   public int getWidth(){
      return width;
   }

   public Coordinates getPosition(){
      return (Coordinates)(position.clone());
   }

   public int getX(){
      return position.getX();
   }

   public int getY(){
      return position.getY();
   }

   public int getID(){
      return id;
   }

   public void setPosition(int xpos, int ypos){
      position.setX(xpos);
      position.setY(ypos);
   }

   public void setPosition(Coordinates position){
      this.position = position;
   }

   public Object clone(){
      Piece clone;
      try
      {
         clone = (Piece) super.clone();
         clone.position = (Coordinates) position.clone();
      }
      catch(CloneNotSupportedException e)
      {
         throw new InternalError("Unable to clone");
      }
      return clone;
   }
}
