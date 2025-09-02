package model;

import java.util.List;

public record GameView(
        GameState street,
        int pot,
        int toCall,
        int currentBet,
        int bigBlindAmount,
        int myStack,
        List<Card> board,
        HoleCards myCards,
        boolean hasShoved // bool to check whether opponent has shoved
) {}
