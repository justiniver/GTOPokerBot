import org.junit.Before;

import controller.PokerController;
import model.BettingRound;
import model.Player;
import model.PokerGame;
import model.Position;
import model.rules.HandEvaluation;
import util.CardStrings;

/**
 * Testing a full game using PokerGame and BettingRound
 */
public class TestFullGame {
  private HandEvaluation eval;
  private Player playerSB;
  private Player playerBB;
  private PokerGame pokerGame;
  private PokerController pokerController;
  private CardStrings cs;
  private BettingRound br;

  @Before
  public void init() {
    this.cs = new CardStrings();
    this.playerSB  = new Player(Position.SMALL_BLIND, 1000);
    this.playerBB = new Player(Position.BIG_BLIND, 1000);
    this.pokerGame = new PokerGame(true, 5, 10, playerSB, playerBB);
    this.pokerController = new PokerController();
    // this.br = new BettingRound(playerSB, playerBB)
  }



}
