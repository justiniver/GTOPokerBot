import org.junit.Before;
import org.junit.Test;

import model.BettingRound;
import model.Player;
import model.Position;

public class TestBettingRound {
  private Player playerSB;
  private Player playerBB;

  @Before
  public void init() {
    Player playerSB = new Player(Position.SMALL_BLIND);
    Player playerBB = new Player(Position.BIG_BLIND);
  }

  @Test
  public void testBettingActionFold() {

  }
}
