package model;

import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of a five-card hand in Poker.
 */
public class PokerHand implements Hand {
  private final List<Card> hand;

  public PokerHand() {
    this.hand = new ArrayList<>(5);
  }

  PokerHand(List<Card> hand) {
    this.hand = hand;
  }

  @Override
  public List<Card> getHand() {
    return this.hand;
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
