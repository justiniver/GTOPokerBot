package model;

public class PokerSession {
  private PokerGame pokerGame;

  public PokerSession(PokerGame pokerGame) {
    this.pokerGame = pokerGame;
  }

  public PokerGame getPokerGame() {
    return pokerGame;
  }

}
