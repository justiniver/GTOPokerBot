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
  HandEvaluation evaluator;


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
    evaluator = new HandEvaluation();
  }

  @Test
  public void testIsFlush() {
    Card[] cards1 = new Card[]{nineHeart, queenHeart, twoHeart, fourHeart, sixHeart};
    Card[] cards2 = new Card[]{nineHeart, queenHeart, twoHeart, fourHeart, jackSpade};
    PokerHand myHand1 = new PokerHand(cards1);
    PokerHand myHand2 = new PokerHand(cards2);


    Assert.assertTrue(evaluator.isFlush(myHand1));
    Assert.assertFalse(evaluator.isFlush(myHand2));
  }

  @Test
  public void testIsStraight() {
    Card[] cards1 = new Card[]{jackSpade, nineHeart, tenClub, queenHeart, kingSpade};
    Card[] cards2 = new Card[]{jackSpade, nineHeart, tenClub, queenHeart, twoHeart};
    PokerHand myHand1 = new PokerHand(cards1);
    PokerHand myHand2 = new PokerHand(cards2);

    Assert.assertTrue(evaluator.isStraight(myHand1));
    Assert.assertFalse(evaluator.isStraight(myHand2));
  }

}
