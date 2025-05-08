package model;

import java.util.ArrayList;
import java.util.List;

import model.rules.HandEvaluation;

/**
 * Implementation of a poker game with betting logic.
 *
 */
public class PokerGame implements Game {
  private GameState state;
  private final PokerBoard board;
  private final PokerDeck deck;
  private final Player playerSB;
  private final Player playerBB;
  private int pot;
  private final int smallBlindAmount;
  private final int bigBlindAmount;
  private PokerHand bestHand;

  // may want to enforce certain constructors, having five constructors is probably a bad idea
  public PokerGame(boolean shuffle) {
    this(shuffle, 0, 0,
            new Player(Position.SMALL_BLIND), new Player(Position.BIG_BLIND));
  }

  public PokerGame(boolean shuffle, int smallBlindAmount, int bigBlindAmount,
                   Player playerSB, Player playerBB) {
    this.deck = new PokerDeck();
    this.board = new PokerBoard();
    this.playerSB = playerSB;
    this.playerBB = playerBB;
    this.state = GameState.PREFLOP;
    if (shuffle) {
      deck.shuffle();
    }
    if (state == GameState.PREFLOP) {
      this.pot = smallBlindAmount + bigBlindAmount;
    }
    this.smallBlindAmount = smallBlindAmount;
    this.bigBlindAmount = bigBlindAmount;
  }

  @Override
  public void dealHoleCards() {
    this.state = GameState.PREFLOP;
    playerSB.setHoleCards(deck.dealCards(2));
    playerBB.setHoleCards(deck.dealCards(2));
  }

  public void dealP1SpecificCards(Card card1, Card card2) {
    this.state = GameState.PREFLOP;
    List<Card> p1Cards = List.of(deck.dealSpecificCard(card1), deck.dealSpecificCard(card2));
    playerSB.setHoleCards(p1Cards);
    playerBB.setHoleCards(deck.dealCards(2));
  }

  @Override
  public void dealFlop() {
    state = GameState.FLOP;
    board.addCards(deck.dealCards(3));
  }

  @Override
  public void dealTurn() {
    state = GameState.TURN;
    board.addCard(deck.dealCard());
  }

  @Override
  public void dealRiver() {
    state = GameState.RIVER;
    board.addCard(deck.dealCard());
  }

  public void runRiverAnalytics() {
    if (state != GameState.RIVER) {
      throw new IllegalStateException("State must be RIVER. Current state is: " + state);
    }
    HandEvaluation eval = new HandEvaluation();

  }

  /**
   * Returns the best possible 5-card hand for current player.
   *
   * @param player the current player
   * @param board the current board
   * @return the best possible 5-card hand for current player.
   */
  public PokerHand getBestFiveCardHand(Player player, PokerBoard board) {
    if (state == GameState.PREFLOP) {
      throw new IllegalStateException("Cannot assemble five card hand preflop");
    }
    Card[] cards = new Card[5];
    HandEvaluation eval = new HandEvaluation();

    if (state == GameState.FLOP) {
      for (int i = 0; i < 3; i++) {
        cards[i] = board.getCommunityCards().get(i);
      }
      cards[3] = player.getHoleCards().getCard1();
      cards[4] = player.getHoleCards().getCard2();

      return new PokerHand(cards);
    } else if (state == GameState.TURN) {
      // would implement turn evaluation if needed

    } else if (state ==  GameState.RIVER) {
      List<Card> allCards = new ArrayList<>(7);

      allCards.addAll(board.getCommunityCards());
      allCards.add(player.getHoleCards().getCard1());
      allCards.add(player.getHoleCards().getCard2());

      PokerHand bestHand = new PokerHand(new Card[]{allCards.get(1), allCards.get(2),
              allCards.get(3), allCards.get(4), allCards.get(5)});

      // Implement 7 choose 5 logic and find best hand of the 21 combinations.
      for (int i = 0; i <= 6; i++) {
        Card[] cand = new Card[5]; // candidate cards

        for (int j = i + 1; j <= 6; j++) {

          int notIorJ = 0;
          for (int k = 0; k <= 6; k++) {
            if (k != i && k != j) {
              cand[notIorJ] = allCards.get(k);
              notIorJ++;
            }
          }
          bestHand = eval.getBetterHand(new PokerHand(cand), bestHand);

        }
      }

      return bestHand;
    }

    return new PokerHand();
  }

  public Player getPlayerSB() {
    return playerSB;
  }

  public Player getPlayerBB() {
    return playerBB;
  }

  public GameState getState() {
    return state;
  }

  public PokerBoard getBoard() {
    return board;
  }

  public PokerDeck getDeck() {
    return deck;
  }

  public void setPot(int potAmount) {
    this.pot = potAmount;
  }

  public int getPot() {
    return this.pot;
  }

  public int getSmallBlindAmount() {
    return this.smallBlindAmount;
  }

  public int getBigBlindAmount() {
    return this.bigBlindAmount;
  }
}
