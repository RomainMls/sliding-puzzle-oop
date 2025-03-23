public class PieceGoal extends Piece{
  private final Coordinates goalPosition;

  public PieceGoal(int height, int width, Coordinates position, int ID, Coordinates goalPosition) {
    super(height, width, position, ID);
    this.goalPosition = goalPosition;
  }

  public PieceGoal(int height, int width, Coordinates position, int ID, int goalPositionX, int goalPositionY) {
    this(height, width, position, ID, new Coordinates(goalPositionX, goalPositionY));
  }

  public Boolean isAtGoalPosition(){
    return this.getX() == goalPosition.getX() && this.getY() == goalPosition.getY();
  }
  
}