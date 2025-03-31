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

  private int smallBlindAmount;
  private int bigBlindAmount;

  public BettingRound(Player playerSB, Player playerBB, int pot, GameState state,
                      int smallBlindAmount, int bigBlindAmount) {
    this.playerSB = playerSB;
    this.playerBB = playerBB;
    this.pot = pot;
    this.state = state;
    this.smallBlindAmount = smallBlindAmount;
    this.bigBlindAmount = bigBlindAmount;
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

  /**
   * Processes the actions and prints out useful information to users regarding their action.
   *
   * @param action the current action (FOLD, CALL, CHECK, BET, RAISE)
   * @param currentPlayer the current player (small blind or big blind)
   * @return true if the betting round should end, false otherwise
   */
  private boolean processAction(Action action, Player currentPlayer) {
    return switch (action) {
      case FOLD -> true;
      case CHECK -> processCheck(currentPlayer); // returns false
      case CALL -> processCall(currentPlayer); // returns false
      case BET -> processBet(action, currentPlayer); // returns false
      case RAISE -> processRaise(action, currentPlayer); // returns false
    };

  }

  private boolean processCheck(Player currentPlayer) {
    int currentPlayerBet = getCurrentPlayerBet(currentPlayer);
    if (currentPlayerBet < currentBet) {
      System.out.println("Invalid action. Cannot check as current bet is " + currentBet);
    }

    return false;
  }

  private boolean processCall(Player currentPlayer) {
    int currentPlayerBet = getCurrentPlayerBet(currentPlayer);
    int chipsNeededToCall = currentBet - currentPlayerBet;
    if (chipsNeededToCall <= 0) {
      System.out.println("Nothing to call. Your current bet is " + currentPlayerBet +
                      "and the opponent bet " + currentBet);

    } else if (currentPlayer.getStack() < chipsNeededToCall) {
      System.out.println("Invalid action. Not enough chips in stack to call");
    } else {
      currentPlayer.subtractStack(chipsNeededToCall);
      pot += chipsNeededToCall;
      if (currentPlayer == playerSB) {
        betSB += chipsNeededToCall;
      } else {
        betBB += chipsNeededToCall;
      }
      System.out.println(currentPlayer.getPosition() + " calls for " + chipsNeededToCall);
    }
    return false;
  }

  private boolean processBet(Action action, Player currentPlayer) {
    Scanner scanner = new Scanner(System.in);
    int currentPlayerBet = getCurrentPlayerBet(currentPlayer);
    if (currentBet != 0) {
      System.out.println("Invalid action. Current bet is " + currentBet);
    } else {
      int amount = scanner.nextInt();
      int required = currentBet - currentPlayerBet;

    }

    return false;
  }

  private boolean processRaise(Action action, Player currentPlayer) {
    Scanner scanner = new Scanner(System.in);
    int currentPlayerBet = getCurrentPlayerBet(currentPlayer);
    if (action == Action.BET && currentBet != 0) {
      System.out.println("Invalid action. Current bet is " + currentBet);
    } else if  (action == Action.RAISE && currentBet == 0) {
      System.out.println("Invalid action. Nothing to raise. Current bet is 0");
    } else {
      int amount = scanner.nextInt();
      if (amount < currentBet) {
        System.out.println("Invalid action. Must raise by at least " + currentBet);
      } else if (amount < bigBlindAmount) {
        System.out.println("Invalid action. Must raise by at least one big blind " + bigBlindAmount);
      } else {
        // logic for when raise is valid
      }


    }

    return false;
  }

  private int getCurrentPlayerBet(Player currentPlayer) {
    if (currentPlayer == playerSB) {
      return betSB;
    } else {
      return betBB;
    }
  }



}


