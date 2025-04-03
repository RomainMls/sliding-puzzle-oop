package be.uliege.montefiore.oop.model;

public class Coordinates implements Cloneable{
   protected int xpos;
   protected int ypos;

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

   public boolean equals(Object o){
      if(o == null || !(o instanceof Coordinates))
         return false;

      Coordinates c = (Coordinates) o;

      return this.xpos == c.xpos && this.ypos == c.ypos;
   }

   public Object clone(){
      return new Coordinates(xpos, ypos);
   }
}
