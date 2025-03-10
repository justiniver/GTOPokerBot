package model;

public class BettingRound {
  private GameState state;
  private final Player playerSB;
  private final Player playerBB;
  private final int pot;

  public BettingRound(Player playerSB, Player playerBB, int pot, GameState state) {
    this.playerSB = playerSB;
    this.playerBB = playerBB;
    this.pot = pot;
    this.state = state;
  }

  public void run() {
    boolean roundOver = false;
    while (!roundOver) {

    }
  }



}
