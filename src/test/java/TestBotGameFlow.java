import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import bots.SimpleCheckCallBot;
import bots.SimpleCheckFoldBot;
import controller.PokerController;
import model.*;
import model.rules.*;
import util.CardStrings;

/**
 * Tests game flow and makes sure the model and controller are working together and correctly.
 * Will implement after creating some rule-based bots.
 */
public class TestBotGameFlow {
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
    Assert.assertEquals(1000, playerSB.getStack());
    Assert.assertEquals(1000, playerBB.getStack());
    pokerController.playHand(pokerGame);
    Assert.assertEquals(2000, playerSB.getStack() + playerBB.getStack());
    Assert.assertNotEquals(1000, playerSB.getStack());
    Assert.assertNotEquals(1000, playerBB.getStack());
  }

  @Test
  public void testCheckingItDownWithFoldBots() {
    playerSB.setStrategy(new SimpleCheckFoldBot());
    playerBB.setStrategy(new SimpleCheckFoldBot());
    Assert.assertEquals(1000, playerSB.getStack());
    Assert.assertEquals(1000, playerBB.getStack());
    pokerController.playHand(pokerGame);
    Assert.assertEquals(2000, playerSB.getStack() + playerBB.getStack());
    Assert.assertNotEquals(1000, playerSB.getStack());
    Assert.assertNotEquals(1000, playerBB.getStack());
  }

  @Test
  public void testRaisingManualGame() {
    pokerController.playHand(pokerGame);
  }

}
