package model;

import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of a five-card hand in Poker.
 */
public class PokerHand implements Hand {
  private final Card[] hand;

  public PokerHand() {
    this.hand = new Card[5];
  }

  public PokerHand(Card[] hand) {
    if (hand == null) {
      throw new IllegalArgumentException("Hand cannot be null");
    }
    if (hand.length != 5) {
      throw new IllegalArgumentException("Poker hand must contain exactly five cards");
    }
    for (Card card : hand) {
      if (card == null) {
        throw new IllegalArgumentException("Poker hand cannot contain null cards");
      }
    }
    this.hand = hand.clone();
  }

  @Override
  public Card[] getHand() {
    return this.hand.clone();
  }

  @Override
  public Boolean compareHands(Hand hand1, Hand hand2) {
    return null;
  }

  @Override
  public HandRank getHandRank() {
    return null;
  }

}
