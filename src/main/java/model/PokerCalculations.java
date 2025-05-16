package model;

/**
 * Provides basic poker calculations for heads-up play.
 */
public class PokerCalculations {

  /**
   * Calculates pot odds - the ratio of the call amount to the potential win amount.
   *
   * @param potSize the current size of the pot
   * @param callAmount the amount needed to call
   * @return the pot odds as a decimal (e.g., 0.25 means 4:1 odds)
   */
  public static double potOdds(int potSize, int callAmount) {
    if (callAmount == 0) {
      return 0.0;
    }

    return (double) callAmount / (potSize + callAmount);
  }
}