package model;

import java.util.HashMap;
import java.util.Map;

public class PokerSession {
  private PokerGame pokerGame;
  private Map<HandRank, Integer> winningHandRankFreq;

  public PokerSession(PokerGame pokerGame) {
    this.pokerGame = pokerGame;
    this.winningHandRankFreq = new HashMap<>();
  }

  public void startNewGame(PokerGame pokerGame) {

  }

  public Map getWinningHandRankFreq() {
    return winningHandRankFreq;
  }

  public PokerGame getPokerGame() {
    return pokerGame;
  }

}
