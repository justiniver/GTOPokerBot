package model;

public class Player {
  private Position position;
  private HoleCards cards;

  public Player(Position position) {
    this.position = position;
  }

  public Player(Position position, HoleCards cards) {
    this.position = position;
    this.cards = cards;
  }

  public HoleCards getHand() {
    return this.cards;
  }

  public Position getPosition() {
    return this.position;
  }


}
