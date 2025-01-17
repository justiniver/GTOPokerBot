package model;

public class Hand {
  Card card1;
  Card card2;

  public Hand(Card card1, Card card2) {
    if (card1 == card2) {
      throw new IllegalStateException("Duplicate cards are not allowed.");
    }
    this.card1 = card1;
    this.card2 = card2;
  }
}
