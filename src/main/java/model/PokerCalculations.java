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

  /**
   * Evaluates preflop hand strength on a scale of 0-1.
   *
   * @param holeCards the player's hole cards
   * @return a value between 0-1 representing hand strength (higher is better)
   */
  public static double evaluatePreflopHand(HoleCards holeCards) {
    Card card1 = holeCards.getCard1();
    Card card2 = holeCards.getCard2();

    int rank1 = card1.getRank().ordinal();
    int rank2 = card2.getRank().ordinal();

    if (rank2 > rank1) {
      int temp = rank1;
      rank1 = rank2;
      rank2 = temp;
    }

    boolean isPair = rank1 == rank2;
    boolean isSuited = card1.getSuit() == card2.getSuit();

    if (isPair) {
      return 0.5 + (rank1 * 0.04);
    }

    double strength = (rank1 * 0.7 + rank2 * 0.3) / 24.0;

    if (isSuited) {
      strength += 0.05;
    }

    int gap = Math.abs(rank1 - rank2);
    if (gap <= 2) {
      strength += 0.05 - (gap * 0.02);
    }

    return Math.min(0.95, strength);
  }

  /**
   * Calculates a recommended bet size based on pot size and hand strength.
   *
   * @param potSize the current pot size
   * @param street the current street (PREFLOP, FLOP, etc.)
   * @param handStrength hand strength value between 0-1
   * @param maxBet the maximum possible bet (usually stack size)
   * @return the recommended bet amount
   */
  public static int calculateBetSize(int potSize, GameState street, double handStrength, int maxBet) {
    double betSizeRatio;

    switch (street) {
      case PREFLOP:
        betSizeRatio = 3 + (handStrength * 2);
        break;
      case FLOP:
        betSizeRatio = 0.5 + (handStrength * 0.3);
        break;
      case TURN:
        betSizeRatio = 0.6 + (handStrength * 0.4);
        break;
      case RIVER:
        betSizeRatio = (handStrength > 0.8) ? 1.0 : 0.5;
        break;
      default:
        betSizeRatio = 0.5;
    }

    int betAmount = (int)(potSize * betSizeRatio);

    return Math.min(betAmount, maxBet);
  }

  /**
   * Calculates effective stack in big blinds.
   *
   * @param stackSize the player's stack size
   * @param bigBlindSize the big blind amount
   * @return the effective stack in big blinds
   */
  public static double effectiveStackInBB(int stackSize, int bigBlindSize) {
    return stackSize / (double)bigBlindSize;
  }
}