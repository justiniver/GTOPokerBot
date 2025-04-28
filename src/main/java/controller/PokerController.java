package controller;

import model.PokerGame;
import model.BettingRound;

public class PokerController implements Controller {
  public PokerController() {
  }

  @Override
  public void playHand(PokerGame pokerGame) {
    pokerGame.dealHoleCards();
    BettingRound preflopBR = new BettingRound(pokerGame.getPlayerSB(), pokerGame.getPlayerBB(),
            pokerGame.getPot(), pokerGame.getState(), pokerGame.getSmallBlindAmount(),
            pokerGame.getBigBlindAmount());
    if (!preflopBR.run()) {
      return;
    }

    pokerGame.dealFlop();
    BettingRound flopBR = new BettingRound(pokerGame.getPlayerSB(), pokerGame.getPlayerBB(),
            pokerGame.getPot(), pokerGame.getState(), pokerGame.getSmallBlindAmount(),
            pokerGame.getBigBlindAmount());
    if (!flopBR.run()) {
      return;
    }

    pokerGame.dealTurn();
    BettingRound turnBR = new BettingRound(pokerGame.getPlayerSB(), pokerGame.getPlayerBB(),
            pokerGame.getPot(), pokerGame.getState(), pokerGame.getSmallBlindAmount(),
            pokerGame.getBigBlindAmount());

    if (!turnBR.run()) {
      return;
    }

    pokerGame.dealRiver();
    BettingRound riverBR = new BettingRound(pokerGame.getPlayerSB(), pokerGame.getPlayerBB(),
            pokerGame.getPot(), pokerGame.getState(), pokerGame.getSmallBlindAmount(),
            pokerGame.getBigBlindAmount());

    if (!riverBR.run()) {
      return;
    }
  }
}
