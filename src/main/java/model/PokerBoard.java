package model;

import java.util.*;

public class PokerBoard implements Board {
  private final List<Card> communityCards;

  public PokerBoard() {
    this.communityCards = new ArrayList<>();
  }

  @Override
  public List<Card> getCommunityCards() {
    return this.communityCards;
  }

  @Override
  public void addCards(List<Card> cards) {
    if (this.communityCards.size() + cards.size() > 5) {
      throw new IllegalArgumentException("Board card total cannot exceed 5");
    }
    this.communityCards.addAll(cards);
  }

  @Override
  public void addCard(Card card) {
    if (this.communityCards.size() + 1 > 5) {
      throw new IllegalArgumentException("Board card total cannot exceed 5");
    }
    this.communityCards.add(card);
  }

  @Override
  public void resetBoard() {
    this.communityCards.clear();
  }
}
