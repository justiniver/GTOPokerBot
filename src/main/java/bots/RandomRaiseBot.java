package bots;
import model.*;
import java.util.Random;

public class RandomRaiseBot implements PlayerStrategy {

  @Override
  public Decision decide(GameView view) {
    int amtCall = view.toCall();
    int myStack = view.myStack();
    int pot = view.pot();
    Random rand = new Random();

    // WHEN NO CURRENT BET
    if (amtCall == 0) {
      if (rand.nextDouble() > 0.5) {
        int amtToBet = (int) (pot * rand.nextDouble(0.30, 1.30));

        if (amtToBet > view.myStack()) {
          amtToBet = myStack;
        } else if (amtToBet < view.bigBlindAmount() || amtToBet < amtCall) {
          amtToBet = view.bigBlindAmount();
        }

        return new Decision(Action.BET, amtToBet);
      } else {
        return Decision.fold();
      }
    }

    // WHEN CURRENT BET
    else if (amtCall > 0 && amtCall < myStack) {
      if (rand.nextDouble() > 0.5) {
        return new Decision(Action.RAISE, myStack);
      }
    }

    // WHEN BET COVERS STACK
    else if (amtCall >= myStack) {
      if (rand.nextDouble() > 0.5) {
        return new Decision(Action.CALL, amtCall);
      }
    }

    return Decision.fold(); // dummy return for now
  }
}