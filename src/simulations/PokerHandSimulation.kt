package simulations

import model.*

class PokerHandSimulation {
    private var pocketPairCount = 0;
    private var suitedPairCount = 0;
    private var pocketPairTrials = 0
    private var suitedPairTrials = 0

    fun runPocketPairSimulation(trials: Int) {
        this.pocketPairCount = 0
        this.pocketPairTrials = trials
        for (i in 1..trials) {
            val deck = Deck()
            deck.shuffle()
            val card1 = deck.dealCard()
            val card2 = deck.dealCard()
            if (card1.rank == card2.rank) {
                this.pocketPairCount++
            }
        }
    }

    fun runSuitedPairSimulation(trials: Int) {
        this.suitedPairCount = 0
        this.suitedPairTrials = trials
        for (i in 1..trials) {
            val deck = Deck()
            deck.shuffle()
            val card1 = deck.dealCard()
            val card2 = deck.dealCard()
            if (card1.suit == card2.suit) {
                this.suitedPairCount++
            }
        }
    }

    fun displayResults() {
        var pocketPairProb: Float = 0.0F;
        var suitedPairProb: Float = 0.0F;

        if (pocketPairTrials > 0) {
            pocketPairProb = pocketPairCount.toFloat() / pocketPairTrials
        }

        if (suitedPairTrials > 0) {
            suitedPairProb = suitedPairCount.toFloat() / suitedPairTrials
        }

        println("Pocket Pair Probability: $pocketPairProb")
        println("Suited Pair Probability: $suitedPairProb")
    }

}