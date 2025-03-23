public class Test{

   public static void main(String[] args){
      SpecificationFileReader sfr = new SpecificationFileReader("klotskipasbien.spzl");
      Grid g;
      try {
          g = sfr.readGrid();
      } catch (Exception e){
         System.out.println(e.toString());
         return;
      }

      g.printGrid();

      if(g.goalReached())
         System.out.println("YES");
      else
         System.out.println("NON");
   }

}
