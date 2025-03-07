package model;

import java.util.*;

/**
 * Represents a board in poker.
 */
public interface Board {

  /**
   * Returns the community cards on the board.
   *
   * @return the community cards on the board
   */
  List<Card> getCommunityCards();

  /**
   * Adds a singular card to the board.
   *
   * @param card the card to add
   */
  void addCard(Card card);

  /**
   * Adds a list of cards to the board.
   *
   * @param cards the cards to add
   */
  void addCards(List<Card> cards);

  /**
   * Clears the board.
   */
  void resetBoard();

}
