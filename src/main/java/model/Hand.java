package model;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a 5-card hand in Poker.
 */
public class Hand {
  private final List<Card> hand;

  public Hand() {
    this.hand = new ArrayList<>(5);
  }

  Hand(List<Card> hand) {
    this.hand = hand;
  }

  List<Card> getHand() {
    return this.hand;
  }

  HandRank getHandRank() {

  }
}
