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
  private final Map<HandRank, Integer> winningRankMap;
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
    this.winningRankMap = new HashMap<>();
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
      PokerController c = new PokerController();
      c.playHand(currentGame);
      HandRank bestHandRank = c.getBestHandRank();
      winningRankMap.put(bestHandRank, winningRankMap.getOrDefault(bestHandRank, 0) + 1);
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



  public Map winningRankMap() {
    return winningRankMap;
  }

  public void printRankAnalytics() {
    System.out.println("\n----------POKER SESSION HAND RANK FREQUENCIES----------\n");

    int entryCount = winningRankMap.size();
    int currentEntry = 0;

    StringBuilder builder = new StringBuilder();
    for (Map.Entry<HandRank, Integer> entry : winningRankMap.entrySet()) {
      currentEntry++;
      builder.append(entry.getKey()).append(" ").append(entry.getValue());

      if (currentEntry < entryCount) {
        builder.append("\n");
      }
    }

    System.out.println(builder);
  }

  public PokerGame getCurrentGame() {
    return currentGame;
  }

}
