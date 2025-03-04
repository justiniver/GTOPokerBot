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

  public Card dealSpecificCard(Card card) {
    if (deck.isEmpty()) {
      throw new IllegalStateException("deck cannot be empty");
    }

    deck.remove(card);
    return card;
  }

  @Override
  public List<Card> dealCards(int n) {
    if (n > deck.size()) {
      throw new IllegalStateException("deck must contain enough cards");
    }
    List<Card> cards = new ArrayList<>();

    for (int i = 0; i < n; i++) {
      cards.add(deck.remove(0));
    }

    return cards;
  }

  @Override
  public int getDeckSize() {
    return deck.size();
  }

  @Override
  public void reset() {
    this.deck = new ArrayList<>(originalDeck);
  }

  public void removeCard(List<Card> cards) {
    for (Card card : cards) {
      deck.remove(card);
    }
  }

}
