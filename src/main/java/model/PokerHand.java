package model;

import java.util.Arrays;

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
  public HandRank getHandRank() {
    HandEvaluation evaluator = new HandEvaluation();
    return evaluator.evaluateHand(new PokerHand(this.hand));
  }

  @Override
  public String toString() {
    return "Hand: " + Arrays.toString(hand);
  }

}
