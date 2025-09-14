package bots;

import model.*;
import java.util.*;

/**
 * GTO Poker Bot using Counterfactual Regret Minimization (CFR).
 */
public class GTOPokerBot implements PlayerStrategy {
    
    private final CFRAlgorithm cfrAlgorithm;
    private final CFRGameTree gameTree;
    private boolean isTraining = false;
    private Random random = new Random();
    private final Map<String, double[]> strategyCache = new HashMap<>();
    
    public GTOPokerBot() {
        this.cfrAlgorithm = new CFRAlgorithm();
        this.gameTree = cfrAlgorithm.getGameTree();
    }
    
    public GTOPokerBot(boolean trainingMode) {
        this.cfrAlgorithm = new CFRAlgorithm();
        this.gameTree = cfrAlgorithm.getGameTree();
        this.isTraining = trainingMode;
        
        if (trainingMode) {
            train();
        }
    }
    
    @Override
    public Decision decide(GameView view) {
        CFRGameTree.InformationSet infoSet = gameTree.getInformationSet(view, 0);
        double[] strategy = infoSet.getStrategy();
        Action action = selectAction(strategy, infoSet.getAvailableActions());
        int amount = calculateBetAmount(action, view, strategy);
        
        return new Decision(action, amount);
    }
    
    private Action selectAction(double[] strategy, List<Action> availableActions) {
        double randomValue = random.nextDouble();
        double cumulativeProbability = 0;
        
        for (int i = 0; i < strategy.length; i++) {
            cumulativeProbability += strategy[i];
            if (randomValue <= cumulativeProbability) {
                return availableActions.get(i);
            }
        }
        
        return availableActions.get(availableActions.size() - 1);
    }
    
    private int calculateBetAmount(Action action, GameView view, double[] strategy) {
        switch (action) {
            case CALL:
                return Math.min(view.toCall(), view.myStack());
            case BET:
                int potSize = view.pot();
                int betSize = Math.max(view.bigBlindAmount(), (int) (potSize * 0.5));
                return Math.min(betSize, view.myStack());
            case RAISE:
                int minRaise = view.currentBet() + view.bigBlindAmount();
                int raiseSize = Math.max(minRaise, (int) (view.pot() * 0.75));
                return Math.min(raiseSize, view.myStack());
            default:
                return 0;
        }
    }
    
    public void train() {
        System.out.println("Starting GTO Bot training with CFR...");
        cfrAlgorithm.train(10000);
        System.out.println("GTO Bot training completed!");
    }
    
    public void train(int iterations) {
        System.out.println("Starting GTO Bot training with " + iterations + " iterations...");
        cfrAlgorithm.train(iterations);
        System.out.println("GTO Bot training completed!");
    }
    
    public Map<String, double[]> getStrategyMap() {
        Map<String, double[]> strategies = new HashMap<>();
        for (Map.Entry<String, CFRGameTree.InformationSet> entry : gameTree.getAllInformationSets().entrySet()) {
            strategies.put(entry.getKey(), entry.getValue().getStrategy().clone());
        }
        return strategies;
    }
    
    public CFRGameTree getGameTree() {
        return gameTree;
    }
    
    public void reset() {
        gameTree.clear();
        strategyCache.clear();
    }
    
    public void setTrainingMode(boolean training) {
        this.isTraining = training;
    }
    
    public int getInformationSetCount() {
        return gameTree.size();
    }
    
    public int getCurrentIteration() {
        return cfrAlgorithm.getIteration();
    }
}
