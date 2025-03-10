package model;

public class BettingRound {
  Player smallBlind;
  Player bigBlind;

  public BettingRound() {
    this.smallBlind = new Player(Position.SMALL_BLIND);
    this.bigBlind = new Player(Position.BIG_BLIND);

  }



}
