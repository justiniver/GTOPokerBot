package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    while (true) {
      currentGame = new PokerGame(true, smallBlindAmount, bigBlindAmount,
              playerSB, playerBB);
    }
  }



  public List<Integer> getWinningHandRankFreq() {
    return new ArrayList<>(winningHandRankFreq.values());
  }

}
