package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import controller.PokerController;

/**
 * Represents a poker session (i.e., multiple poker games).
 * Updates player stack sizes, tracks buy-ins and buy-outs, and calculates poker statistics.
 */
public class PokerSession {
  private Map<HandRank, Integer> winningHandRankFreq;
  private PokerGame currentGame;
  private final int smallBlindAmount;
  private final int bigBlindAmount;
  private final Player playerSB;
  private final Player playerBB;

  public PokerSession(int smallBlindAmount, int bigBlindAmount,
                      Player playerSB, Player playerBB) {
    this.smallBlindAmount = smallBlindAmount;
    this.bigBlindAmount = bigBlindAmount;
    this.playerSB = playerSB;
    this.playerBB = playerBB;
    this.winningHandRankFreq = new HashMap<>();
  }

  public void runGames() {
    boolean continuePlay = true;
    while (continuePlay) {
      currentGame = new PokerGame(true, smallBlindAmount, bigBlindAmount,
              playerSB, playerBB);
      PokerController controller = new PokerController();
      controller.playHand(currentGame);
      continuePlay = promptContinueGame();
    }
    concludedGameOutput();
  }

  public void runNumberOfGames(int numberOfGames) {
    for (int i = 0; i < numberOfGames; i++) {
      currentGame = new PokerGame(true, smallBlindAmount, bigBlindAmount,
              playerSB, playerBB);
      PokerController controller = new PokerController();
      controller.playHand(currentGame);
    }
    concludedGameOutput();
  }

  private boolean promptContinueGame() {
    Scanner scanner = new Scanner(System.in);
    System.out.println("\nWould you like to continue playing (YES or NO)? \n");
    return scanner.next().trim().equalsIgnoreCase("YES");
  }

  public void concludedGameOutput() {
    System.out.println("\n----------POKER SESSION HAS CONCLUDED----------\n");
    System.out.println("SMALL_BLIND stack: " + playerSB.getStack());
    System.out.println("BIG_BLIND stack: " + playerBB.getStack());
  }



  public List<Integer> getWinningHandRankFreq() {
    return new ArrayList<>(winningHandRankFreq.values());
  }

  public PokerGame getCurrentGame() {
    return currentGame;
  }

}
