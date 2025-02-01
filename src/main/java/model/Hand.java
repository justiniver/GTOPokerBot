package model;

import java.util.List;

/**
 * Represents a hand in Poker.
 */
public interface Hand {

  /**
   * Returns the poker hand.
   *
   * @return the poker hand
   * @throws IllegalStateException if hand is not size five
   */
  Card[] getHand();

  /**
   * Compares two poker hands, hand1 and hand2.
   *
   * @param hand1 the first hand
   * @param hand2 the second hand
   * @return the winning poker hand, null if tie
   */
  PokerHand compareHands(PokerHand hand1, PokerHand hand2);

  /**
   * Returns the rank of the hand.
   *
   * @param hand the hand to evaluate
   * @return the HandRank
   */
  HandRank getHandRank(PokerHand hand);

}
