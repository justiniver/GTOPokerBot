package model;

/**
 * Implementation of a poker game.
 */
public class PokerGame implements Game {
  private GameState state;
  private PokerBoard board;
  private Deck deck;

  public void PublicGame() {
    state = GameState.PREFLOP;
    deck = new Deck();
    board = new PokerBoard();
  }


  @Override
  public void dealFlop() {
    board.addCard(deck.dealCard());
  }

  @Override
  public void dealTurn() {

  }

  @Override
  public void dealRiver() {

  }
}
