import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import model.*;
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
    Assert.assertNotNull(pokerGame.getP1());
    Assert.assertNotNull(pokerGame.getP2());
    Assert.assertNotNull(pokerGame.getDeck());
    Assert.assertNotNull(pokerGame.getBoard());
    Assert.assertEquals(Position.SMALL_BLIND, pokerGame.getP1().getPosition());
    Assert.assertEquals(Position.BIG_BLIND, pokerGame.getP2().getPosition());
    Assert.assertEquals(52, pokerGame.getDeck().getDeckSize());
    Assert.assertEquals(GameState.PREFLOP, pokerGame.getState());
  }

  @Test
  public void testDealHoleCards() {
    int initialDeckSize = pokerGame.getDeck().getDeckSize();
    pokerGame.dealHoleCards();
    Assert.assertNotNull(pokerGame.getP1().getHand().getCard1());
    Assert.assertNotNull(pokerGame.getP1().getHand().getCard2());
    Assert.assertNotNull(pokerGame.getP2().getHand().getCard1());
    Assert.assertNotNull(pokerGame.getP2().getHand().getCard2());
    Assert.assertEquals(GameState.PREFLOP, pokerGame.getState());
    Assert.assertEquals(initialDeckSize - 4, pokerGame.getDeck().getDeckSize());
  }

  @Test
  public void testDealFlop() {
    pokerGame.dealHoleCards();
    int deckSizeAfterHole = pokerGame.getDeck().getDeckSize();
    pokerGame.dealFlop();
    Assert.assertEquals(GameState.FLOP, pokerGame.getState());
    Assert.assertEquals(3, pokerGame.getBoard().getCommunityCards().size());
    Assert.assertEquals(deckSizeAfterHole - 3, pokerGame.getDeck().getDeckSize());
  }

  @Test
  public void testDealTurn() {
    pokerGame.dealHoleCards();
    pokerGame.dealFlop();
    int deckSizeAfterFlop = pokerGame.getDeck().getDeckSize();
    pokerGame.dealTurn();
    Assert.assertEquals(GameState.TURN, pokerGame.getState());
    Assert.assertEquals(4, pokerGame.getBoard().getCommunityCards().size());
    Assert.assertEquals(deckSizeAfterFlop - 1, pokerGame.getDeck().getDeckSize());
  }

  @Test
  public void testDealRiver() {
    pokerGame.dealHoleCards();
    pokerGame.dealFlop();
    pokerGame.dealTurn();
    int deckSizeAfterTurn = pokerGame.getDeck().getDeckSize();
    pokerGame.dealRiver();
    Assert.assertEquals(GameState.RIVER, pokerGame.getState());
    Assert.assertEquals(5, pokerGame.getBoard().getCommunityCards().size());
    Assert.assertEquals(deckSizeAfterTurn - 1, pokerGame.getDeck().getDeckSize());
  }

  @Test
  public void testGetBestFiveCardHandPreflop() {
    pokerGame.dealHoleCards();
    Assert.assertThrows(IllegalStateException.class, () ->
            pokerGame.getBestFiveCardHand(pokerGame.getP1(), pokerGame.getBoard())
    );
  }

  @Test
  public void testGetBestFiveCardHandRiver() {
    pokerGame.dealHoleCards();
    pokerGame.dealFlop();
    pokerGame.dealTurn();
    pokerGame.dealRiver();
    Assert.assertEquals(GameState.RIVER, pokerGame.getState());
    pokerGame.getBestFiveCardHand(pokerGame.getP1(), pokerGame.getBoard());
  }




}
