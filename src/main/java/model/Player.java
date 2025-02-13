package model;

import java.util.List;

public class Player {
  private final Position position;
  private HoleCards cards;

  public Player(Position position) {
    this.position = position;
  }

  public Player(Position position, HoleCards cards) {
    this.position = position;
    this.cards = cards;
  }

  public void setHand(List<Card> cards) {
    if (cards == null || cards.size() != 2) {
      throw new IllegalArgumentException("The hand must be exactly two cards");
    }
    this.cards = new HoleCards(cards.get(0), cards.get(1));
  }

  public HoleCards getHand() {
    return this.cards;
  }

  public Position getPosition() {
    return this.position;
  }


}
