package bots;

import model.*;
import java.util.*;

/**
 * Represents the game tree for CFR algorithm in poker.
 */
public class CFRGameTree {
    
    private final Map<String, InformationSet> informationSets = new HashMap<>();
    private final Map<String, GameState> gameStates = new HashMap<>();
    
    public static class InformationSet {
        private final String id;
        private final List<Action> availableActions;
        private final double[] strategy;
        private final double[] regretSum;
        private final double[] strategySum;
        private final int player;
        
        public InformationSet(String id, List<Action> actions, int player) {
            this.id = id;
            this.availableActions = new ArrayList<>(actions);
            this.player = player;
            this.strategy = new double[actions.size()];
            this.regretSum = new double[actions.size()];
            this.strategySum = new double[actions.size()];
            
            Arrays.fill(strategy, 1.0 / actions.size());
        }
        
        public String getId() { return id; }
        public List<Action> getAvailableActions() { return availableActions; }
        public double[] getStrategy() { return strategy; }
        public double[] getRegretSum() { return regretSum; }
        public double[] getStrategySum() { return strategySum; }
        public int getPlayer() { return player; }
        
        public void updateStrategy() {
            double sum = 0;
            for (double regret : regretSum) {
                sum += Math.max(0, regret);
            }
            
            if (sum > 0) {
                for (int i = 0; i < strategy.length; i++) {
                    strategy[i] = Math.max(0, regretSum[i]) / sum;
                }
            } else {
                Arrays.fill(strategy, 1.0 / strategy.length);
            }
        }
        
        public void updateRegretSum(double[] utility) {
            for (int i = 0; i < regretSum.length; i++) {
                regretSum[i] += utility[i];
            }
        }
        
        public void updateStrategySum() {
            for (int i = 0; i < strategySum.length; i++) {
                strategySum[i] += strategy[i];
            }
        }
    }
    
    public InformationSet getInformationSet(GameView view, int player) {
        String infoSetId = createInformationSetId(view, player);
        
        if (!informationSets.containsKey(infoSetId)) {
            List<Action> actions = getAvailableActions(view);
            InformationSet infoSet = new InformationSet(infoSetId, actions, player);
            informationSets.put(infoSetId, infoSet);
        }
        
        return informationSets.get(infoSetId);
    }
    
    private String createInformationSetId(GameView view, int player) {
        StringBuilder id = new StringBuilder();
        
        id.append("P").append(player).append("_");
        
        HoleCards cards = view.myCards();
        Card card1 = cards.getCard1();
        Card card2 = cards.getCard2();

        // Treat cards same regardless of order (e.g., AKs and KAs is same)
        if (card1.getRank().ordinal() > card2.getRank().ordinal() ||
            (card1.getRank().ordinal() == card2.getRank().ordinal() && 
             card1.getSuit().ordinal() > card2.getSuit().ordinal())) {
            Card temp = card1;
            card1 = card2;
            card2 = temp;
        }
        
        id.append(card1.getRank().name()).append(card1.getSuit().name());
        id.append(card2.getRank().name()).append(card2.getSuit().name());
        
        id.append("_B");
        for (Card boardCard : view.board()) {
            id.append(boardCard.getRank().name()).append(boardCard.getSuit().name());
        }
        
        id.append("_").append(view.street().name());
        
        int potSize = view.pot();
        int bigBlind = view.bigBlindAmount();
        if (potSize <= bigBlind * 2) {
            id.append("_S");
        } else if (potSize <= bigBlind * 10) {
            id.append("_M");
        } else {
            id.append("_L");
        }
        
        if (view.toCall() == 0) {
            id.append("_C");
        } else {
            id.append("_B");
        }
        
        return id.toString();
    }
    
    private List<Action> getAvailableActions(GameView view) {
        List<Action> actions = new ArrayList<>();
        
        if (view.toCall() == 0) {
            actions.add(Action.CHECK);
            if (view.myStack() >= view.bigBlindAmount()) {
                actions.add(Action.BET);
            }
        } else {
            actions.add(Action.FOLD);
            if (view.myStack() >= view.toCall()) {
                actions.add(Action.CALL);
            }
            if (view.myStack() > view.toCall() + view.bigBlindAmount()) {
                actions.add(Action.RAISE);
            }
        }
        
        return actions;
    }
    
    public Map<String, InformationSet> getAllInformationSets() {
        return new HashMap<>(informationSets);
    }
    
    public void clear() {
        informationSets.clear();
        gameStates.clear();
    }
    
    public int size() {
        return informationSets.size();
    }
}
