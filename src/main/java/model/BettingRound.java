package model;

import java.util.Scanner;

/**
 * Represents the betting round that occurs four times during a game of poker.
 */
public class BettingRound {
  private GameState state;
  private final Player playerSB;
  private final Player playerBB;
  private int pot;

  private int betSB = 0;
  private int betBB = 0;
  private int currentBet = 0;

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

    Scanner scanner = new Scanner(System.in);
    boolean bettingComplete = false;

    while (!bettingComplete) {
      // Need to come up with all conditions that should end the round as well as
      // the game flow for betting.

    }

  }

  private boolean processAction(Action action, Player currentPlayer) {
    return switch (action) {
      case FOLD -> true;
      case CHECK -> processCheck(currentPlayer);
      case CALL -> processCall(currentPlayer);
      case BET, RAISE -> processBetOrRaise(currentPlayer);
    };

  }

  private boolean processCheck(Player currentPlayer) {
    return false;
  }

  private boolean processCall(Player currentPlayer) {
    return false;
  }

  private boolean processBetOrRaise(Player currentPlayer) {
    return false;
  }




}


