package model;

import java.util.List;

/**
 * Represents a player in Poker.
 */
public class Player {
  private final Position position;
  private HoleCards cards;
  private int stack;

  public Player(Position position) {
    this.position = position;
  }

  public Player(Position position, HoleCards cards) {
    this.position = position;
    this.cards = cards;
  }

  public Player(Position position, HoleCards cards, int stack) {
    this.position = position;
    this.cards = cards;
    this.stack = stack;
  }

  public void setHoleCards(List<Card> cards) {
    if (cards == null || cards.size() != 2) {
      throw new IllegalArgumentException("The hand must be exactly two cards");
    }
    this.cards = new HoleCards(cards.get(0), cards.get(1));
  }




  public HoleCards getHoleCards() {
    return this.cards;
  }

  public Position getPosition() {
    return this.position;
  }

  public int getStack() {
    return this.stack;
  }


}
