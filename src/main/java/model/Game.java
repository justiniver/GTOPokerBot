package model;

/**
 * Structure of a poker game.
 */
public interface Game {

  /**
   * The preflop action, including dealing HoleCards to the players.
   */
  void preflopAction();

  /**
   * Deals the flop (three cards to board).
   */
  void dealFlop();

  /**
   * Deals the turn (fourth card).
   */
  void dealTurn();

  /**
   * Deals the river (fifth card).
   */
  void dealRiver();
}
