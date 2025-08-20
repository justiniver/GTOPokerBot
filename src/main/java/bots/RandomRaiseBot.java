package bots;
import model.*;
import java.util.Random;

public class RandomRaiseBot implements PlayerStrategy {

  @Override
  public Decision decide(GameView view) {
    int amtCall = view.toCall();
    if (amtCall == 0) {
      Random rand = new Random();
      if (rand.nextBoolean()) {
        int amtToBet = (int) (view.pot() * rand.nextDouble(0.30, 1.30));

        if (amtToBet > view.myStack()) {
          amtToBet = view.myStack();
        } else if (amtToBet < view.bigBlindAmount() || amtToBet < amtCall) {
          amtToBet = view.bigBlindAmount();
        }

        return new Decision(Action.BET, amtToBet);
      } else {
        return Decision.fold();
      }
    }

    return Decision.fold();
  }
}