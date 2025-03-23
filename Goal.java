import java.util.Vector;

public class Goal {
  private final int nbPiecesGoal;
  private Vector<PieceGoal> piecesGoal;

  public Goal(int nbPiecesGoal){
    this.nbPiecesGoal = nbPiecesGoal;
    piecesGoal = new Vector<>(nbPiecesGoal);
  }

  public Boolean areAtGoalPosition(){
    for(PieceGoal p : piecesGoal){
      if(!p.isAtGoalPosition())
        return false;
    }
    return true;
  }
}
