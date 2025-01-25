package model.rules;

import java.util.Arrays;
import java.util.HashSet;

import model.*;


public class HandEvaluation implements HandRules {
  private boolean isStraightFlush(PokerHand hand) {
    return isStraight(hand) && isFlush(hand);
  }

  private boolean isFlush(PokerHand hand) {
    HashSet<Suit> suitSet = new HashSet<>();
    for (int i = 0; i < hand.getHand().length; i++) {
      suitSet.add(hand.getHand()[i].getSuit());
    }

    return suitSet.size() == 1;
  }

  private boolean isStraight(PokerHand hand) {
    Rank[] ranks = new Rank[5];
    for (int i = 0; i < hand.getHand().length; i++) {
      ranks[i] = hand.getHand()[i].getRank();
    }
    Arrays.sort(ranks);

    for (int i = 0; i < hand.getHand().length - 1; i++) {
      if (ranks[i] != ranks[i + 1]) {
        return false;
      }
    }

    return true;
  }

  @Override
  public HandRank evaluateHand(PokerHand hand) {
    return null;
  }

}
