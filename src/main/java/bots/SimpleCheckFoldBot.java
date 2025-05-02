package bots;

import model.Decision;
import model.GameView;
import model.PlayerStrategy;

public class SimpleCheckFoldBot implements PlayerStrategy {
  @Override
  public Decision decide(GameView view) {
    if (view.toCall() == 0) {
      return Decision.check();
    }

    return Decision.fold();
  }
}
