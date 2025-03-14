package model.rules;

import java.util.*;

import model.*;

/**
 * Implementation of hand rules and the evaluation logic of Poker.
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
  public boolean isPair(PokerHand hand) {
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
  public PokerHand breakTie(PokerHand hand1, PokerHand hand2) {
    Rank[] ranks1 = new Rank[5];
    Rank[] ranks2 = new Rank[5];
    sortRanks(hand1, ranks1);
    sortRanks(hand2, ranks2);

    for (int i = 0; i < ranks1.length; i++) {
      if (ranks1[i].ordinal() > ranks2[i].ordinal()) {
        return hand1;
      } else if (ranks1[i].ordinal() < ranks2[i].ordinal()) {
        return hand2;
      }
    }

    return hand1;
  }

  @Override
  public HandRank evaluateHand(PokerHand hand) {
    if (isStraightFlush(hand)) {
      return HandRank.STRAIGHTFLUSH;
    } else if (isFourOAK(hand)) {
      return HandRank.FOUROAK;
    } else if (isFullHouse(hand)) {
      return HandRank.FULLHOUSE;
    } else if (isFlush(hand)) {
      return HandRank.FLUSH;
    } else if (isStraight(hand)) {
      return HandRank.STRAIGHT;
    } else if (isThreeOAK(hand)) {
      return HandRank.THREEOAK;
    } else if (isTwoPair(hand)) {
      return HandRank.TWOPAIR;
    } else if (isPair(hand)) {
      return HandRank.PAIR;
    } else {
      return HandRank.HIGHCARD;
    }
  }

  /**
   * Compares two poker hands, hand1 and hand2.
   *
   * @param hand1 the first hand
   * @param hand2 the second hand
   * @return the winning poker hand, null if tie
   */
  public PokerHand getBetterHand(PokerHand hand1, PokerHand hand2) {
    HandRank handRank1 = evaluateHand(hand1);
    HandRank handRank2 = evaluateHand(hand2);
    if (handRank1.ordinal() > handRank2.ordinal()) {
      return hand1;
    } else if (handRank1.ordinal() < handRank2.ordinal()) {
      return hand2;
    }
    return breakTie(hand1, hand2);
  }

  public Boolean isHand1Better(PokerHand hand1, PokerHand hand2) {
    HandRank handRank1 = evaluateHand(hand1);
    HandRank handRank2 = evaluateHand(hand2);
    if (handRank1.ordinal() > handRank2.ordinal()) {
      return true;
    } else if (handRank1.ordinal() < handRank2.ordinal()) {
      return false;
    }

    PokerHand betterHand = breakTie(hand1, hand2);
    if (betterHand == null) {
      return null;
    }
    return betterHand == hand1;
  }


}
