package model;

/**
 * Represents a poker game.
 */
public class PokerGame {
  private GameState state;
  private Deck deck;

  public void PublicGame() {
    state = GameState.PREFLOP;
  }


}
