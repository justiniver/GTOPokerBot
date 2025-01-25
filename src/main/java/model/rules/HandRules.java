package model.rules;

import model.PokerHand;
import model.HandRank;

/**
 * Rules of a poker hand.
 */
public interface HandRules {
  /**
   * Evaluates the poker hand and returns the rank of the hand.
   *
   * @param hand the hand to evaluate
   * @return the HandRank
   */
  HandRank evaluateHand(PokerHand hand);
}
