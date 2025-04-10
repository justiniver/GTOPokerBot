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
    this(position, 0);
  }


  public Player(Position position, int stack) {
    this.position = position;
    this.stack = stack;
  }

  public void addStack(int add) {
    if (add <= 0) {
      throw new IllegalArgumentException("Must add a non-zero positive number.");
    }
    this.stack = this.stack + add;
  }

  public void subtractStack(int subtract) {
    if (subtract > this.stack) {
      throw new IllegalArgumentException("Operation will result in negative stack.");
    }
    if (subtract <= 0) {
      throw new IllegalArgumentException("Must subtract a non-zero positive number.");
    }
    this.stack = this.stack - subtract;
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
