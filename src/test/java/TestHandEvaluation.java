import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import model.*;
import model.rules.*;

/**
 * Tests to check that the hand evaluations are working properly.
 */
public class TestHandEvaluation {

  private Card kingSpade;

  private Card queenHeart;
  private Card queenSpade;

  private Card jackSpade;

  private Card tenClub;
  private Card tenSpade;

  private Card nineHeart;
  private Card nineSpade;

  private Card sixClub;
  private Card sixHeart;

  private Card fourHeart;
  private Card fourSpade;

  private Card twoClub;
  private Card twoDiamond;
  private Card twoHeart;
  private Card twoSpade;
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
    twoSpade = new Card(Rank.TWO, Suit.SPADE);
    twoClub = new Card(Rank.TWO, Suit.CLUB);
    twoDiamond = new Card(Rank.TWO, Suit.DIAMOND);
    sixClub = new Card(Rank.SIX, Suit.CLUB);
    queenSpade = new Card(Rank.QUEEN, Suit.SPADE);
    tenSpade = new Card(Rank.TEN, Suit.SPADE);
    nineSpade = new Card(Rank.NINE, Suit.SPADE);
    fourSpade = new Card(Rank.FOUR, Suit.SPADE);
    evaluator = new HandEvaluation();
  }

  @Test
  public void isStraightFlush() {
    Card[] cards1 = new Card[]{nineSpade, tenSpade, kingSpade, queenSpade, jackSpade};
    Card[] cards2 = new Card[]{nineHeart, queenHeart, twoHeart, fourHeart, jackSpade};
    PokerHand myHand1 = new PokerHand(cards1);
    PokerHand myHand2 = new PokerHand(cards2);


    Assert.assertTrue(evaluator.isStraightFlush(myHand1));
    Assert.assertFalse(evaluator.isStraightFlush(myHand2));
    Assert.assertEquals(evaluator.evaluateHand(myHand1), HandRank.STRAIGHTFLUSH);
  }

  @Test
  public void testIsFlush() {
    Card[] cards1 = new Card[]{nineHeart, queenHeart, twoHeart, fourHeart, sixHeart};
    Card[] cards2 = new Card[]{nineHeart, queenHeart, twoHeart, fourHeart, jackSpade};
    PokerHand myHand1 = new PokerHand(cards1);
    PokerHand myHand2 = new PokerHand(cards2);


    Assert.assertTrue(evaluator.isFlush(myHand1));
    Assert.assertFalse(evaluator.isFlush(myHand2));
    Assert.assertEquals(evaluator.evaluateHand(myHand1), HandRank.FLUSH);
  }

  @Test
  public void testIsStraight() {
    Card[] cards1 = new Card[]{jackSpade, nineHeart, tenClub, queenHeart, kingSpade};
    Card[] cards2 = new Card[]{jackSpade, nineHeart, tenClub, queenHeart, twoHeart};
    PokerHand myHand1 = new PokerHand(cards1);
    PokerHand myHand2 = new PokerHand(cards2);

    Assert.assertTrue(evaluator.isStraight(myHand1));
    Assert.assertFalse(evaluator.isStraight(myHand2));
    Assert.assertEquals(evaluator.evaluateHand(myHand1), HandRank.STRAIGHT);
  }

  @Test
  public void testIsFourOAK() {
    Card[] cards1 = new Card[]{twoDiamond, twoSpade, twoClub, twoHeart, jackSpade};
    Card[] cards2 = new Card[]{jackSpade, nineHeart, tenClub, queenHeart, twoHeart};
    PokerHand myHand1 = new PokerHand(cards1);
    PokerHand myHand2 = new PokerHand(cards2);

    Assert.assertTrue(evaluator.isFourOAK(myHand1));
    Assert.assertFalse(evaluator.isFourOAK(myHand2));
    Assert.assertEquals(evaluator.evaluateHand(myHand1), HandRank.FOUROAK);
  }

  @Test
  public void testIsFullHouse() {
    Card[] cards1 = new Card[]{twoDiamond, twoSpade, twoClub, sixClub, sixClub};
    Card[] cards2 = new Card[]{jackSpade, nineHeart, tenClub, queenHeart, twoHeart};
    Card[] cards3 = new Card[]{twoDiamond, twoSpade, twoClub, twoHeart, jackSpade};
    PokerHand myHand1 = new PokerHand(cards1);
    PokerHand myHand2 = new PokerHand(cards2);
    PokerHand myHand3 = new PokerHand(cards3);

    Assert.assertTrue(evaluator.isFullHouse(myHand1));
    Assert.assertFalse(evaluator.isFullHouse(myHand2));
    Assert.assertFalse(evaluator.isFullHouse(myHand3));
    Assert.assertEquals(evaluator.evaluateHand(myHand1), HandRank.FULLHOUSE);
  }

  @Test
  public void testIsThreeOAK() {
    Card[] cards1 = new Card[]{twoDiamond, twoSpade, twoClub, fourHeart, jackSpade};
    Card[] cards2 = new Card[]{jackSpade, nineHeart, tenClub, queenHeart, twoHeart};
    PokerHand myHand1 = new PokerHand(cards1);
    PokerHand myHand2 = new PokerHand(cards2);

    Assert.assertTrue(evaluator.isThreeOAK(myHand1));
    Assert.assertFalse(evaluator.isThreeOAK(myHand2));
    Assert.assertEquals(evaluator.evaluateHand(myHand1), HandRank.THREEOAK);
  }

  @Test
  public void testIsTwoPair() {
    Card[] cards1 = new Card[]{twoDiamond, twoSpade, fourSpade, fourHeart, jackSpade};
    Card[] cards2 = new Card[]{jackSpade, nineHeart, tenClub, queenHeart, twoHeart};
    PokerHand myHand1 = new PokerHand(cards1);
    PokerHand myHand2 = new PokerHand(cards2);

    Assert.assertTrue(evaluator.isTwoPair(myHand1));
    Assert.assertFalse(evaluator.isTwoPair(myHand2));
    Assert.assertEquals(evaluator.evaluateHand(myHand1), HandRank.TWOPAIR);
  }

  @Test
  public void testIsPair() {
    Card[] cards1 = new Card[]{twoDiamond, twoSpade, fourSpade, queenSpade, jackSpade};
    Card[] cards2 = new Card[]{jackSpade, nineHeart, tenClub, queenHeart, twoHeart};
    PokerHand myHand1 = new PokerHand(cards1);
    PokerHand myHand2 = new PokerHand(cards2);

    Assert.assertTrue(evaluator.isPair(myHand1));
    Assert.assertFalse(evaluator.isPair(myHand2));
    Assert.assertEquals(evaluator.evaluateHand(myHand1), HandRank.PAIR);
  }

  @Test
  public void testBreakTie() {
    Card[] cards1 = new Card[]{twoDiamond, twoSpade, fourSpade, fourHeart, jackSpade};
    Card[] cards2 = new Card[]{queenSpade, twoDiamond, twoSpade, fourSpade, fourHeart};
    Card[] cards3 = new Card[]{twoHeart, twoClub, fourSpade, queenHeart, fourHeart};
    PokerHand myHand1 = new PokerHand(cards1);
    PokerHand myHand2 = new PokerHand(cards2);
    PokerHand myHand3 = new PokerHand(cards3);

    Assert.assertTrue(evaluator.isTwoPair(myHand1));
    Assert.assertTrue(evaluator.isTwoPair(myHand2));
    Assert.assertEquals(evaluator.evaluateHand(myHand1), HandRank.TWOPAIR);
    Assert.assertEquals(evaluator.evaluateHand(myHand2), HandRank.TWOPAIR);

    Assert.assertEquals(myHand2, evaluator.breakTie(myHand1, myHand2));
    Assert.assertEquals(myHand2, evaluator.compareHands(myHand1, myHand2));
    Assert.assertNull(evaluator.breakTie(myHand2, myHand3));
  }


}
