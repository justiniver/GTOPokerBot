package model;

/**
 * Represents the hole cards of one player.
 */
public class HoleCards {
  private final Card card1;
  private final Card card2;

  public HoleCards(Card card1, Card card2) {
    if (card1 == null || card2 == null) {
      throw new IllegalArgumentException("Cards cannot be null.");
    }
    if (card1.equals(card2)) {
      throw new IllegalArgumentException("Duplicate cards are not allowed.");
    }
    this.card1 = card1;
    this.card2 = card2;
  }

  public Card getCard1() {
    return card1;
  }

  public Card getCard2() {
    return card2;
  }
}
