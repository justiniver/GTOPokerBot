package model;

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

  public void setHand(PokerDeck deck) {
    this.cards = new HoleCards(deck.dealCard(), deck.dealCard());
  }

  public HoleCards getHand() {
    return this.cards;
  }

  public Position getPosition() {
    return this.position;
  }


}
