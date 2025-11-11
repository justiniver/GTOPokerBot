import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import model.*;
import util.CardStrings;

public class TestPokerGameClass {
  private CardStrings cs;
  private PokerGame pokerGame;

  @Before
  public void init() {
    cs = new CardStrings();
    pokerGame = new PokerGame(false);
  }

  @Test
  public void testPokerGameConstructor() {
    Assert.assertNotNull(pokerGame.getPlayerSB());
    Assert.assertNotNull(pokerGame.getPlayerBB());
    Assert.assertNotNull(pokerGame.getDeck());
    Assert.assertNotNull(pokerGame.getBoard());
    Assert.assertEquals(Position.SMALL_BLIND, pokerGame.getPlayerSB().getPosition());
    Assert.assertEquals(Position.BIG_BLIND, pokerGame.getPlayerBB().getPosition());
    Assert.assertEquals(52, pokerGame.getDeck().getSize());
    Assert.assertEquals(GameState.PREFLOP, pokerGame.getState());
  }

  @Test
  public void testDealHoleCards() {
    int initialDeckSize = pokerGame.getDeck().getSize();
    pokerGame.dealHoleCards();
    Assert.assertNotNull(pokerGame.getPlayerSB().getHoleCards().getCard1());
    Assert.assertNotNull(pokerGame.getPlayerSB().getHoleCards().getCard2());
    Assert.assertNotNull(pokerGame.getPlayerBB().getHoleCards().getCard1());
    Assert.assertNotNull(pokerGame.getPlayerBB().getHoleCards().getCard2());
    Assert.assertEquals(GameState.PREFLOP, pokerGame.getState());
    Assert.assertEquals(initialDeckSize - 4, pokerGame.getDeck().getSize());
  }

  @Test
  public void testDealFlop() {
    pokerGame.dealHoleCards();
    int deckSizeAfterHole = pokerGame.getDeck().getSize();
    pokerGame.dealFlop();
    Assert.assertEquals(GameState.FLOP, pokerGame.getState());
    Assert.assertEquals(3, pokerGame.getBoard().getCommunityCards().size());
    Assert.assertEquals(deckSizeAfterHole - 3, pokerGame.getDeck().getSize());
  }

  @Test
  public void testDealTurn() {
    pokerGame.dealHoleCards();
    pokerGame.dealFlop();
    int deckSizeAfterFlop = pokerGame.getDeck().getSize();
    pokerGame.dealTurn();
    Assert.assertEquals(GameState.TURN, pokerGame.getState());
    Assert.assertEquals(4, pokerGame.getBoard().getCommunityCards().size());
    Assert.assertEquals(deckSizeAfterFlop - 1, pokerGame.getDeck().getSize());
  }

  @Test
  public void testDealRiver() {
    pokerGame.dealHoleCards();
    pokerGame.dealFlop();
    pokerGame.dealTurn();
    int deckSizeAfterTurn = pokerGame.getDeck().getSize();
    pokerGame.dealRiver();
    Assert.assertEquals(GameState.RIVER, pokerGame.getState());
    Assert.assertEquals(5, pokerGame.getBoard().getCommunityCards().size());
    Assert.assertEquals(deckSizeAfterTurn - 1, pokerGame.getDeck().getSize());
  }

  @Test
  public void testGetBestFiveCardHandPreflop() {
    pokerGame.dealHoleCards();
    Assert.assertThrows(IllegalStateException.class, () ->
            pokerGame.getBestFiveCardHand(pokerGame.getPlayerSB(), pokerGame.getBoard())
    );
  }

  @Test
  public void testGetBestFiveCardHandRiver() {
    PokerGame pokerGame = new PokerGame(false);
    pokerGame.dealP1SpecificCards(cs.threeHeart, cs.queenHeart);
    pokerGame.dealFlop();
    pokerGame.dealTurn();
    pokerGame.dealRiver();
    Assert.assertEquals(GameState.RIVER, pokerGame.getState());
    PokerBoard board = pokerGame.getBoard();
    Assert.assertEquals("Board: [TWOSPADE, TWOHEART, THREECLUB, THREEDIAMOND, THREESPADE]",
            board.toString());
    Assert.assertEquals("HoleCards: THREEHEART QUEENHEART",
            pokerGame.getPlayerSB().getHoleCards().toString());
    PokerHand bestP1 = pokerGame.getBestFiveCardHand(pokerGame.getPlayerSB(), pokerGame.getBoard());
    Assert.assertEquals(bestP1.getHandRank(), HandRank.FOUROAK);
  }

  @Test
  public void testGetBestFiveCardHandRemoveCards() {
    PokerGame pokerGame = new PokerGame(false);
    List<Card> cardsToRemove = new ArrayList<>(List.of(cs.twoSpade, cs.threeSpade, cs.threeDiamond));
    pokerGame.getDeck().removeCards(cardsToRemove);
    pokerGame.dealP1SpecificCards(cs.aceDiamond, cs.aceClub);
    pokerGame.dealFlop();
    pokerGame.dealTurn();
    pokerGame.dealRiver();
    Assert.assertEquals(GameState.RIVER, pokerGame.getState());
    PokerBoard board = pokerGame.getBoard();
    Assert.assertEquals("Board: [TWOHEART, THREECLUB, THREEHEART, FOURCLUB, FOURDIAMOND]",
            board.toString());
    Assert.assertEquals("HoleCards: ACEDIAMOND ACECLUB",
            pokerGame.getPlayerSB().getHoleCards().toString());
    PokerHand bestP1 = pokerGame.getBestFiveCardHand(pokerGame.getPlayerSB(), pokerGame.getBoard());
    Assert.assertEquals(HandRank.TWOPAIR, bestP1.getHandRank());
  }

}
