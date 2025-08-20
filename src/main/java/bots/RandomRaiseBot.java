package bots;
import model.*;
import java.util.Random;

public class RandomRaiseBot implements PlayerStrategy {

  @Override
  public Decision decide(GameView view) {
    Random rand = new Random();
    if (rand.nextBoolean()) {
      int amtCall = view.toCall();
      if (amtCall == 0) {
        int amtToBet = (int) (view.pot() * rand.nextDouble(0.30, 1.30));
        if (amtToBet < view.bigBlindAmount()) {
          amtToBet = view.bigBlindAmount(); // need to make sure that we can afford this as well.
                                            // thinking that maybe I just create an all in amt which can always be shoved (regardless of minbet)
        } else if (amtToBet > view.myStack()) {
          amtToBet = view.myStack();
        }
      }
    }

    // logic to bet, raise, reraise randomly and safely

  }
}