package be.uliege.montefiore.oop.GUI;

public class Color{
   private int r;
   private int g;
   private int b;

   public Color(int r, int g, int b){
      this.r = r;
      this.g = g;
      this.b = b;
   }

   public int getRed(){
      return r;
   }

   public int getGreen(){
      return g;
   }

   public int getBlue(){
      return b;
   }
}
