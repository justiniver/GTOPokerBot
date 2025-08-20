package bots;

import model.*;
import java.util.Random;

/**
 * A rule-based bot that follows a set of rules to make decisions.
 */
public class RuleBasedBot implements PlayerStrategy {
  private final Random random = new Random();

  private final double tightness;
  private final double aggression;
  private int consecutiveFolds = 0;
  private boolean opponentAggressive = false;
  private int roundsPlayed = 0;

  public RuleBasedBot(double tightness, double aggression) {
    this.tightness = tightness;
    this.aggression = aggression;
  }

  @Override
  public Decision decide(GameView view) {
    roundsPlayed++;

    double handStrength = evaluateHandStrength(view);
    double positionValue = getPositionValue(view);
    double stackLeverage = getStackLeverage(view);
    double playStyle = determinePlayStyle();

    double decisionScore = handStrength * 0.5 +
            positionValue * 0.2 +
            stackLeverage * 0.1 +
            playStyle * 0.2;

    if (view.toCall() > 0) {
      return handleFacingBet(view, decisionScore);
    } else {
      return handleOpenAction(view, decisionScore);
    }
  }

  /**
   * Determines how to respond when facing a bet
   */
  private Decision handleFacingBet(GameView view, double decisionScore) {
    double potOdds = PokerCalculations.potOdds(view.pot(), view.toCall());
    int minRaise = view.bigBlindAmount();
    int stack = view.myStack();

    double raiseThreshold = 0.7 - (aggression * 0.2);

    if (decisionScore > raiseThreshold && stack >= view.toCall() + minRaise) {
      int raiseTo = Math.min(stack, view.toCall() + minRaise);
      return new Decision(Action.RAISE, raiseTo);
    }

    if (decisionScore > potOdds - (random.nextDouble() * 0.1)) {
      consecutiveFolds = 0;
      if (stack >= view.toCall()) {
        return Decision.call();
      } else {
        return Decision.fold();
      }
    }

    if (random.nextDouble() < 0.1 - (tightness * 0.05)) {
      consecutiveFolds = 0;
      if (stack >= view.toCall()) {
        return Decision.call();
      } else {
        return Decision.fold();
      }
    }

    consecutiveFolds++;
    return Decision.fold();
  }

  /**
   * Determines what to do when checking or betting is an option
   */
  private Decision handleOpenAction(GameView view, double decisionScore) {
    int minRaise = view.bigBlindAmount();
    int stack = view.myStack();

    if (decisionScore < 0.3 + (tightness * 0.1)) {
      return Decision.check();
    }

    if (decisionScore > 0.5 - (aggression * 0.1)) {
      if (stack >= minRaise) {
        if (view.currentBet() == 0) {
          return new Decision(Action.BET, minRaise);
        } else if (stack >= view.toCall() + minRaise) {
          return new Decision(Action.RAISE, view.toCall() + minRaise);
        }
      } else if (stack > 0) {
        // Not enough for min bet/raise, go all-in if possible
        if (view.currentBet() == 0) {
          return new Decision(Action.BET, stack);
        } else if (stack > view.toCall()) {
          return new Decision(Action.RAISE, stack);
        }
      }
    }

    return Decision.check();
  }

  /**
   * Evaluates hand strength based on hole cards and board
   * Returns a value between 0-1 representing hand strength
   */
  private double evaluateHandStrength(GameView view) {
    HoleCards holeCards = view.myCards();
    GameState street = view.street();

    if (street == GameState.PREFLOP) {
      return PokerCalculations.evaluatePreflopHand(holeCards);
    }

    Card card1 = holeCards.getCard1();
    Card card2 = holeCards.getCard2();

    int highCardCount = 0;
    if (card1.getRank().ordinal() >= 10) highCardCount++;
    if (card2.getRank().ordinal() >= 10) highCardCount++;

    boolean hasPair = card1.getRank() == card2.getRank();
    boolean suited = card1.getSuit() == card2.getSuit();

    double strength = 0.3;
    strength += highCardCount * 0.1;
    if (hasPair) strength += 0.3;
    if (suited) strength += 0.1;

    switch (street) {
      case FLOP:
        strength *= 0.9;
        break;
      case TURN:
        strength *= 1.0;
        break;
      case RIVER:
        strength *= 1.1;
        break;
      default:
        break;
    }

    return Math.min(1.0, strength);
  }

  /**
   * Returns positional value (being in position is an advantage)
   */
  private double getPositionValue(GameView view) {
    if (view.street() == GameState.PREFLOP) {
      return view.myStack() < view.bigBlindAmount() * 20 ? 0.4 : 0.6;
    } else {
      return 0.5;
    }
  }

  /**
   * Calculates stack leverage - how stack size affects decision-making
   */
  private double getStackLeverage(GameView view) {
    double effectiveStackInBB = PokerCalculations.effectiveStackInBB(
            view.myStack(),
            view.bigBlindAmount()
    );

    if (effectiveStackInBB < 10) {
      return 0.7;
    } else if (effectiveStackInBB > 50) {
      return 0.6;
    } else {
      return 0.5;
    }
  }

  /**
   * Dynamically adjust play style based on game flow
   */
  private double determinePlayStyle() {
    if (consecutiveFolds > 3) {
      return Math.min(1.0, aggression + 0.2);
    }

    if (opponentAggressive) {
      return aggression - 0.1;
    }

    double playStyleVariation = Math.sin(roundsPlayed / 5.0) * 0.1;

    return Math.max(0.1, Math.min(0.9, aggression + playStyleVariation));
  }

  /**
   * Update opponent model based on their actions
   * This would be called after seeing opponent actions
   */
  public void updateOpponentModel(Action opponentAction, int betSize, int potSize) {
    if (opponentAction == Action.RAISE || opponentAction == Action.BET) {
      double betSizeToPotRatio = (double) betSize / potSize;
      if (betSizeToPotRatio > 0.7) {
        opponentAggressive = true;
      } else if (betSizeToPotRatio < 0.3) {
        opponentAggressive = false;
      }
    }
  }
}
