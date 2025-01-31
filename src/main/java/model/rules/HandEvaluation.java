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
  public boolean isFourOAK(PokerHand hand) {
    HashMap<Rank, Integer> rankFreqMap = getRankIntegerHashMap(hand);

    if (rankFreqMap.size() != 2) {
      return false;
    }

    int rankFreq = rankFreqMap.get(hand.getHand()[0].getRank());
    return rankFreq == 4 || rankFreq == 1;

  }

  @Override
  public boolean isFullHouse(PokerHand hand) {
    HashMap<Rank, Integer> rankFreqMap = getRankIntegerHashMap(hand);

    if (rankFreqMap.size() != 2) {
      return false;
    }

    int rankFreq = rankFreqMap.get(hand.getHand()[0].getRank());
    return rankFreq == 3 || rankFreq == 2;
  }

  private HashMap<Rank, Integer> getRankIntegerHashMap(PokerHand hand) {
    HashMap<Rank, Integer> rankFreqMap = new HashMap<>();

    for (int i = 0; i < hand.getHand().length; i++) {
      Rank cardRank = hand.getHand()[i].getRank();
      rankFreqMap.put(cardRank, rankFreqMap.getOrDefault(cardRank, 0) + 1);
    }
    return rankFreqMap;
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
    sortRanks(hand, ranks);

    for (int i = 0; i < hand.getHand().length - 1; i++) {
      if (ranks[i].ordinal() != ranks[i + 1].ordinal() - 1) {
        return false;
      }
    }

    return true;
  }

  private void sortRanks(Hand hand, Rank[] ranks) {
    for (int i = 0; i < hand.getHand().length; i++) {
      ranks[i] = hand.getHand()[i].getRank();
    }
    Arrays.sort(ranks);
  }

  @Override
  public boolean isThreeOAK(PokerHand hand) {
    HashMap<Rank, Integer> rankFreqMap = getRankIntegerHashMap(hand);
    for (int i = 0; i < hand.getHand().length; i++) {
      if (rankFreqMap.get(hand.getHand()[i].getRank()) == 3) {
        return true;
      }
    }

    return false;
  }

  @Override
  public boolean isTwoPair(PokerHand hand) {
    HashMap<Rank, Integer> rankFreqMap = getRankIntegerHashMap(hand);
    boolean twoFreqExists = false;
    for (int i = 0; i < hand.getHand().length; i++) {
      if (rankFreqMap.get(hand.getHand()[i].getRank()) == 2) {
        twoFreqExists = true;
        break;
      }
    }
    return twoFreqExists && rankFreqMap.size() == 3;
  }

  @Override
  public boolean pair(PokerHand hand) {
    HashMap<Rank, Integer> rankFreqMap = getRankIntegerHashMap(hand);
    boolean twoFreqExists = false;
    for (int i = 0; i < hand.getHand().length; i++) {
      if (rankFreqMap.get(hand.getHand()[i].getRank()) == 2) {
        twoFreqExists = true;
        break;
      }
    }
    return twoFreqExists && rankFreqMap.size() == 4;
  }

  @Override
  public HandRank evaluateHand(PokerHand hand) {
    return null;
  }

}
