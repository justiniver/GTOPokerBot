package model;

import model.rules.HandEvaluation;

/**
 * Implementation of a five-card hand in Poker.
 */
public class PokerHand implements Hand {
  private final Card[] hand;

  public PokerHand() {
    this.hand = new Card[5];
  }

  public PokerHand(Card[] hand) {
    if (hand.length != 5) {
      throw new IllegalArgumentException("Poker hand must contain exactly five cards");
    }

    this.hand = hand.clone();
  }

  @Override
  public Card[] getHand() {
    return this.hand.clone();
  }

  @Override
  public HandRank getHandRank(PokerHand hand) {
    HandEvaluation evaluator = new HandEvaluation();
    return evaluator.evaluateHand(hand);
  }

}
