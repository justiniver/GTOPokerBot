package model;

import java.util.List;

/**
 * Represents a deck.
 */
public interface Deck {

  List<Card> getDeck();

  void shuffle();

  /**
   * Deals a single card.
   *
   * @return the card dealt
   */
  Card dealCard();

  /**
   * Deals three cards.
   *
   * @return a list of three cards
   */
  List<Card> dealCards();

  /**
   * Resets the deck to its original 52 cards.
   */
  void reset();

}
