package model;

import java.util.List;

public record GameView(
        GameState street,
        int pot,
        int toCall,
        int currentBet,
        int minRaise,
        int myStack,
        List<Card> board,
        HoleCards myCards
) {}
