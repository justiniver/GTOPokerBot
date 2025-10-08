package bots;

import model.*;
import java.util.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Manages the training process for CFR-based poker bots.
 */
public class CFRTrainingManager {
    
    private final GTOPokerBot bot1;
    private final GTOPokerBot bot2;
    private final Random random;
    
    private int gamesPlayed = 0;
    private double totalUtility = 0.0;
    private final Map<String, Integer> actionCounts = new HashMap<>();
    
    public CFRTrainingManager() {
        this.bot1 = new GTOPokerBot(false);
        this.bot2 = new GTOPokerBot(false);
        this.random = new Random();
    }
    
    public void runTrainingSession(int iterations) {
        System.out.println("Starting CFR training session with " + iterations + " iterations...");
        
        for (int i = 0; i < iterations; i++) {
            bot1.train(1);
            bot2.train(1);
            playTrainingGame();
            
            if (i % 1000 == 0) {
                System.out.println("Training iteration: " + i + "/" + iterations);
                printTrainingStats();
            }
        }
        
        System.out.println("CFR training session completed!");
        printFinalStats();
    }
    
    private void playTrainingGame() {
        GameView gameState = createRandomGameState();
        Decision decision1 = bot1.decide(gameState);
        Decision decision2 = bot2.decide(gameState);
        double outcome = simulateGameOutcome(gameState, decision1, decision2);
        
        updateTrainingStats(decision1, decision2, outcome);
        gamesPlayed++;
    }
    
    private GameView createRandomGameState() {
        Card card1 = generateRandomCard();
        Card card2;
        do {
            card2 = generateRandomCard();
        } while (card1.equals(card2));
        
        HoleCards holeCards = new HoleCards(card1, card2);
        
        List<Card> board = new ArrayList<>();
        Set<Card> usedCards = new HashSet<>();
        usedCards.add(card1);
        usedCards.add(card2);
        
        int boardSize = random.nextInt(4);
        for (int i = 0; i < boardSize; i++) {
            Card boardCard;
            do {
                boardCard = generateRandomCard();
            } while (usedCards.contains(boardCard));
            board.add(boardCard);
            usedCards.add(boardCard);
        }
        
        GameState street = GameState.values()[random.nextInt(GameState.values().length)];
        int pot = random.nextInt(1000) + 100;
        int bigBlind = 10;
        int myStack = random.nextInt(2000) + 500;
        boolean hasShoved = random.nextBoolean();
        
        // Generate consistent betting state
        int currentBet = 0;
        int toCall = 0;
        
        // Simulate realistic betting scenarios
        if (random.nextDouble() < 0.3) {
            // No betting yet (preflop limped or checked)
            currentBet = 0;
            toCall = 0;
        } else if (random.nextDouble() < 0.6) {
            // Someone bet, we need to call
            currentBet = bigBlind + random.nextInt(bigBlind * 3);
            toCall = currentBet;
        } else {
            // Someone raised, we need to call the raise
            int previousBet = bigBlind;
            currentBet = previousBet + bigBlind + random.nextInt(bigBlind * 2);
            toCall = currentBet - previousBet;
        }
        
        return new GameView(street, pot, toCall, currentBet, bigBlind, myStack, board, holeCards, hasShoved);
    }
    
    private Card generateRandomCard() {
        Rank rank = Rank.values()[random.nextInt(Rank.values().length)];
        Suit suit = Suit.values()[random.nextInt(Suit.values().length)];
        return new Card(rank, suit);
    }
    
    private double simulateGameOutcome(GameView gameState, Decision decision1, Decision decision2) {
        double baseOutcome = 0.0;
        double handStrength = evaluateHandStrength(gameState.myCards());
        double actionQuality1 = evaluateActionQuality(decision1, gameState);
        double actionQuality2 = evaluateActionQuality(decision2, gameState);
        double relativeQuality = actionQuality1 - actionQuality2;
        double randomFactor = random.nextGaussian() * 0.1;
        
        return baseOutcome + handStrength * 0.3 + relativeQuality * 0.7 + randomFactor;
    }
    
    private double evaluateHandStrength(HoleCards cards) {
        Card card1 = cards.getCard1();
        Card card2 = cards.getCard2();
        
        double strength = 0.0;
        strength += (card1.getRank().ordinal() + card2.getRank().ordinal()) / 24.0;
        
        if (card1.getRank() == card2.getRank()) {
            strength += 0.3;
        }
        
        if (card1.getSuit() == card2.getSuit()) {
            strength += 0.1;
        }
        
        int gap = Math.abs(card1.getRank().ordinal() - card2.getRank().ordinal());
        if (gap <= 2) {
            strength += 0.1 - (gap * 0.03);
        }
        
        return Math.min(1.0, strength);
    }
    
    private double evaluateActionQuality(Decision decision, GameView gameState) { // don't think this needs to take in gameState
        switch (decision.action()) {
            case FOLD:
                return -0.1;
            case CHECK:
                return 0.0;
            case CALL:
                return 0.2;
            case BET:
                return 0.4;
            case RAISE:
                return 0.6;
            default:
                return 0.0;
        }
    }
    
    private void updateTrainingStats(Decision decision1, Decision decision2, double outcome) {
        totalUtility += outcome;
        
        String action1 = decision1.action().name();
        String action2 = decision2.action().name();
        
        actionCounts.put(action1, actionCounts.getOrDefault(action1, 0) + 1);
        actionCounts.put(action2, actionCounts.getOrDefault(action2, 0) + 1);
    }
    
    private void printTrainingStats() {
        System.out.println("Games played: " + gamesPlayed);
        System.out.println("Average utility: " + (totalUtility / Math.max(1, gamesPlayed)));
        System.out.println("Bot 1 information sets: " + bot1.getInformationSetCount());
        System.out.println("Bot 2 information sets: " + bot2.getInformationSetCount());
    }
    
    private void printFinalStats() {
        System.out.println("\n=== Final Training Statistics ===");
        System.out.println("Total games played: " + gamesPlayed);
        System.out.println("Average utility: " + (totalUtility / Math.max(1, gamesPlayed)));
        System.out.println("Bot 1 information sets: " + bot1.getInformationSetCount());
        System.out.println("Bot 2 information sets: " + bot2.getInformationSetCount());
        
        System.out.println("\nAction distribution:");
        for (Map.Entry<String, Integer> entry : actionCounts.entrySet()) {
            double percentage = (double) entry.getValue() / (gamesPlayed * 2) * 100;
            System.out.println(entry.getKey() + ": " + entry.getValue() + " (" + String.format("%.1f", percentage) + "%)");
        }
    }
    
    public GTOPokerBot getBot1() {
        return bot1;
    }
    
    public GTOPokerBot getBot2() {
        return bot2;
    }
    
    public void reset() {
        bot1.reset();
        bot2.reset();
        gamesPlayed = 0;
        totalUtility = 0.0;
        actionCounts.clear();
    }
}
