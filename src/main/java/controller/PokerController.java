package controller;

import model.HandRank;
import model.PokerBoard;
import model.GameState;
import model.Player;
import model.PokerGame;
import model.BettingRound;
import model.PokerHand;
import model.Position;
import model.RoundCondition;
import model.rules.HandEvaluation;

/**
 * Controller to run through individual poker games.
 */
public class PokerController implements Controller {
  PokerHand bestHand;

  public PokerController() {
  }

  @Override
  public void playHand(PokerGame pokerGame) {
    System.out.println("\n----------Current state: " + GameState.PREFLOP + "----------");
    pokerGame.dealHoleCards();
    BettingRound preflopBR = new BettingRound(pokerGame.getPlayerSB(), pokerGame.getPlayerBB(),
            pokerGame.getPot(), GameState.PREFLOP, pokerGame.getSmallBlindAmount(),
            pokerGame.getBigBlindAmount());

    if (checkRoundCondition(pokerGame, preflopBR)) {
      return;
    }

    System.out.println("\n----------Current state: " + GameState.FLOP + "----------");
    pokerGame.dealFlop();
    System.out.println(pokerGame.getBoard());
    BettingRound flopBR = new BettingRound(pokerGame.getPlayerSB(), pokerGame.getPlayerBB(),
            pokerGame.getPot(), GameState.FLOP, pokerGame.getSmallBlindAmount(),
            pokerGame.getBigBlindAmount());

    if (checkRoundCondition(pokerGame, flopBR)) {
      return;
    }

    System.out.println("\n----------Current state: " + GameState.TURN + "----------");
    pokerGame.dealTurn();
    System.out.println(pokerGame.getBoard());
    BettingRound turnBR = new BettingRound(pokerGame.getPlayerSB(), pokerGame.getPlayerBB(),
            pokerGame.getPot(), GameState.TURN, pokerGame.getSmallBlindAmount(),
            pokerGame.getBigBlindAmount());

    if (checkRoundCondition(pokerGame, turnBR)) {
      return;
    }

    System.out.println("\n----------Current state: " + GameState.RIVER + "----------");
    pokerGame.dealRiver();
    System.out.println(pokerGame.getBoard());
    BettingRound riverBR = new BettingRound(pokerGame.getPlayerSB(), pokerGame.getPlayerBB(),
            pokerGame.getPot(), GameState.RIVER, pokerGame.getSmallBlindAmount(),
            pokerGame.getBigBlindAmount());

    checkRoundCondition(pokerGame, riverBR);
  }

  /**
   * @param pokerGame the poker game
   * @param bettingRound the current betting round
   * @return true if hand is over, false otherwise
   */
  private boolean checkRoundCondition(PokerGame pokerGame, BettingRound bettingRound) {
    RoundCondition flopRoundCondition = bettingRound.run();
    if (flopRoundCondition == RoundCondition.FOLD) {
      playerFoldLogic(pokerGame, bettingRound);
      return true;
    } else if (flopRoundCondition == RoundCondition.SHOWDOWN) {
      pokerGame.setPot(bettingRound.getPot());
      showdownLogic(pokerGame);
      return true;
    } else {
      pokerGame.setPot(bettingRound.getPot());
    }
    return false;
  }

  private void playerFoldLogic(PokerGame pokerGame, BettingRound bettingRound) {
    Player playerSB = pokerGame.getPlayerSB();
    Player playerBB = pokerGame.getPlayerBB();

    if (bettingRound.getCurrPlayer().getPosition() == Position.SMALL_BLIND) {
      playerBB.addStack(pokerGame.getPot());
    } else {
      playerSB.addStack(pokerGame.getPot());
    }

    System.out.println("New SMALL_BLIND stack: " + playerSB.getStack());
    System.out.println("New BIG_BLIND stack: " + playerBB.getStack());
  }

  private void showdownLogic(PokerGame pokerGame) {
    if (pokerGame.getState() == GameState.PREFLOP) {
      pokerGame.dealFlop();
      System.out.println(pokerGame.getBoard());
      pokerGame.dealTurn();
      System.out.println(pokerGame.getBoard());
      pokerGame.dealRiver();
      System.out.println(pokerGame.getBoard());
    } else if (pokerGame.getState() == GameState.FLOP) {
      pokerGame.dealTurn();
      System.out.println(pokerGame.getBoard());
      pokerGame.dealRiver();
      System.out.println(pokerGame.getBoard());
    } else if (pokerGame.getState() == GameState.TURN) {
      pokerGame.dealRiver();
      System.out.println(pokerGame.getBoard());
    }


    HandEvaluation eval = new HandEvaluation();
    Player playerSB = pokerGame.getPlayerSB();
    Player playerBB = pokerGame.getPlayerBB();
    PokerBoard board = pokerGame.getBoard();
    PokerHand handSB = pokerGame.getBestFiveCardHand(playerSB, board);
    PokerHand handBB = pokerGame.getBestFiveCardHand(playerBB, board);


    System.out.println("\n----------SHOWDOWN----------\n");
    System.out.println(pokerGame.getBoard().toString());
    System.out.println("SMALL_BLIND " + playerSB.getHoleCards().toString());
    System.out.println("BIG_BLIND " + playerBB.getHoleCards().toString());

    if (eval.isHand1Better(handSB, handBB)) {
      bestHand = handSB;
      System.out.println("SMALL_BLIND wins with: " + handSB.getHandRank()
              + " (" + handSB.toString() + ")");
      playerSB.addStack(pokerGame.getPot());
    } else if (!eval.isHand1Better(handSB, handBB)) {
      bestHand = handBB;
      System.out.println("BIG_BLIND wins with: " + handBB.getHandRank()
              + " (" + handBB.toString() + ")");
      playerBB.addStack(pokerGame.getPot());
    } else if (eval.isHand1Better(handSB, handBB) == null) {
      throw new IllegalStateException("IMPLEMENT LATER");
    }

    System.out.println("New SMALL_BLIND stack: " + playerSB.getStack());
    System.out.println("New BIG_BLIND stack: " + playerBB.getStack());
  }

  public PokerHand getBestHand() {
    return bestHand;
  }

  public HandRank getBestHandRank() {
    return bestHand.getHandRank();
  }

}
