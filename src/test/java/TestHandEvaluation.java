import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import model.*;
import model.rules.*;

/**
 * Tests to check that the hand evaluations are working properly.
 */
public class TestHandEvaluation {

  private Card nineHeart;
  private Card tenClub;
  private Card jackSpade;
  private Card queenHeart;
  private Card kingSpade;
  private Card twoHeart;
  private Card fourHeart;
  private Card sixHeart;

  @Before
  public void init() {
    nineHeart = new Card(Rank.NINE, Suit.HEART);
    tenClub = new Card(Rank.TEN, Suit.CLUB);
    jackSpade = new Card(Rank.JACK, Suit.SPADE);
    queenHeart = new Card(Rank.QUEEN, Suit.HEART);
    kingSpade = new Card(Rank.KING, Suit.SPADE);
    twoHeart = new Card(Rank.TWO, Suit.HEART);
    fourHeart = new Card(Rank.FOUR, Suit.HEART);
    sixHeart = new Card(Rank.SIX, Suit.HEART);
  }

  @Test
  public void testFlushEval() {
    Card[] cards = new Card[]{nineHeart, queenHeart, twoHeart, fourHeart, sixHeart};
    PokerHand myHand = new PokerHand(cards);

    Assert.assertEquals(HandRank.FLUSH, myHand.getHandRank());
  }

}
