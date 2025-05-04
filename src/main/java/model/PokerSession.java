package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PokerSession {
  private PokerGame pokerGame;
  private Map<HandRank, Integer> winningHandRankFreq;
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

  public void startNewGame(PokerGame pokerGame) {

  }

  public List<Integer> getWinningHandRankFreq() {
    return new ArrayList<>(winningHandRankFreq.values());
  }


  public PokerGame getPokerGame() {
    return pokerGame;
  }

}
