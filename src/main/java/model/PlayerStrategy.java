package model;

public interface PlayerStrategy {

  /** Return a complete Decision for the current turn. */
  Decision decide(GameView view);
}
