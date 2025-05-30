package model;

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
   * Returns the rank of the hand.
   *
   * @return the HandRank
   */
  HandRank getHandRank();

}
