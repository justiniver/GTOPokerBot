package model;

/**
 * Implementation of a poker game.
 */
public class PokerGame implements Game {
  private GameState state;
  private PokerBoard board;
  private Deck deck;
  private Position p1;
  private Position p2;

  public void PublicGame() {
    state = GameState.PREFLOP;
    deck = new Deck();
    board = new PokerBoard();
    p1 = Position.SMALL_BLIND;
    p2 = Position.BIG_BLIND;
  }

  @Override
  public void dealFlop() {
    state = GameState.FLOP;
    board.addCards(deck.dealCards());
  }

  @Override
  public void dealTurn() {
    state = GameState.TURN;
    board.addCard(deck.dealCard());
  }

  @Override
  public void dealRiver() {
    state = GameState.RIVER;
    board.addCard(deck.dealCard());
  }
}
