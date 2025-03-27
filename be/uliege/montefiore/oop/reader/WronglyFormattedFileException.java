package be.uliege.montefiore.oop.reader;
public class WronglyFormattedFileException extends Exception{
   public WronglyFormattedFileException(){
      super();
   }

   public WronglyFormattedFileException(String s){
      super(s);
   }
}
