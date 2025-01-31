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
   * Evaluates the poker hand and returns the rank of the hand.
   *
   * @param hand the hand to evaluate
   * @return the HandRank
   */
  HandRank evaluateHand(PokerHand hand);
}
