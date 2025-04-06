package be.uliege.montefiore.oop.model;

import be.uliege.montefiore.oop.gui.*;

public class GoalPiece extends Piece{
   private final Coordinates goalPosition;   // coordinates of the top left corne
   private Color color;

   public GoalPiece(int width, int height, Coordinates position, int ID, Coordinates goalPosition){
      super(width, height, position, ID);
      this.goalPosition = goalPosition;
      color = generateColor();
   }

   public GoalPiece(int width, int height, Coordinates position, int ID, int goalPositionX, int goalPositionY){
      this(width, height, position, ID, new Coordinates(goalPositionX, goalPositionY));
   }

   public GoalPiece(int width, int height, int xpos, int ypos, int ID, int goalPositionX, int goalPositionY){
      this(width, height, new Coordinates(xpos, ypos), ID, new Coordinates(goalPositionX, goalPositionY));
   }

   private Color generateColor(){
      int r = (id * 123) % 256;
      int g = (id * 321) % 256;
      int b = (id * 231) % 256;
      return new Color(r, g, b);
   }

   public Color getColor(){
      return color;
   }

   public Coordinates getGoalPosition(){
      return goalPosition;
   }

   public int getGoalX(){
      return goalPosition.getX();
   }

   public int getGoalY(){
      return goalPosition.getY();
   }

   public boolean isAtGoalPosition(){
      return this.getX() == goalPosition.getX() && this.getY() == goalPosition.getY();
   }

   public Object clone(){
      Coordinates p = (Coordinates)(position.clone());
      Coordinates gp = (Coordinates)(goalPosition.clone());
      return new GoalPiece(width, height, p, id, gp);
   }
}