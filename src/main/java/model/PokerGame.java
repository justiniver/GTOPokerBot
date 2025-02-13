package model;

/**
 * Implementation of a poker game.
 */
public class PokerGame implements Game {
  private GameState state;
  private PokerBoard board;
  private PokerDeck deck;
  private Player p1;
  private Player p2;

  public void PublicGame() {
    this.deck = new PokerDeck();
    this.board = new PokerBoard();
    this.state = GameState.PREFLOP;
    this.p1 = new Player(Position.SMALL_BLIND);
    this.p2 = new Player(Position.BIG_BLIND);
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
