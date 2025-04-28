package controller;

import model.PokerGame;
import model.BettingRound;

public class PokerController implements Controller {
  public PokerController() {
  }

  @Override
  public void playHand(PokerGame pokerGame) {
    System.out.println("Current state " + pokerGame.getState());
    pokerGame.dealHoleCards();
    BettingRound preflopBR = new BettingRound(pokerGame.getPlayerSB(), pokerGame.getPlayerBB(),
            pokerGame.getPot(), pokerGame.getState(), pokerGame.getSmallBlindAmount(),
            pokerGame.getBigBlindAmount());
    if (!preflopBR.run()) {

      return;
    }

    System.out.println("Current state " + pokerGame.getState());
    pokerGame.dealFlop();
    BettingRound flopBR = new BettingRound(pokerGame.getPlayerSB(), pokerGame.getPlayerBB(),
            pokerGame.getPot(), pokerGame.getState(), pokerGame.getSmallBlindAmount(),
            pokerGame.getBigBlindAmount());
    if (!flopBR.run()) {
      playerFoldLogic();
      return;
    }

    System.out.println("Current state " + pokerGame.getState());
    pokerGame.dealTurn();
    BettingRound turnBR = new BettingRound(pokerGame.getPlayerSB(), pokerGame.getPlayerBB(),
            pokerGame.getPot(), pokerGame.getState(), pokerGame.getSmallBlindAmount(),
            pokerGame.getBigBlindAmount());

    if (!turnBR.run()) {
      playerFoldLogic();
      return;
    }

    System.out.println("Current state " + pokerGame.getState());
    pokerGame.dealRiver();
    BettingRound riverBR = new BettingRound(pokerGame.getPlayerSB(), pokerGame.getPlayerBB(),
            pokerGame.getPot(), pokerGame.getState(), pokerGame.getSmallBlindAmount(),
            pokerGame.getBigBlindAmount());

    if (!riverBR.run()) {
      playerFoldLogic();
    } else {
      showdownLogic();
    }
  }

  private void playerFoldLogic() {

  }

  private void showdownLogic() {

  }

}
