package model;
import bots.GTOPokerBot;
import model.Player;
import model.PokerSession;
import model.Position;

public class TrainedSession {
  private final int smallBlindAmount;
  private final int bigBlindAmount;
  private final Player human;
  private final Player bot;
  private final int numIterations;

  public TrainedSession(int smallBlindAmount, int bigBlindAmount, Player human, Player bot,
                        int numIterations) {
    this.smallBlindAmount = smallBlindAmount;
    this.bigBlindAmount = bigBlindAmount;
    this.human = human;
    this.bot = bot;
    this.numIterations = numIterations;
  }

  public void run() {
    System.out.println("=== GTO Poker Bot Training & Play ===");

    System.out.println("Creating and training GTO Bot...");

    GTOPokerBot gtoBot = new GTOPokerBot();
    gtoBot.train(numIterations);

    System.out.println("Training completed!");
    System.out.println("Information sets learned: " + gtoBot.getInformationSetCount());

    System.out.println("\n=== Ready to Play! ===");
    // Don't hardcode these comments, maybe take in the poker game details?
    System.out.println("You will play as Small Blind, GTO Bot as Big Blind");
    System.out.println("Starting stacks: 1000 chips each");
    System.out.println("Blinds: 5/10");

    bot.setStrategy(gtoBot);

    PokerSession session = new PokerSession(smallBlindAmount, bigBlindAmount, human, bot);
    session.runGames();

    System.out.println("Thanks for playing!");
  }
}
