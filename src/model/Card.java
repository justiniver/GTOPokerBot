package model;

/**
 * Representation of a poker card.
 */
public class Card {
  Rank rank;
  Suit suit;

  public Card(Rank rank, Suit suit) {
    this.rank = rank;
    this.suit = suit;
  }

}
