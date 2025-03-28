package be.uliege.montefiore.oop.model;

public class InvalidPieceException extends Exception{

   public InvalidPieceException(){
      super();
   }

   public InvalidPieceException(String s){
      super(s);
   }
}
