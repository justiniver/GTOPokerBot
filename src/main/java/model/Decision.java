package model;

/** One complete decision made by a player for this turn. */
public class Decision {

  public final Action action;   // FOLD / CHECK / CALL / BET / RAISE
  public final int amount;      // Limited to natural numbers (including 0) and only meaningful for BET or RAISE

  public Decision(Action action, int amount) {
    this.action = action;
    this.amount = amount;
  }

  /* Convenience builders */
  public static Decision fold()  { return new Decision(Action.FOLD , 0); }
  public static Decision check() { return new Decision(Action.CHECK, 0); }
  public static Decision call()  { return new Decision(Action.CALL , 0); }
}
