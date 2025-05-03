import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import bots.SimpleCheckCallBot;
import controller.PokerController;
import model.*;
import model.rules.*;
import util.CardStrings;

/**
 * Tests game flow and makes sure the model and controller are working together and correctly.
 * Will implement after creating some rule-based bots.
 */
public class TestGameFlow {
  private HandEvaluation eval;
  private Player playerSB;
  private Player playerBB;
  private PokerGame pokerGame;
  private PokerController pokerController;
  private CardStrings cs;

  @Before
  public void init() {
    this.cs = new CardStrings();
    this.playerSB  = new Player(Position.SMALL_BLIND, 1000);
    this.playerBB = new Player(Position.BIG_BLIND, 1000);
    this.pokerGame = new PokerGame(true, 5, 10, playerSB, playerBB);
    this.pokerController = new PokerController();
  }

  @Test
  public void testCheckingItDown() {
    playerSB.setStrategy(new SimpleCheckCallBot());
    playerBB.setStrategy(new SimpleCheckCallBot());
    pokerController.playHand(pokerGame);
    Assert.assertEquals(2000, playerSB.getStack() + playerBB.getStack());
    Assert.assertNotEquals(1000, playerSB.getStack());
    Assert.assertNotEquals(1000, playerBB.getStack());
  }

}
