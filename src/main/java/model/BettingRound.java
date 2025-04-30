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
  private int betSB;
  private int betBB;
  private int currentBet;
  private int lastRaiseIncrement;
  private final int smallBlindAmount;
  private final int bigBlindAmount;
  private Player currPlayer;
  Scanner scanner;

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
    this.scanner = new Scanner(System.in);

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
      System.out.println("\nPot: " + pot);
      System.out.println("Your opponents (" + otherPlayer.getPosition() + ") current bet this round: " + currentBet);
      System.out.println("Your (" + currentPlayer.getPosition() + ") current bet: "
              + getCurrentPlayerBet(currentPlayer) +
              " | Your stack: " + currentPlayer.getStack());
      if (currentBet - getCurrentPlayerBet(currentPlayer) > 0) {
        System.out.println("Amount to call is: " + (currentBet - getCurrentPlayerBet(currentPlayer)));
      }

      Action action = getAction();

      RoundCondition roundCondition = getRoundCondition(action, currentPlayer);

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

  /**
   * Prompts and returns player action.
   * Will continue prompting until valid action is inputted.
   *
   * @return the inputted action
   */
  private Action getAction() {
    System.out.println("Enter action (FOLD, CHECK, CALL, BET, RAISE): ");
    Action action = null;
    while (action == null) {
      String input = scanner.nextLine().trim().toUpperCase();
      try {
        action = Action.valueOf(input);
      } catch (IllegalArgumentException e){
        System.out.println("Invalid action (check spelling). " +
                "Please enter a valid action (FOLD, CHECK, CALL, BET, RAISE).");
      }
    }

    return action;
  }

  /**
   * Returns the round condition the chosen action yields.
   * Re-prompts getAction if a bet or raise size is invalid
   *
   * @param action the action
   * @param currentPlayer the current player
   * @return the round condition
   */
  private RoundCondition getRoundCondition(Action action, Player currentPlayer) {
    RoundCondition roundCondition = null;
    while (roundCondition == null) {
      try {
        roundCondition = processAction(action, currentPlayer);
      } catch (IllegalStateException illegalStateExceptions) {
        System.out.println(illegalStateExceptions.getLocalizedMessage());
        action = getAction();
      }
    }

    return roundCondition;
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
  private RoundCondition processAction(Action action, Player currentPlayer) {
    return switch (action) {
      case FOLD -> RoundCondition.FOLD;
      case CHECK -> processCheck(currentPlayer);
      case CALL -> processCall(currentPlayer);
      case BET -> processBet(currentPlayer);
      case RAISE -> processRaise(currentPlayer);
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

  private RoundCondition processBet(Player currentPlayer) {
    if (currentBet != 0) {
      throw new IllegalStateException("Invalid action. Current bet is " + currentBet + ". Use RAISE instead");
    }
    System.out.println("Enter amount to bet: ");
    Scanner scanInt = new Scanner(System.in);
    int amount = scanInt.nextInt();

    if (amount < bigBlindAmount) {
      throw new IllegalStateException("Invalid action. You must bet at least " + bigBlindAmount);
    }

    if (currentPlayer.getStack() < amount) {
      throw new IllegalStateException("Invalid action. Not enough chips to bet " + amount);
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

  private RoundCondition processRaise(Player currentPlayer) {
    int currentPlayerBet = getCurrentPlayerBet(currentPlayer);
    if (currentBet == 0) {
      throw new IllegalStateException("Invalid action. Nothing to raise. Current bet is 0");
    }
    System.out.println("Enter amount to raise by: ");
    Scanner scanInt = new Scanner(System.in);
    int raiseIncrement = scanInt.nextInt();
    int newTotalBet = currentPlayerBet + raiseIncrement;

    if (raiseIncrement < lastRaiseIncrement) {
      throw new IllegalStateException("Invalid action. You must raise by at least " + lastRaiseIncrement);
    }

    if (newTotalBet <= currentBet) {
      throw new IllegalStateException("Invalid action. Your total bet must exceed the current bet of " + currentBet);
    }

    if (currentPlayer.getStack() < raiseIncrement) {
      throw new IllegalStateException("Invalid action. Not enough chips to raise by that amount.");
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


