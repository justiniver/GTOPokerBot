import bots.GTOPokerBot;
import model.Player;
import model.PokerSession;
import model.Position;

public class Main {
    public static void main(String[] args) {
        System.out.println("=== GTO Poker Bot Training & Play ===");

        System.out.println("Creating and training GTO Bot...");
        System.out.println("This may take a few moments...");

        GTOPokerBot gtoBot = new GTOPokerBot();
        gtoBot.train(100);

        System.out.println("Training completed!");
        System.out.println("Information sets learned: " + gtoBot.getInformationSetCount());

        System.out.println("\n=== Ready to Play! ===");
        System.out.println("You will play as Small Blind, GTO Bot as Big Blind");
        System.out.println("Starting stacks: 1000 chips each");
        System.out.println("Blinds: 5/10");

        Player human = new Player(Position.SMALL_BLIND, 1000);
        Player bot = new Player(Position.BIG_BLIND, 1000);

        bot.setStrategy(gtoBot);

        PokerSession session = new PokerSession(5, 10, human, bot);
        session.runGames();

        System.out.println("Thanks for playing!");
    }
}

// KEEP TRACK OF ERRORS
//