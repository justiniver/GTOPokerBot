package model;

import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

import controller.PokerController;

public class PokerSession {
  private final Map<HandRank, Integer> winningRankMap;
  private PokerGame currentGame;
  private final int smallBlindAmount;
  private final int bigBlindAmount;
  private final Player player1;
  private final Player player2;
  private int handNumber = 0;

  private boolean trackWinningHands = false;

  public PokerSession(int smallBlindAmount, int bigBlindAmount,
                      Player player1, Player player2) {
    this.smallBlindAmount = smallBlindAmount;
    this.bigBlindAmount = bigBlindAmount;
    this.player1 = player1;
    this.player2 = player2;
    this.winningRankMap = new TreeMap<>();
  }

  public void setTrackWinningHands(boolean bool) {
    trackWinningHands = bool;
  }

  public void runGames() {
    boolean continuePlay = true;
    while (continuePlay) {
      currentGame = createNextGame();
      PokerController controller = new PokerController();
      controller.playHand(currentGame);
      continuePlay = promptContinueGame();
    }
    concludedGameOutput();
  }

  public void runNumberOfGames(int numberOfGames) {
    for (int i = 0; i < numberOfGames; i++) {
      System.out.println("\n----------Game Number: " + (i + 1) + "----------");
      currentGame = createNextGame();
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

      setBackToInitialStack(player1);
      setBackToInitialStack(player2);

      currentGame = createNextGame();
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
  }

  private PokerGame createNextGame() {
    Player sb = (handNumber % 2 == 0) ? player1 : player2;
    Player bb = (handNumber % 2 == 0) ? player2 : player1;
    System.out.println("\n" + sb.getName() + ": SB | " + bb.getName() + ": BB");
    handNumber++;
    return new PokerGame(true, smallBlindAmount, bigBlindAmount, sb, bb);
  }

  private boolean promptContinueGame() {
    Scanner scanner = new Scanner(System.in);
    System.out.println("\nWould you like to continue playing (YES or NO)? \n");
    String in = scanner.next().trim();
    return in.equalsIgnoreCase("YES") || in.equalsIgnoreCase("y");
  }

  public void concludedGameOutput() {
    System.out.println("\n----------SESSION RESULTS----------\n");
    printPlayerResult(player1);
    printPlayerResult(player2);
  }

  private void printPlayerResult(Player player) {
    int endStack = player.getStack();
    System.out.println(player.getName() + " end stack: " + endStack);
    if (endStack > 0) {
      player.buyOut(endStack);
    }
    System.out.println(player.getName() + " net profit: " + (player.getBuyOut() - player.getBuyIn()));
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
