public class Coordinates{
   private int xpos;
   private int ypos;

   public Coordinates(int xpos, int ypos){
      this.xpos = xpos;
      this.ypos = ypos;
   }

   public int getX(){
      return xpos;
   }

   public int getY(){
      return ypos;
   }

   public void setX(int xpos){
      this.xpos = xpos;
   }

   public void setY(int ypos){
      this.ypos = ypos;
   }

   public static Boolean areEqual(Coordinates c1, Coordinates c2){
      return c1.xpos == c2.xpos && c1.ypos == c2.ypos;
   }

   public Boolean equals(Coordinates c2){
      return this.xpos == c2.xpos && this.ypos == c2.ypos;
   }
}
