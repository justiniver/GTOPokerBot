package model.rules;

import model.Hand;
import model.HandRank;

public interface HandRules {

  /**
   * Evaluates the poker hand and returns the rank of the hand.
   *
   * @param hand the hand to evaluate
   * @return the HandRank
   */
  HandRank evaluateHand(Hand hand);

  /**
   * Compares two poker hands, hand1 and hand2.
   *
   * @param hand1 the first hand
   * @param hand2 the second hand
   * @return true if hand1 is better than hand2, false if hand2 is better than hand1, null if tie
   */
  Boolean compareHands(Hand hand1, Hand hand2);
}
