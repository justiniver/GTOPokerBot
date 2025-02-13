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

  public PokerGame() {
    this.deck = new PokerDeck();
    this.board = new PokerBoard();
    this.p1 = new Player(Position.SMALL_BLIND);
    this.p2 = new Player(Position.BIG_BLIND);
  }

  @Override
  public void preflopAction() {
    this.state = GameState.PREFLOP;
    p1.setHand(deck.dealCards(2));
    p2.setHand(deck.dealCards(2));
  }

  @Override
  public void dealFlop() {
    state = GameState.FLOP;
    board.addCards(deck.dealCards(3));
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

  public Player getP1() {
    return p1;
  }

  public Player getP2() {
    return p2;
  }

  public GameState getState() {
    return state;
  }

  public PokerBoard getBoard() {
    return board;
  }

  public PokerDeck getDeck() {
    return deck;
  }
}
