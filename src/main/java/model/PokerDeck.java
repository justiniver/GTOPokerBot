package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Represents a deck in the game of poker.
 */
public class PokerDeck implements Deck {
  private final List<Card> originalDeck;
  private List<Card> deck;

  public PokerDeck() {
    this.originalDeck = new ArrayList<>(52);
    for (Rank rank : Rank.values()) {
      for (Suit suit : Suit.values()) {
        this.originalDeck.add(new Card(rank, suit));
      }
    }

    this.deck = new ArrayList<>(originalDeck);
  }

  @Override
  public List<Card> getDeck() {
    return this.deck;
  }

  @Override
  public void shuffle() {
    Collections.shuffle(deck);
  }

  @Override
  public Card dealCard() {
    if (deck.isEmpty()) {
      throw new IllegalStateException("deck cannot be empty");
    }

    return deck.remove(0);
  }

  @Override
  public List<Card> dealCards() {
    if (deck.isEmpty()) {
      throw new IllegalStateException("deck cannot be empty");
    }
    List<Card> cards = new ArrayList<>();

    for (int i = 0; i < 3; i++) {
      cards.add(deck.remove(0));
    }

    return cards;
  }

  @Override
  public void reset() {
    this.deck = new ArrayList<>(originalDeck);
  }

}
