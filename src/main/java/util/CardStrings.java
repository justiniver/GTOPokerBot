package util;

import model.Card;
import model.Rank;
import model.Suit;

/**
 * Represents all 52 playing cards as strings.
 */
public class CardStrings {
  public final Card twoClub, twoDiamond, twoHeart, twoSpade;
  public final Card threeClub, threeDiamond, threeHeart, threeSpade;
  public final Card fourClub, fourDiamond, fourHeart, fourSpade;
  public final Card fiveClub, fiveDiamond, fiveHeart, fiveSpade;
  public final Card sixClub, sixDiamond, sixHeart, sixSpade;
  public final Card sevenClub, sevenDiamond, sevenHeart, sevenSpade;
  public final Card eightClub, eightDiamond, eightHeart, eightSpade;
  public final Card nineClub, nineDiamond, nineHeart, nineSpade;
  public final Card tenClub, tenDiamond, tenHeart, tenSpade;
  public final Card jackClub, jackDiamond, jackHeart, jackSpade;
  public final Card queenClub, queenDiamond, queenHeart, queenSpade;
  public final Card kingClub, kingDiamond, kingHeart, kingSpade;
  public final Card aceClub, aceDiamond, aceHeart, aceSpade;

  public CardStrings() {
    twoClub = new Card(Rank.TWO, Suit.CLUB);
    twoDiamond = new Card(Rank.TWO, Suit.DIAMOND);
    twoHeart = new Card(Rank.TWO, Suit.HEART);
    twoSpade = new Card(Rank.TWO, Suit.SPADE);

    threeClub = new Card(Rank.THREE, Suit.CLUB);
    threeDiamond = new Card(Rank.THREE, Suit.DIAMOND);
    threeHeart = new Card(Rank.THREE, Suit.HEART);
    threeSpade = new Card(Rank.THREE, Suit.SPADE);

    fourClub = new Card(Rank.FOUR, Suit.CLUB);
    fourDiamond = new Card(Rank.FOUR, Suit.DIAMOND);
    fourHeart = new Card(Rank.FOUR, Suit.HEART);
    fourSpade = new Card(Rank.FOUR, Suit.SPADE);

    fiveClub = new Card(Rank.FIVE, Suit.CLUB);
    fiveDiamond = new Card(Rank.FIVE, Suit.DIAMOND);
    fiveHeart = new Card(Rank.FIVE, Suit.HEART);
    fiveSpade = new Card(Rank.FIVE, Suit.SPADE);

    sixClub = new Card(Rank.SIX, Suit.CLUB);
    sixDiamond = new Card(Rank.SIX, Suit.DIAMOND);
    sixHeart = new Card(Rank.SIX, Suit.HEART);
    sixSpade = new Card(Rank.SIX, Suit.SPADE);

    sevenClub = new Card(Rank.SEVEN, Suit.CLUB);
    sevenDiamond = new Card(Rank.SEVEN, Suit.DIAMOND);
    sevenHeart = new Card(Rank.SEVEN, Suit.HEART);
    sevenSpade = new Card(Rank.SEVEN, Suit.SPADE);

    eightClub = new Card(Rank.EIGHT, Suit.CLUB);
    eightDiamond = new Card(Rank.EIGHT, Suit.DIAMOND);
    eightHeart = new Card(Rank.EIGHT, Suit.HEART);
    eightSpade = new Card(Rank.EIGHT, Suit.SPADE);

    nineClub = new Card(Rank.NINE, Suit.CLUB);
    nineDiamond = new Card(Rank.NINE, Suit.DIAMOND);
    nineHeart = new Card(Rank.NINE, Suit.HEART);
    nineSpade = new Card(Rank.NINE, Suit.SPADE);

    tenClub = new Card(Rank.TEN, Suit.CLUB);
    tenDiamond = new Card(Rank.TEN, Suit.DIAMOND);
    tenHeart = new Card(Rank.TEN, Suit.HEART);
    tenSpade = new Card(Rank.TEN, Suit.SPADE);

    jackClub = new Card(Rank.JACK, Suit.CLUB);
    jackDiamond = new Card(Rank.JACK, Suit.DIAMOND);
    jackHeart = new Card(Rank.JACK, Suit.HEART);
    jackSpade = new Card(Rank.JACK, Suit.SPADE);

    queenClub = new Card(Rank.QUEEN, Suit.CLUB);
    queenDiamond = new Card(Rank.QUEEN, Suit.DIAMOND);
    queenHeart = new Card(Rank.QUEEN, Suit.HEART);
    queenSpade = new Card(Rank.QUEEN, Suit.SPADE);

    kingClub = new Card(Rank.KING, Suit.CLUB);
    kingDiamond = new Card(Rank.KING, Suit.DIAMOND);
    kingHeart = new Card(Rank.KING, Suit.HEART);
    kingSpade = new Card(Rank.KING, Suit.SPADE);

    aceClub = new Card(Rank.ACE, Suit.CLUB);
    aceDiamond = new Card(Rank.ACE, Suit.DIAMOND);
    aceHeart = new Card(Rank.ACE, Suit.HEART);
    aceSpade = new Card(Rank.ACE, Suit.SPADE);
  }
}
