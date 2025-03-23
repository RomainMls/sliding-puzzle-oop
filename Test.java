public class Test{

   public static void main(String[] args){
      SpecificationFileReader sfr = new SpecificationFileReader("klotski.spzl");
      Grid g;
      try {
          g = sfr.readGrid();
      } catch (Exception e){
         System.out.println(e.toString());
         return;
      }

      g.printGrid();
   }

}
