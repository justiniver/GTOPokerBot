package model;

/**
 * Represents the betting round that occurs four times during a game of poker.
 */
public class BettingRound {
  private GameState state;
  private final Player playerSB;
  private final Player playerBB;
  private int pot;

  public BettingRound(Player playerSB, Player playerBB, int pot, GameState state) {
    this.playerSB = playerSB;
    this.playerBB = playerBB;
    this.pot = pot;
    this.state = state;
  }

  /**
   * Execution method to run the betting round.
   * Does not terminate unless player actions indicate termination (e.g., a player folds).
   */
  public void run() {
    Player currentPlayer;
    Player otherPlayer;
    if (state == GameState.PREFLOP) {
      currentPlayer = playerSB;
      otherPlayer = playerBB;
    } else {
      currentPlayer = playerBB;
      otherPlayer = playerSB;
    }
    boolean roundOver = false;

    while (!roundOver) {

    }

  }

}
