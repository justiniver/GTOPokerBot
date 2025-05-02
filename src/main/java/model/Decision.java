package model;

/** Represents the player's decision. */
public record Decision(Action action, int amount) {
  public static Decision fold()  { return new Decision(Action.FOLD , 0); }
  public static Decision check() { return new Decision(Action.CHECK, 0); }
  public static Decision call()  { return new Decision(Action.CALL , 0); }
}
