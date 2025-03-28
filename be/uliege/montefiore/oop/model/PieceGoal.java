package be.uliege.montefiore.oop.model;

public class PieceGoal extends Piece{
   private final Coordinates goalPosition;

   public PieceGoal(int width, int height, Coordinates position, int ID, Coordinates goalPosition){
      super(width, height, position, ID);
      this.goalPosition = goalPosition;
   }

   public PieceGoal(int width, int height, Coordinates position, int ID, int goalPositionX, int goalPositionY){
      this(width, height, position, ID, new Coordinates(goalPositionX, goalPositionY));
   }

   public PieceGoal(int width, int height, int xpos, int ypos, int ID, int goalPositionX, int goalPositionY){
      this(width, height, new Coordinates(xpos, ypos), ID, new Coordinates(goalPositionX, goalPositionY));
   }

   public boolean isAtGoalPosition(){
      return this.getX() == goalPosition.getX() && this.getY() == goalPosition.getY();
   }

   public Coordinates getGoalPosition(){
      return goalPosition;
   }
}