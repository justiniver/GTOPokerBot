package simulations

import model.*

class PokerHandSimulation {
    private var pocketPairCount = 0;
    private var suitedPairCount = 0;
    private var pocketPairTrials = 0
    private var suitedPairTrials = 0

    private fun simpleSimHelper(
        trials: Int,
        condition: (Card, Card) -> Boolean,
        countUpdater: () -> Unit
    ) {
        val deck = Deck()
        for (i in 1..trials) {
            deck.shuffle()
            val card1 = deck.dealCard()
            val card2 = deck.dealCard()
            if (condition(card1, card2)) {
                countUpdater()
            }
            deck.reset()
        }
    }


    fun runPocketPairSimulation(trials: Int) {
        this.pocketPairCount = 0
        this.pocketPairTrials = trials
        simpleSimHelper(trials, { card1, card2 -> card1.rank == card2.rank }) {
            pocketPairCount++
        }
    }

    fun runSuitedPairSimulation(trials: Int) {
        this.suitedPairCount = 0
        this.suitedPairTrials = trials
        simpleSimHelper(trials, { card1, card2 -> card1.suit == card2.suit }) {
            suitedPairCount++
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

    fun getPocketPairTrials(): Int {
        return pocketPairTrials;
    }

    fun getPocketPairCount(): Int {
        return pocketPairCount;
    }

    fun getSuitedPairCount(): Int {
        return suitedPairCount;
    }

    fun getSuitedPairTrials(): Int {
        return suitedPairTrials;
    }

}