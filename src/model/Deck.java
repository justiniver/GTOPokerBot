package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Represents a deck in the game of poker.
 */
public class Deck {
  List<Card> deck;

  public Deck() {
    this.deck = new ArrayList<>();
    for (Rank rank : Rank.values()) {
      for (Suit suit : Suit.values()) {
        this.deck.add(new Card(rank, suit));
      }
    }
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

}
