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

      }
    }
  }
}