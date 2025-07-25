package model;

import java.util.Scanner;

public class ConsoleStrategy implements PlayerStrategy {
  private final Scanner in = new Scanner(System.in);

  @Override
  public Decision decide(GameView view) {
    while (true) {
      System.out.print("Enter action (FOLD, CHECK, CALL, BET, RAISE): ");
      String action = in.nextLine().trim().toUpperCase();
      try {
        Action a = Action.valueOf(action);

        if (a == Action.BET && view.currentBet() > 0) {
          System.out.println("Invalid action. Cannot BET when there's already a bet. Use RAISE instead.");
          continue;
        }
        if (a == Action.RAISE && view.currentBet() == 0) {
          System.out.println("Invalid action. Cannot RAISE when there's no bet. Use BET instead.");
          continue;
        }

        int amt = 0;
        if (a == Action.BET || a == Action.RAISE) {
          System.out.print("Enter amount (total): ");
          amt = Integer.parseInt(in.nextLine().trim());
        }
        return new Decision(a, amt);
      } catch (IllegalArgumentException e) {
        System.out.println("Invalid action (check spelling).");
      }
    }
  }
}