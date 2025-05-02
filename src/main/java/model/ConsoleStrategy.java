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
        int amt = 0;
        if (a == Action.BET || a == Action.RAISE) {
          System.out.print("Enter amount: ");
          amt = Integer.parseInt(in.nextLine().trim());
        }
        return new Decision(a, amt);
      } catch (Exception ex) {
        System.out.println("Invalid input – try again.");
      }
    }
  }
}
