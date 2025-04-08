package model;

import java.util.Scanner;

/**
 * Represents the betting round that occurs four times during a game of poker.
 */
public class BettingRound {
  private final GameState state;
  private final Player playerSB;
  private final Player playerBB;
  private int pot;
  private int betSB = 0;
  private int betBB = 0;
  private int currentBet = 0;
  private int lastRaiseIncrement = 0;
  private final int bigBlindAmount;

  public BettingRound(Player playerSB, Player playerBB, int pot, GameState state,
                      int bigBlindAmount) {
    this.playerSB = playerSB;
    this.playerBB = playerBB;
    this.pot = pot;
    this.state = state;
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
      System.out.println("\nPot: " + pot);
      System.out.println("Current bet to call: " + currentBet);
      System.out.println("Your current bet: " + getCurrentPlayerBet(currentPlayer) +
              " | Your stack: " + currentPlayer.getStack());
      System.out.println("Enter action (FOLD, CHECK, CALL, BET, RAISE):\n");

      String input = scanner.nextLine().trim().toUpperCase();

      Action action;
      try {
        action = Action.valueOf(input);
      } catch (IllegalArgumentException e) {
        System.out.println("Invalid action. Please enter a valid action (FOLD, CHECK, CALL, BET, RAISE).");
        continue;
      }

      boolean endRound = processAction(action, currentPlayer);
      if (endRound) {
        System.out.println("Betting round ended due to action: " + action);
        break;
      }

      if (betSB == betBB && currentBet != 0) {
        System.out.println("Both players have matched bets. Betting round complete.");
        bettingComplete = true;
      } else {
        Player temp = currentPlayer;
        currentPlayer = otherPlayer;
        otherPlayer = temp;
      }
    }

    System.out.println("Final pot: " + pot);
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
      case BET -> processBet(currentPlayer); // returns false
      case RAISE -> processRaise(currentPlayer); // returns false
    };

  }

  private boolean processCheck(Player currentPlayer) {
    int currentPlayerBet = getCurrentPlayerBet(currentPlayer);
    if (currentPlayerBet < currentBet) {
      System.out.println("Invalid action--Cannot check as current bet is " + currentBet);
    }

    return false;
  }

  private boolean processCall(Player currentPlayer) {
    int currentPlayerBet = getCurrentPlayerBet(currentPlayer);
    int chipsNeededToCall = currentBet - currentPlayerBet;
    if (chipsNeededToCall <= 0) {
      System.out.println("Nothing to call--Your current bet is " + currentPlayerBet +
                      "and the opponent bet " + currentBet);
      return false;
    }
    if (currentPlayer.getStack() < chipsNeededToCall) {
      System.out.println("Invalid action--Not enough chips in stack to call");
      return false;
    }
    currentPlayer.subtractStack(chipsNeededToCall);
    pot += chipsNeededToCall;
    if (currentPlayer == playerSB) {
      betSB += chipsNeededToCall;
    } else {
      betBB += chipsNeededToCall;
    }
    System.out.println(currentPlayer.getPosition() + " calls for " + chipsNeededToCall);
    return false;
  }

  private boolean processBet(Player currentPlayer) {
    Scanner scanner = new Scanner(System.in);
    int currentPlayerBet = getCurrentPlayerBet(currentPlayer);
    if (currentBet != 0) {
      System.out.println("Invalid action--Current bet is " + currentBet + "--Use RAISE instead");
      return false;
    }
    int amount = scanner.nextInt();

    if (amount < bigBlindAmount) {
      System.out.println("Invalid action--You must bet at least " + bigBlindAmount);
      return false;
    }

    if (currentPlayer.getStack() < amount) {
      System.out.println("Invalid action--Not enough chips to bet " + amount);
      return false;
    }

    currentPlayer.subtractStack(amount);
    pot += amount;
    if (currentPlayer == playerSB) {
      betSB += amount;
      currentBet = betSB;
    } else {
      betBB += amount;
      currentBet = betBB;
    }

    System.out.println(currentPlayer.getPosition() + " bets " + amount);
    return false;
  }

  private boolean processRaise(Player currentPlayer) {
    Scanner scanner = new Scanner(System.in);
    int currentPlayerBet = getCurrentPlayerBet(currentPlayer);
    if (currentBet == 0) {
      System.out.println("Invalid action. Nothing to raise. Current bet is 0");
      return false;
    }
    System.out.println("Enter amount to raise by: ");
    int raiseIncrement = scanner.nextInt();
    int newTotalBet = currentPlayerBet + raiseIncrement;

    if (raiseIncrement < lastRaiseIncrement) {
      System.out.println("Invalid action. You must raise by at least " + lastRaiseIncrement);
      return false;
    }

    if (newTotalBet <= currentBet) {
      System.out.println("Invalid action. Your total bet must exceed the current bet of " + currentBet);
      return false;
    }

    if (currentPlayer.getStack() < raiseIncrement) {
      System.out.println("Invalid action. Not enough chips to raise by that amount.");
      return false;
    }

    currentPlayer.subtractStack(raiseIncrement);
    pot += raiseIncrement;

    if (currentPlayer == playerSB) {
      betSB = newTotalBet;
      currentBet = betSB;
    } else {
      betBB = newTotalBet;
      currentBet = betBB;
    }

    lastRaiseIncrement = raiseIncrement;

    System.out.println(currentPlayer.getPosition() + " raises to " + newTotalBet);
    return false;
  }


  private int getCurrentPlayerBet(Player currentPlayer) {
    if (currentPlayer == playerSB) {
      return betSB;
    } else {
      return betBB;
    }
  }

  public int getPot() {
    return this.pot;
  }

}


