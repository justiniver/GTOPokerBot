import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import model.*;
import model.rules.*;
import util.CardStrings;

public class TestPokerGame {
  private CardStrings cs;
  private PokerGame pokerGame;

  @Before
  public void init() {
    cs = new CardStrings();
    pokerGame = new PokerGame();
  }

  @Test
  public void testPokerGameConstructor() {
    Assert.assertEquals(Position.SMALL_BLIND, pokerGame.getP1().getPosition());
    Assert.assertEquals(Position.BIG_BLIND, pokerGame.getP2().getPosition());
    Assert.assertEquals(52, pokerGame.getDeck().getDeckSize());
    Assert.assertEquals(GameState.PREFLOP, pokerGame.getState());
  }

  @Test
  public void testDealingHoleCards() {
    pokerGame.dealHoleCards();
  }


}
