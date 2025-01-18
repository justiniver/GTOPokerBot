package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Represents a deck in the game of poker.
 */
public class Deck {
  private final List<Card> originalDeck;
  private List<Card> deck;

  public Deck() {
    this.originalDeck = new ArrayList<>(52);
    for (Rank rank : Rank.values()) {
      for (Suit suit : Suit.values()) {
        this.originalDeck.add(new Card(rank, suit));
      }
    }

    this.deck = new ArrayList<>(originalDeck);
  }

  List<Card> getDeck() {
    return this.deck;
  }

  public void shuffle() {
    Collections.shuffle(deck);
  }

  public Card dealCard() {
    if (deck.isEmpty()) {
      throw new IllegalStateException("deck cannot be empty");
    }

    return deck.remove(0);
  }

  public void reset() {
    this.deck = new ArrayList<>(originalDeck);
  }

}
