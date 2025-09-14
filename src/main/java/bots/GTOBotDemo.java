package bots;

import model.*;
import java.util.*;

/**
 * Demo class showing how to use the GTO Poker Bot with CFR.
 * This class demonstrates training and using the bot in actual games.
 */
public class GTOBotDemo {
    
    public static void main(String[] args) {
        System.out.println("=== GTO Poker Bot Demo ===");
        
        // Create and train a GTO bot
        GTOPokerBot gtoBot = new GTOPokerBot(true);
        
        // Train the bot
        System.out.println("\nTraining GTO Bot...");
        gtoBot.train(5000); // Train for 5000 iterations
        
        // Create a training manager for advanced training
        CFRTrainingManager trainingManager = new CFRTrainingManager();
        trainingManager.runTrainingSession(2000);
        
        // Get the trained bot
        GTOPokerBot trainedBot = trainingManager.getBot1();
        
        // Demonstrate the bot's decision making
        demonstrateBotDecisions(trainedBot);
        
        // Show strategy analysis
        analyzeBotStrategy(trainedBot);
        
        // Compare with other bots
        compareWithOtherBots(trainedBot);
    }
    
    /**
     * Demonstrates the bot's decision making in various scenarios
     */
    private static void demonstrateBotDecisions(GTOPokerBot bot) {
        System.out.println("\n=== Bot Decision Demonstration ===");
        
        // Create various game scenarios
        List<GameView> scenarios = createTestScenarios();
        
        for (int i = 0; i < scenarios.size(); i++) {
            GameView scenario = scenarios.get(i);
            Decision decision = bot.decide(scenario);
            
            System.out.println("\nScenario " + (i + 1) + ":");
            System.out.println("Hole cards: " + scenario.myCards());
            System.out.println("Board: " + scenario.board());
            System.out.println("Street: " + scenario.street());
            System.out.println("Pot: " + scenario.pot());
            System.out.println("To call: " + scenario.toCall());
            System.out.println("Bot decision: " + decision.action() + " for " + decision.amount());
        }
    }
    
    /**
     * Creates test scenarios for bot demonstration
     */
    private static List<GameView> createTestScenarios() {
        List<GameView> scenarios = new ArrayList<>();
        
        // Scenario 1: Strong hand preflop
        HoleCards strongHand = new HoleCards(new Card(Rank.ACE, Suit.SPADE), new Card(Rank.ACE, Suit.HEART));
        GameView scenario1 = new GameView(GameState.PREFLOP, 20, 0, 0, 10, 1000, new ArrayList<>(), strongHand, false);
        scenarios.add(scenario1);
        
        // Scenario 2: Weak hand facing bet
        HoleCards weakHand = new HoleCards(new Card(Rank.SEVEN, Suit.CLUB), new Card(Rank.TWO, Suit.DIAMOND));
        List<Card> board2 = new ArrayList<>();
        board2.add(new Card(Rank.KING, Suit.SPADE));
        board2.add(new Card(Rank.QUEEN, Suit.HEART));
        board2.add(new Card(Rank.JACK, Suit.CLUB));
        GameView scenario2 = new GameView(GameState.FLOP, 100, 50, 50, 10, 1000, board2, weakHand, false);
        scenarios.add(scenario2);
        
        // Scenario 3: Drawing hand on turn
        HoleCards drawingHand = new HoleCards(new Card(Rank.NINE, Suit.SPADE), new Card(Rank.TEN, Suit.SPADE));
        List<Card> board3 = new ArrayList<>();
        board3.add(new Card(Rank.EIGHT, Suit.HEART));
        board3.add(new Card(Rank.SEVEN, Suit.CLUB));
        board3.add(new Card(Rank.SIX, Suit.DIAMOND));
        board3.add(new Card(Rank.FIVE, Suit.SPADE));
        GameView scenario3 = new GameView(GameState.TURN, 200, 0, 0, 10, 1000, board3, drawingHand, false);
        scenarios.add(scenario3);
        
        return scenarios;
    }
    
    /**
     * Analyzes the bot's learned strategy
     */
    private static void analyzeBotStrategy(GTOPokerBot bot) {
        System.out.println("\n=== Strategy Analysis ===");
        
        Map<String, double[]> strategies = bot.getStrategyMap();
        System.out.println("Total information sets learned: " + strategies.size());
        
        // Show some example strategies
        int count = 0;
        for (Map.Entry<String, double[]> entry : strategies.entrySet()) {
            if (count < 5) { // Show first 5 strategies
                System.out.println("\nInformation Set: " + entry.getKey());
                double[] strategy = entry.getValue();
                System.out.println("Strategy: " + Arrays.toString(strategy));
            }
            count++;
        }
        
        System.out.println("\nBot training iteration: " + bot.getCurrentIteration());
    }
    
    /**
     * Compares the GTO bot with other bot types
     */
    private static void compareWithOtherBots(GTOPokerBot gtoBot) {
        System.out.println("\n=== Bot Comparison ===");
        
        // Create other bot types
        RandomRaiseBot randomBot = new RandomRaiseBot();
        RuleBasedBot ruleBot = new RuleBasedBot(0.5, 0.5);
        SimpleCheckCallBot callBot = new SimpleCheckCallBot();
        
        // Test scenarios
        List<GameView> testScenarios = createTestScenarios();
        
        System.out.println("Comparing bot decisions across different scenarios:");
        System.out.println("Scenario | GTO Bot | Random Bot | Rule Bot | Call Bot");
        System.out.println("---------|---------|------------|----------|----------");
        
        for (int i = 0; i < testScenarios.size(); i++) {
            GameView scenario = testScenarios.get(i);
            
            Decision gtoDecision = gtoBot.decide(scenario);
            Decision randomDecision = randomBot.decide(scenario);
            Decision ruleDecision = ruleBot.decide(scenario);
            Decision callDecision = callBot.decide(scenario);
            
            System.out.printf("%8d | %7s | %10s | %8s | %8s%n", 
                i + 1, 
                gtoDecision.action().name(), 
                randomDecision.action().name(), 
                ruleDecision.action().name(), 
                callDecision.action().name());
        }
    }
    
    /**
     * Runs a simulation between the GTO bot and another bot
     */
    public static void runSimulation(GTOPokerBot gtoBot, PlayerStrategy opponentBot, int numGames) {
        System.out.println("\n=== Running Simulation ===");
        System.out.println("GTO Bot vs " + opponentBot.getClass().getSimpleName());
        System.out.println("Number of games: " + numGames);
        
        int gtoWins = 0;
        int opponentWins = 0;
        int ties = 0;
        
        for (int i = 0; i < numGames; i++) {
            // Create a random game scenario
            GameView scenario = createRandomScenario();
            
            Decision gtoDecision = gtoBot.decide(scenario);
            Decision opponentDecision = opponentBot.decide(scenario);
            
            // Simulate outcome (simplified)
            double outcome = simulateOutcome(scenario, gtoDecision, opponentDecision);
            
            if (outcome > 0.1) {
                gtoWins++;
            } else if (outcome < -0.1) {
                opponentWins++;
            } else {
                ties++;
            }
        }
        
        System.out.println("Results:");
        System.out.println("GTO Bot wins: " + gtoWins + " (" + (gtoWins * 100.0 / numGames) + "%)");
        System.out.println("Opponent wins: " + opponentWins + " (" + (opponentWins * 100.0 / numGames) + "%)");
        System.out.println("Ties: " + ties + " (" + (ties * 100.0 / numGames) + "%)");
    }
    
    /**
     * Creates a random game scenario for simulation
     */
    private static GameView createRandomScenario() {
        Random random = new Random();
        
        // Random hole cards
        Rank rank1 = Rank.values()[random.nextInt(Rank.values().length)];
        Suit suit1 = Suit.values()[random.nextInt(Suit.values().length)];
        Rank rank2 = Rank.values()[random.nextInt(Rank.values().length)];
        Suit suit2 = Suit.values()[random.nextInt(Suit.values().length)];
        
        HoleCards holeCards = new HoleCards(new Card(rank1, suit1), new Card(rank2, suit2));
        
        // Random board
        List<Card> board = new ArrayList<>();
        int boardSize = random.nextInt(4);
        for (int i = 0; i < boardSize; i++) {
            Rank rank = Rank.values()[random.nextInt(Rank.values().length)];
            Suit suit = Suit.values()[random.nextInt(Suit.values().length)];
            board.add(new Card(rank, suit));
        }
        
        // Random game state
        GameState street = GameState.values()[random.nextInt(GameState.values().length)];
        int pot = random.nextInt(1000) + 100;
        int toCall = random.nextInt(100);
        int currentBet = random.nextInt(200);
        int bigBlind = 10;
        int myStack = random.nextInt(2000) + 500;
        boolean hasShoved = random.nextBoolean();
        
        return new GameView(street, pot, toCall, currentBet, bigBlind, myStack, board, holeCards, hasShoved);
    }
    
    /**
     * Simulates the outcome of a game scenario
     */
    private static double simulateOutcome(GameView scenario, Decision gtoDecision, Decision opponentDecision) {
        // Simplified outcome simulation
        Random random = new Random();
        
        // Base outcome on hand strength and decision quality
        double handStrength = evaluateHandStrength(scenario.myCards());
        double gtoQuality = evaluateDecisionQuality(gtoDecision);
        double opponentQuality = evaluateDecisionQuality(opponentDecision);
        
        double baseOutcome = (gtoQuality - opponentQuality) * 0.5 + handStrength * 0.3;
        double randomFactor = random.nextGaussian() * 0.2;
        
        return baseOutcome + randomFactor;
    }
    
    /**
     * Evaluates hand strength
     */
    private static double evaluateHandStrength(HoleCards cards) {
        Card card1 = cards.getCard1();
        Card card2 = cards.getCard2();
        
        double strength = (card1.getRank().ordinal() + card2.getRank().ordinal()) / 24.0;
        
        if (card1.getRank() == card2.getRank()) {
            strength += 0.3;
        }
        
        if (card1.getSuit() == card2.getSuit()) {
            strength += 0.1;
        }
        
        return Math.min(1.0, strength);
    }
    
    /**
     * Evaluates decision quality
     */
    private static double evaluateDecisionQuality(Decision decision) {
        switch (decision.action()) {
            case FOLD: return -0.1;
            case CHECK: return 0.0;
            case CALL: return 0.2;
            case BET: return 0.4;
            case RAISE: return 0.6;
            default: return 0.0;
        }
    }
}
