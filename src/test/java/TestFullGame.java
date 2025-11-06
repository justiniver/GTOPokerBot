import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import model.BettingRound;
import model.Player;
import model.PokerGame;
import model.Position;
import model.rules.HandEvaluation;
import util.CardStrings;

/**
 * Testing a full game using PokerGame and BettingRound
 */
public class TestFullGame {
  private HandEvaluation eval;
  private PokerGame pokerGame;
  private CardStrings cs;

  @Before
  public void init() {
    this.cs = new CardStrings();
    Player playerSB  = new Player(Position.SMALL_BLIND, 1000);
    Player playerBB = new Player(Position.BIG_BLIND, 1000);
    this.pokerGame = new PokerGame(true, 5, 10, playerSB, playerBB);
  }

  @Test
  public void testRunManualGame() {
    pokerGame.dealHoleCards();
    BettingRound brPre = new BettingRound(pokerGame);
    Assert.assertEquals(15, brPre.getPot());
    Assert.assertEquals(Position.SMALL_BLIND, brPre.getCurrentPlayer().getPosition());

    pokerGame.dealFlop();
  }

}
