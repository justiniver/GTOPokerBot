package model;

import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

import controller.PokerController;

/**
 * Represents a poker session (i.e., multiple poker games).
 * Updates player stack sizes, tracks buy-ins and buy-outs, and calculates poker statistics.
 *
 * NOTE: SOLVE THE NON-ZERO-SUM MYSTERY WHEN RUNNING RULE BASED BOTS AGAINST EACH-OTHER
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
      System.out.println("\n----------Game Number: " + (i + 1) + "----------\n"); // 0 -> 1-indexed

      currentGame = new PokerGame(true, smallBlindAmount, bigBlindAmount,
              playerSB, playerBB);
      PokerController c = new PokerController();
      c.playHand(currentGame);

      if (trackWinningHands) {
        try {
          HandRank bestHandRank = c.getBestHandRank();
          if (bestHandRank != null) {
            winningRankMap.put(bestHandRank, winningRankMap.getOrDefault(bestHandRank, 0) + 1);
          }
        } catch (Exception e) {
          System.out.println("Note: Could not track winning hand for this game.");
        }
      }
    }

    concludedGameOutput();
    setBackToInitialStack(playerSB);
    setBackToInitialStack(playerBB);
  }

  private boolean promptContinueGame() {
    Scanner scanner = new Scanner(System.in);
    System.out.println("\nWould you like to continue playing (YES or NO)? \n");
    return scanner.next().trim().equalsIgnoreCase("YES");
  }

  public void concludedGameOutput() {
    int endStackSB = playerSB.getStack();
    int endStackBB = playerBB.getStack();
    if (endStackSB > 0) {
      playerSB.buyOut(endStackSB);
    }
    if (endStackBB > 0) {
      playerBB.buyOut(endStackBB);
    }

    System.out.println("\n----------POKER SESSION HAS CONCLUDED----------\n");
    System.out.println("SMALL_BLIND end stack: " + endStackSB);
    System.out.println("SMALL_BLIND net profit: " + (playerSB.getBuyOut() - playerSB.getBuyIn()) + endStackSB);
    System.out.println("BIG_BLIND end stack: " + endStackBB);
    System.out.println("BIG_BLIND net profit: " + (playerBB.getBuyOut() - playerBB.getBuyIn()) + endStackBB); // FIX NET PROFIT
  }


  public Map getWinningRankMap() {
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

  public void setBackToInitialStack(Player player) {
    int initialStack = player.getInitialStack();
    int currentStack = player.getStack();

    if (currentStack < initialStack) {
      int buyinAmount = initialStack - currentStack;
      player.addToBuyIn(buyinAmount);
    } else if (currentStack > initialStack) {
      int cashoutAmount = currentStack - initialStack;
      player.buyOut(cashoutAmount);
    }
  }

  public PokerGame getCurrentGame() {
    return currentGame;
  }

}
