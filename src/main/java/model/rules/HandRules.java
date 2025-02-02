package model.rules;

import model.PokerHand;
import model.HandRank;

/**
 * Rules of a poker hand.
 */
public interface HandRules {

  boolean isStraightFlush(PokerHand Hand);

  boolean isFourOAK(PokerHand Hand);

  boolean isFullHouse(PokerHand Hand);

  boolean isFlush(PokerHand Hand);

  boolean isStraight(PokerHand Hand);

  boolean isThreeOAK(PokerHand Hand);

  boolean isTwoPair(PokerHand Hand);

  boolean isPair(PokerHand Hand);

  /**
   * Returns the winning hand, null if ties.
   *
   * @param hand1 the first hand
   * @param hand2 the second hand
   * @return the winning hand, null if tied
   */
  PokerHand breakTie(PokerHand hand1, PokerHand hand2);

  /**
   * Evaluates the poker hand and returns the rank of the hand.
   *
   * @param hand the hand to evaluate
   * @return the HandRank
   */
  HandRank evaluateHand(PokerHand hand);
}
