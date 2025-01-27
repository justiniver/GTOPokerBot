package model.rules;

import java.util.*;

import model.*;

/**
 * Implementation of hand rules.
 */
public class HandEvaluation implements HandRules {

  @Override
  public boolean isStraightFlush(PokerHand hand) {
    return isStraight(hand) && isFlush(hand);
  }

  @Override
  public boolean isFourOAK(PokerHand Hand) {
    return false;
  }

  @Override
  public boolean isFullHouse(PokerHand Hand) {
    return false;
  }

  @Override
  public boolean isFlush(PokerHand hand) {
    Suit firstSuit = hand.getHand()[0].getSuit();
    for (int i = 1; i < hand.getHand().length; i++) {
      if (hand.getHand()[i].getSuit() != firstSuit)
        return false;
    }
    return true;
  }

  @Override
  public boolean isStraight(PokerHand hand) {
    Rank[] ranks = new Rank[5];
    for (int i = 0; i < hand.getHand().length; i++) {
      ranks[i] = hand.getHand()[i].getRank();
    }
    Arrays.sort(ranks);

    for (int i = 0; i < hand.getHand().length - 1; i++) {
      if (ranks[i].ordinal() != ranks[i + 1].ordinal() - 1) {
        return false;
      }
    }

    return true;
  }

  @Override
  public boolean isThreeOAK(PokerHand Hand) {

    return false;
  }

  @Override
  public boolean isTwoPair(PokerHand Hand) {
    return false;
  }

  @Override
  public boolean pair(PokerHand Hand) {
    return false;
  }

  @Override
  public HandRank evaluateHand(PokerHand hand) {
    return null;
  }

}
