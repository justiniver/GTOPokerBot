package bots;
import model.*;

public class SimpleCheckCallBot implements PlayerStrategy {
  @Override
  public Decision decide(GameView view) {
    if (view.toCall() > 0) {
      return new Decision(Action.CALL, view.toCall());
    }
    return Decision.check();
  }
}
