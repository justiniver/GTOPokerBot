package model;

/**
 * Represents the hold cards (dealt cards) of one player.
 */
public class HoleCards {
  private Card card1;
  private Card card2;

  public HoleCards(Card card1, Card card2) {
    if (card1 == card2) {
      throw new IllegalStateException("Duplicate cards are not allowed.");
    }
    this.card1 = card1;
    this.card2 = card2;
  }
}
