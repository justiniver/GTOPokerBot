import bots.GTOPokerBot;
import bots.RandomRaiseBot;
import bots.RuleBasedBot;
import bots.SimpleCheckCallBot;
import bots.CFRTrainingManager;
import model.Player;
import model.PokerSession;
import model.Position;
import model.PlayerStrategy;

public class Main {
    public static void main(String[] args) {
        System.out.println("=== GTO Poker Bot Demonstration ===");
        System.out.println("This demo showcases the CFR-based GTO Poker Bot against various opponents.");
        System.out.println("The bot will be trained using Counterfactual Regret Minimization (CFR).");

        // Demo 1: GTO Bot vs Random Bot
        System.out.println("\n1. GTO Bot vs Random Bot");
        System.out.println("==============================");
        runBotMatch("GTO Bot", "Random Bot", createGTOBot(), new RandomRaiseBot(), 2);

        // Demo 2: GTO Bot vs Rule-Based Bot
        System.out.println("\n2. GTO Bot vs Rule-Based Bot");
        System.out.println("==============================");
        runBotMatch("GTO Bot", "Rule-Based Bot", createGTOBot(), new RuleBasedBot(0.5, 0.5), 2);

        // Demo 3: GTO Bot vs Simple Check-Call Bot
        System.out.println("\n3. GTO Bot vs Simple Check-Call Bot");
        System.out.println("==============================");
        runBotMatch("GTO Bot", "Check-Call Bot", createGTOBot(), new SimpleCheckCallBot(), 2);

        // Demo 4: Advanced Training Demo
        System.out.println("\n4. Advanced Training Demo");
        System.out.println("==============================");
        demonstrateAdvancedTraining();

        System.out.println("\n=== Demo Complete ===");
        System.out.println("The GTO Bot uses Counterfactual Regret Minimization (CFR) to learn optimal strategies.");
        System.out.println("It can be trained through self-play and adapts its strategy over time.");
    }

    private static GTOPokerBot createGTOBot() {
        System.out.println("Creating GTO Bot...");
        GTOPokerBot gtoBot = new GTOPokerBot();
        
        System.out.println("Training GTO Bot...");
        System.out.println("This may take a few moments...");
        gtoBot.train(5000); // Train for 5,000 iterations
        
        System.out.println("GTO Bot training completed!");
        System.out.println("Information sets learned: " + gtoBot.getInformationSetCount());
        return gtoBot;
    }

    private static void runBotMatch(String bot1Name, String bot2Name, GTOPokerBot bot1, PlayerStrategy bot2, int numGames) {
        System.out.println(bot1Name + " vs " + bot2Name + " - " + numGames + " games");
        System.out.println("Starting stack: 1000 chips each");
        System.out.println("Blinds: 5/10");

        Player player1 = new Player(Position.SMALL_BLIND, 1000);
        Player player2 = new Player(Position.BIG_BLIND, 1000);
        PokerSession session = new PokerSession(5, 10, player1, player2);

        player1.setStrategy(bot1);
        player2.setStrategy(bot2);

        session.runNumberOfGamesAutoRebuy(numGames);

        int profit1 = player1.getBuyOut() - player1.getBuyIn();
        int profit2 = player2.getBuyOut() - player2.getBuyIn();

        System.out.println("\nFinal Results:");
        System.out.println(bot1Name + " final stack: " + player1.getStack() + " (profit: " + profit1 + ")");
        System.out.println(bot2Name + " final stack: " + player2.getStack() + " (profit: " + profit2 + ")");

        if (profit1 > profit2) {
            System.out.println(bot1Name + " wins!");
        } else if (profit2 > profit1) {
            System.out.println(bot2Name + " wins!");
        } else {
            System.out.println("It's a tie!");
        }
    }

    private static void demonstrateAdvancedTraining() {
        System.out.println("Demonstrating advanced training with CFRTrainingManager...");
        System.out.println("This will train two bots against each other through self-play.");
        
        CFRTrainingManager manager = new CFRTrainingManager();
        
        System.out.println("Starting advanced training session...");
        System.out.println("Training 2 bots for 2000 iterations each...");
        
        manager.runTrainingSession(2000);
        
        System.out.println("Advanced training completed!");
        System.out.println("Bot 1 information sets: " + manager.getBot1().getInformationSetCount());
        System.out.println("Bot 2 information sets: " + manager.getBot2().getInformationSetCount());
        
        // Test the trained bots against each other
        System.out.println("\nTesting trained bots against each other...");
        runBotMatch("Trained Bot 1", "Trained Bot 2", manager.getBot1(), manager.getBot2(), 1);
    }
}
