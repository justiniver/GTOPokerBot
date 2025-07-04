package model;

import java.util.List;
import java.util.Scanner;

/**
 * Represents the betting round that occurs four times during a game of poker.
 */
public class BettingRound {
  private final GameState state;
  private final Player playerSB;
  private final Player playerBB;
  private int pot;
  private int betSB;
  private int betBB;
  private int currentBet;
  private int lastRaiseIncrement;
  private final int smallBlindAmount;
  private final int bigBlindAmount;
  private Player currPlayer;

  public BettingRound(Player playerSB, Player playerBB, int pot, GameState state,
                      int smallBlindAmount, int bigBlindAmount) {
    this.playerSB = playerSB;
    this.playerBB = playerBB;
    this.pot = pot;
    this.state = state;
    this.smallBlindAmount = smallBlindAmount;
    this.bigBlindAmount = bigBlindAmount;
    if (state == GameState.PREFLOP) {
      this.betSB = smallBlindAmount;
      this.betBB = bigBlindAmount;
    } else {
      this.betSB = 0;
      this.betBB = 0;
    }
  }

  /**
   * Execution method to run the betting round.
   * Does not terminate unless player actions indicate termination (e.g., a player folds).
   */
  public RoundCondition run() {
    Player currentPlayer;
    Player otherPlayer;
    if (state == GameState.PREFLOP) {
      currentPlayer = playerSB;
      otherPlayer = playerBB;
      currentBet = bigBlindAmount;
      playerBB.subtractStack(bigBlindAmount);
      playerSB.subtractStack(smallBlindAmount);
    } else {
      currentBet = 0;
      currentPlayer = playerBB;
      otherPlayer = playerSB;
    }


    boolean bettingComplete = false;

    while (!bettingComplete) {
      printGameState(currentPlayer, otherPlayer);

      GameView view = new GameView(
              state,
              pot,
              currentBet - getCurrentPlayerBet(currentPlayer),
              currentBet,
              bigBlindAmount,
              currentPlayer.getStack(),
              List.of(),
              currentPlayer.getHoleCards());

      Decision decision;
      RoundCondition roundCondition = null;
      boolean validAction = false;

      while (!validAction) {
        decision = currentPlayer.getStrategy().decide(view);

        try {
          roundCondition = processAction(decision.action(), decision.amount(), currentPlayer);
          validAction = true;
        } catch (IllegalStateException e) {
          System.out.println(e.getMessage());
        }
      }

      if (roundCondition == RoundCondition.FOLD) {
        System.out.println("Betting round ended due to " + currentPlayer.getPosition() + " folding.");
        this.currPlayer = currentPlayer;
        return roundCondition;
      } else if (roundCondition == RoundCondition.SHOWDOWN) {
        return roundCondition;
      }

      if (state == GameState.PREFLOP && pot == 2 * bigBlindAmount && currentPlayer == playerSB) { // limping logic
        System.out.println(currentPlayer.getPosition() + " limps in, action on " +
                otherPlayer.getPosition());
        Player temp = currentPlayer;
        currentPlayer = otherPlayer;
        otherPlayer = temp;
      } else if (betSB == betBB && currentBet != 0) { // Betting (or raising) gets called
        System.out.println("Both players have matched bets. Betting round complete.");
        if (currentPlayer.getStack() == 0 || otherPlayer.getStack() == 0 || state == GameState.RIVER) {
          return RoundCondition.SHOWDOWN;
        }
        bettingComplete = true;
      } else if (betSB == 0 && betBB == 0 && currentPlayer == playerSB) { // Both players check
        System.out.println("Both players have checked. Betting round complete.");
        if (state == GameState.RIVER) {
          return RoundCondition.SHOWDOWN;
        }
        bettingComplete = true;
      } else {
        Player temp = currentPlayer;
        currentPlayer = otherPlayer;
        otherPlayer = temp;
      }
    }

    System.out.println("Final pot: " + pot);
    return RoundCondition.CONTINUE;
  }

  private void printGameState(Player currentPlayer, Player otherPlayer) {
    System.out.println("\nPot: " + pot);
    System.out.println("Your opponents (" + otherPlayer.getPosition() + ") current bet this round: " + currentBet);
    System.out.println("Your (" + currentPlayer.getPosition() + ") current bet: "
            + getCurrentPlayerBet(currentPlayer) +
            " | Your stack: " + currentPlayer.getStack());
    if (currentBet - getCurrentPlayerBet(currentPlayer) > 0) {
      System.out.println("Amount to call is: " + (currentBet - getCurrentPlayerBet(currentPlayer)));
    }
  }

  /**
   * Processes the actions and prints out useful information to users regarding their action.
   *
   * @param action the current action (FOLD, CALL, CHECK, BET, RAISE)
   * @param currentPlayer the current player (small blind or big blind)
   * @return FOLD if someone folded, SHOWDOWN if someone calls for all their chips or action
   * is checked/called down on river, CONTINUE if betting round is not over.
   *
   * @throws IllegalStateException if action violates poker rules (e.g., raise size too small)
   */
  private RoundCondition processAction(Action action, int amount, Player currentPlayer) {
    return switch (action) {
      case FOLD -> RoundCondition.FOLD;
      case CHECK -> processCheck(currentPlayer);
      case CALL -> processCall(currentPlayer);
      case BET -> processBet(currentPlayer, amount);
      case RAISE -> processRaise(currentPlayer, amount);
    };

  }

  private RoundCondition processCheck(Player currentPlayer) {
    int currentPlayerBet = getCurrentPlayerBet(currentPlayer);
    if (currentPlayerBet < currentBet) {
      throw new IllegalStateException("Invalid action. Cannot check as current bet is " + currentBet);
    }

    return RoundCondition.CONTINUE;
  }

  private RoundCondition processCall(Player currentPlayer) {
    int currentPlayerBet = getCurrentPlayerBet(currentPlayer);
    int chipsNeededToCall = currentBet - currentPlayerBet;
    if (chipsNeededToCall <= 0) {
      throw new IllegalStateException("Nothing to call. Your current bet is " + currentPlayerBet +
                      " and the opponent bet " + currentBet);
    }
    if (currentPlayer.getStack() < chipsNeededToCall) {
      throw new IllegalStateException("Invalid action. Not enough chips in stack to call");
    }
    currentPlayer.subtractStack(chipsNeededToCall);
    pot += chipsNeededToCall;
    if (currentPlayer == playerSB) {
      betSB += chipsNeededToCall;
    } else {
      betBB += chipsNeededToCall;
    }
    System.out.println(currentPlayer.getPosition() + " calls for " + chipsNeededToCall);
    return RoundCondition.CONTINUE;
  }

  private RoundCondition processBet(Player currentPlayer, int amount) {
    if (currentBet != 0) {
      throw new IllegalStateException("Invalid action. Current bet is " + currentBet + ". Use RAISE instead");
    }
    if (currentPlayer.getStack() < amount) {
      throw new IllegalStateException("Invalid action. Not enough chips to bet " + amount);
    }
    // Allow all-in bets below minimum
    if (amount < bigBlindAmount && amount < currentPlayer.getStack()) {
      throw new IllegalStateException("Invalid action. You must bet at least " + bigBlindAmount + ", unless you are going all-in.");
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
    return RoundCondition.CONTINUE;
  }

  private RoundCondition processRaise(Player currentPlayer, int raiseToAmount) {
    int playerCurrentBet = getCurrentPlayerBet(currentPlayer);
    int callAmount = currentBet - playerCurrentBet;
    int raiseAmount = raiseToAmount - currentBet;
    int totalAmountRequired = callAmount + raiseAmount;

    if (currentBet == 0) {
      throw new IllegalStateException("Invalid action. Nothing to raise. Current bet is 0");
    }
    if (raiseToAmount <= currentBet) {
      throw new IllegalStateException("Invalid action. Your total bet must exceed the current bet of " + currentBet);
    }
    if (currentPlayer.getStack() < totalAmountRequired) {
      throw new IllegalStateException("Invalid action. Not enough chips to raise to " + raiseToAmount);
    }
    if (raiseAmount < lastRaiseIncrement && totalAmountRequired < currentPlayer.getStack()) {
      throw new IllegalStateException("Invalid action. You must raise by at least " + lastRaiseIncrement + ", unless you are going all-in.");
    }
    currentPlayer.subtractStack(totalAmountRequired);
    pot += totalAmountRequired;
    if (currentPlayer == playerSB) {
      betSB = raiseToAmount;
      currentBet = betSB;
    } else {
      betBB = raiseToAmount;
      currentBet = betBB;
    }
    lastRaiseIncrement = raiseAmount;
    System.out.println(currentPlayer.getPosition() + " raises to " + raiseToAmount);
    return RoundCondition.CONTINUE;
  }




  private int getCurrentPlayerBet(Player currentPlayer) {
    if (currentPlayer == playerSB) {
      return betSB;
    } else {
      return betBB;
    }
  }

  public Player getCurrPlayer() {
    return this.currPlayer;
  }

  public int getPot() {
    return this.pot;
  }

}


