import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import model.*;
import model.rules.*;
import util.CardStrings;

/**
 * Tests to check that the hand evaluations are working properly.
 */
public class TestHandEvaluation {
  private HandEvaluation evaluator;
  private CardStrings cs;

  @Before
  public void init() {
    cs = new CardStrings();
    evaluator = new HandEvaluation();
  }

  @Test
  public void testIsStraightFlush() {
    Card[] cards1 = new Card[]{cs.nineSpade, cs.tenSpade, cs.kingSpade, cs.queenSpade, cs.jackSpade};
    Card[] cards2 = new Card[]{cs.nineHeart, cs.queenHeart, cs.twoHeart, cs.fourHeart, cs.jackSpade};

    PokerHand myHand1 = new PokerHand(cards1);
    PokerHand myHand2 = new PokerHand(cards2);

    Assert.assertTrue(evaluator.isStraightFlush(myHand1));
    Assert.assertFalse(evaluator.isStraightFlush(myHand2));
    Assert.assertEquals(evaluator.evaluateHand(myHand1), HandRank.STRAIGHTFLUSH);
  }

  @Test
  public void testIsFlush() {
    Card[] cards1 = new Card[]{cs.nineHeart, cs.queenHeart, cs.twoHeart, cs.fourHeart, cs.sixHeart};
    Card[] cards2 = new Card[]{cs.nineHeart, cs.queenHeart, cs.twoHeart, cs.fourHeart, cs.jackSpade};

    PokerHand myHand1 = new PokerHand(cards1);
    PokerHand myHand2 = new PokerHand(cards2);

    Assert.assertTrue(evaluator.isFlush(myHand1));
    Assert.assertFalse(evaluator.isFlush(myHand2));
    Assert.assertEquals(evaluator.evaluateHand(myHand1), HandRank.FLUSH);
  }

  @Test
  public void testIsStraight() {
    Card[] cards1 = new Card[]{cs.jackSpade, cs.nineHeart, cs.tenClub, cs.queenHeart, cs.kingSpade};
    Card[] cards2 = new Card[]{cs.jackSpade, cs.nineHeart, cs.tenClub, cs.queenHeart, cs.twoHeart};

    PokerHand myHand1 = new PokerHand(cards1);
    PokerHand myHand2 = new PokerHand(cards2);

    Assert.assertTrue(evaluator.isStraight(myHand1));
    Assert.assertFalse(evaluator.isStraight(myHand2));
    Assert.assertEquals(evaluator.evaluateHand(myHand1), HandRank.STRAIGHT);
  }

  @Test
  public void testIsFourOAK() {
    Card[] cards1 = new Card[]{cs.twoDiamond, cs.twoSpade, cs.twoClub, cs.twoHeart, cs.jackSpade};
    Card[] cards2 = new Card[]{cs.jackSpade, cs.nineHeart, cs.tenClub, cs.queenHeart, cs.twoHeart};

    PokerHand myHand1 = new PokerHand(cards1);
    PokerHand myHand2 = new PokerHand(cards2);

    Assert.assertTrue(evaluator.isFourOAK(myHand1));
    Assert.assertFalse(evaluator.isFourOAK(myHand2));
    Assert.assertEquals(evaluator.evaluateHand(myHand1), HandRank.FOUROAK);
  }

  @Test
  public void testIsFullHouse() {
    Card[] cards1 = new Card[]{cs.twoDiamond, cs.twoSpade, cs.twoClub, cs.sixClub, cs.sixClub};
    Card[] cards2 = new Card[]{cs.jackSpade, cs.nineHeart, cs.tenClub, cs.queenHeart, cs.twoHeart};
    Card[] cards3 = new Card[]{cs.twoDiamond, cs.twoSpade, cs.twoClub, cs.twoHeart, cs.jackSpade};

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
    Card[] cards1 = new Card[]{cs.twoDiamond, cs.twoSpade, cs.twoClub, cs.fourHeart, cs.jackSpade};
    Card[] cards2 = new Card[]{cs.jackSpade, cs.nineHeart, cs.tenClub, cs.queenHeart, cs.twoHeart};

    PokerHand myHand1 = new PokerHand(cards1);
    PokerHand myHand2 = new PokerHand(cards2);

    Assert.assertTrue(evaluator.isThreeOAK(myHand1));
    Assert.assertFalse(evaluator.isThreeOAK(myHand2));
    Assert.assertEquals(evaluator.evaluateHand(myHand1), HandRank.THREEOAK);
  }

  @Test
  public void testIsTwoPair() {
    Card[] cards1 = new Card[]{cs.twoDiamond, cs.twoSpade, cs.fourSpade, cs.fourHeart, cs.jackSpade};
    Card[] cards2 = new Card[]{cs.jackSpade, cs.nineHeart, cs.tenClub, cs.queenHeart, cs.twoHeart};

    PokerHand myHand1 = new PokerHand(cards1);
    PokerHand myHand2 = new PokerHand(cards2);

    Assert.assertTrue(evaluator.isTwoPair(myHand1));
    Assert.assertFalse(evaluator.isTwoPair(myHand2));
    Assert.assertEquals(evaluator.evaluateHand(myHand1), HandRank.TWOPAIR);
  }

  @Test
  public void testIsPair() {
    Card[] cards1 = new Card[]{cs.twoDiamond, cs.twoSpade, cs.fourSpade, cs.queenSpade, cs.jackSpade};
    Card[] cards2 = new Card[]{cs.jackSpade, cs.nineHeart, cs.tenClub, cs.queenHeart, cs.twoHeart};

    PokerHand myHand1 = new PokerHand(cards1);
    PokerHand myHand2 = new PokerHand(cards2);

    Assert.assertTrue(evaluator.isPair(myHand1));
    Assert.assertFalse(evaluator.isPair(myHand2));
    Assert.assertEquals(evaluator.evaluateHand(myHand1), HandRank.PAIR);
  }

  @Test
  public void testBreakTie() {
    Card[] cards1 = new Card[]{cs.twoDiamond, cs.twoSpade, cs.fourSpade, cs.fourHeart, cs.jackSpade};
    Card[] cards2 = new Card[]{cs.queenSpade, cs.twoDiamond, cs.twoSpade, cs.fourSpade, cs.fourHeart};
    Card[] cards3 = new Card[]{cs.twoHeart, cs.twoClub, cs.fourSpade, cs.queenHeart, cs.fourHeart};

    PokerHand myHand1 = new PokerHand(cards1);
    PokerHand myHand2 = new PokerHand(cards2);
    PokerHand myHand3 = new PokerHand(cards3);

    Assert.assertTrue(evaluator.isTwoPair(myHand1));
    Assert.assertTrue(evaluator.isTwoPair(myHand2));
    Assert.assertEquals(evaluator.evaluateHand(myHand1), HandRank.TWOPAIR);
    Assert.assertEquals(evaluator.evaluateHand(myHand2), HandRank.TWOPAIR);

    Assert.assertEquals(myHand2, evaluator.breakTie(myHand1, myHand2));
    Assert.assertEquals(myHand2, evaluator.getBetterHand(myHand1, myHand2));
    Assert.assertNotNull(evaluator.breakTie(myHand2, myHand3));
  }
}
