package bots;

import model.*;
import java.util.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Implements the Counterfactual Regret Minimization (CFR) algorithm for poker.
 */
public class CFRAlgorithm {
    
    private final CFRGameTree gameTree;
    private final Random random;
    private int iteration = 0;
    
    private static final double EXPLORATION_PROBABILITY = 0.6; // test other consts
    private static final double EXPLORATION_DECAY = 0.999;
    
    public CFRAlgorithm() {
        this.gameTree = new CFRGameTree();
        this.random = new Random();
    }
    
    public void train(int iterations) {
        System.out.println("Starting CFR training with " + iterations + " iterations...");
        
        for (int i = 0; i < iterations; i++) {
            this.iteration = i;
            runCFRIteration();
            
            if (i % 1000 == 0) {
                System.out.println("CFR iteration: " + i + "/" + iterations);
                System.out.println("Information sets: " + gameTree.size());
            }
        }
        
        calculateAverageStrategies();
        System.out.println("CFR training completed!");
    }
    
    private void runCFRIteration() {
        List<GameView> gameStates = generateRandomGameStates();
        
        for (GameView gameState : gameStates) {
            for (int player = 0; player < 2; player++) {
                processInformationSet(gameState, player);
            }
        }
    }
    
    private void processInformationSet(GameView view, int player) {
        CFRGameTree.InformationSet infoSet = gameTree.getInformationSet(view, player);
        double[] strategy = infoSet.getStrategy().clone();
        
        if (random.nextDouble() < EXPLORATION_PROBABILITY) {
            addExploration(strategy);
        }
        
        double[] utilities = calculateUtilities(view, infoSet, strategy);
        updateRegrets(infoSet, utilities, strategy);
        infoSet.updateStrategySum();
        infoSet.updateStrategy();
    }
    
    private double[] calculateUtilities(GameView view, CFRGameTree.InformationSet infoSet, double[] strategy) {
        double[] utilities = new double[strategy.length];
        
        for (int i = 0; i < strategy.length; i++) {
            utilities[i] = simulateActionOutcome(view, infoSet.getAvailableActions().get(i));
        }
        
        return utilities;
    }
    
    private double simulateActionOutcome(GameView view, Action action) {
        double randomFactor = random.nextGaussian() * 0.1;
        double handStrength = evaluateHandStrength(view);
        
        switch (action) {
            case FOLD:
                return -0.1 + randomFactor;
            case CHECK:
                return handStrength * 0.5 + randomFactor;
            case CALL:
                return handStrength * 0.7 + randomFactor;
            case BET:
                return handStrength * 0.8 + randomFactor;
            case RAISE:
                return handStrength * 0.9 + randomFactor;
            default:
                return randomFactor;
        }
    }
    
    private double evaluateHandStrength(GameView view) {
        HoleCards cards = view.myCards();
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
    
    private void updateRegrets(CFRGameTree.InformationSet infoSet, double[] utilities, double[] strategy) {
        double[] regretSum = infoSet.getRegretSum();
        
        // Calculate counterfactual utilities
        double[] counterfactualUtilities = new double[utilities.length];
        for (int i = 0; i < utilities.length; i++) {
            counterfactualUtilities[i] = utilities[i];
            for (int j = 0; j < utilities.length; j++) {
                if (i != j) {
                    counterfactualUtilities[i] -= strategy[j] * utilities[j];
                }
            }
        }
        
        for (int i = 0; i < regretSum.length; i++) {
            regretSum[i] += counterfactualUtilities[i];
        }
    }
    
    private void addExploration(double[] strategy) {
        double explorationWeight = 0.1 * Math.pow(EXPLORATION_DECAY, iteration);
        
        for (int i = 0; i < strategy.length; i++) {
            strategy[i] = (1 - explorationWeight) * strategy[i] + 
                         explorationWeight / strategy.length;
        }
    }
    
    private List<GameView> generateRandomGameStates() {
        List<GameView> gameStates = new ArrayList<>();
        
        for (int i = 0; i < 50; i++) {
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
            for (int j = 0; j < boardSize; j++) {
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
            if (random.nextDouble() < 0.6) {
                // Someone bet, we need to call
                currentBet = bigBlind + random.nextInt(bigBlind * 3);
                toCall = currentBet;
            } else {
                // Someone raised, we need to call the raise
              currentBet = bigBlind + bigBlind + random.nextInt(bigBlind * 2);
                toCall = currentBet - bigBlind;
            }
            
            GameView view = new GameView(street, pot, toCall, currentBet, bigBlind, myStack, board, holeCards, hasShoved);
            gameStates.add(view);
        }
        
        return gameStates;
    }
    
    private Card generateRandomCard() {
        Rank rank = Rank.values()[random.nextInt(Rank.values().length)];
        Suit suit = Suit.values()[random.nextInt(Suit.values().length)];
        return new Card(rank, suit);
    }
    
    private void calculateAverageStrategies() {
        for (CFRGameTree.InformationSet infoSet : gameTree.getAllInformationSets().values()) {
            double[] strategySum = infoSet.getStrategySum();
            double[] strategy = infoSet.getStrategy();
            
            double totalSum = Arrays.stream(strategySum).sum();
            
            if (totalSum > 0) {
                for (int i = 0; i < strategy.length; i++) {
                    strategy[i] = strategySum[i] / totalSum;
                }
            }
        }
    }
    
    public CFRGameTree getGameTree() {
        return gameTree;
    }
    
    public int getIteration() {
        return iteration;
    }
}
