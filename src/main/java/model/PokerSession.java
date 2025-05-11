package model;

import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

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

  private boolean trackWinningHands = false;

  public PokerSession(int smallBlindAmount, int bigBlindAmount,
                      Player playerSB, Player playerBB) {
    this.smallBlindAmount = smallBlindAmount;
    this.bigBlindAmount = bigBlindAmount;
    this.playerSB = playerSB;
    this.playerBB = playerBB;
    this.winningRankMap = new TreeMap<>();
  }

  public void setTrackWinningHands(boolean bool) {
    trackWinningHands = bool;
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
      System.out.println("\n----------Game Number: " + (i + 1) + "----------");
      currentGame = new PokerGame(true, smallBlindAmount, bigBlindAmount,
              playerSB, playerBB);
      PokerController c = new PokerController();
      c.playHand(currentGame);

      if (trackWinningHands) {
        HandRank bestHandRank = c.getBestHandRank();
        winningRankMap.put(bestHandRank, winningRankMap.getOrDefault(bestHandRank, 0) + 1);
      }
    }
    concludedGameOutput();
  }

  public void runNumberOfGamesAutoRebuy(int numberOfGames) {
    for (int i = 0; i < numberOfGames; i++) {
      System.out.println("\n----------Game Number: " + (i + 1) + "----------");
      setBackToInitialStack(playerSB);
      setBackToInitialStack(playerBB);
      currentGame = new PokerGame(true, smallBlindAmount, bigBlindAmount,
              playerSB, playerBB);
      PokerController c = new PokerController();
      c.playHand(currentGame);

      if (trackWinningHands) {
        HandRank bestHandRank = c.getBestHandRank();
        winningRankMap.put(bestHandRank, winningRankMap.getOrDefault(bestHandRank, 0) + 1);
      }
    }
    concludedGameOutput();
  }

  private boolean promptContinueGame() {
    Scanner scanner = new Scanner(System.in);
    System.out.println("\nWould you like to continue playing (YES or NO)? \n");
    return scanner.next().trim().equalsIgnoreCase("YES");
  }

  public void concludedGameOutput() {
    int endStackSB = playerSB.getStack();
    int endStackBB = playerBB.getStack();
    playerSB.buyOut(playerSB.getStack());
    playerBB.buyOut(playerBB.getStack());
    System.out.println("\n----------POKER SESSION HAS CONCLUDED----------\n");
    System.out.println("SMALL_BLIND end stack: " + endStackSB);
    System.out.println("SMALL_BLIND net profit: " + (playerSB.getBuyIn() - playerSB.getBuyOut()));
    System.out.println("BIG_BLIND end stack: " + endStackBB);
    System.out.println("BIG_BLIND net profit: " + (playerBB.getBuyIn() - playerBB.getBuyOut()));
  }


  public Map getWinningRankMap() {
    return winningRankMap;
  }

  // Traversing unordered hashmap results in random hand rank ordering--fix later
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

  public void setBackToInitialStack(Player player) {
    int diff = player.getInitialStack() - player.getStack();
    if (diff > 0) {
      player.addToBuyIn(diff);
    } else if (diff < 0) {
      player.buyOut(Math.abs(diff));
    }
  }

  public PokerGame getCurrentGame() {
    return currentGame;
  }

}
