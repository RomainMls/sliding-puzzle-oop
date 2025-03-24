public class PieceGoal extends Piece{
  private final Coordinates goalPosition;

  public PieceGoal(int width, int heigth, Coordinates position, int ID, Coordinates goalPosition) {
    super(width, heigth, position, ID);
    this.goalPosition = goalPosition;
  }

  public PieceGoal(int width, int heigth, Coordinates position, int ID, int goalPositionX, int goalPositionY) {
    this(width, heigth, position, ID, new Coordinates(goalPositionX, goalPositionY));
  }

  public boolean isAtGoalPosition(){
    return this.getX() == goalPosition.getX() && this.getY() == goalPosition.getY();
  }

  public Coordinates getGoalPosition(){
    return goalPosition;
  }
  
}