public class Piece{
   private int height;
   private int width;
   private Coordinates position;
   private final int ID;

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
}
