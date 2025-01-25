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
   * @return true if hand1 is better than hand2, false if hand2 is better than hand1, null if tie
   */
  Boolean compareHands(Hand hand1, Hand hand2);

  /**
   * Returns the rank of the hand.
   *
   * @return the HandRank
   */
  HandRank getHandRank();

}
