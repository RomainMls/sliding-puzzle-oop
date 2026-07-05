package be.uliege.montefiore.oop.reader;

public class InvalidFileFormatException extends Exception{
   public InvalidFileFormatException(){
      super();
   }

   public InvalidFileFormatException(String s){
      super(s);
   }
}
