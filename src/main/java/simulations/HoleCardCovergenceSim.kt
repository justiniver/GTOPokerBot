package simulations

import model.*;

class HoleCardCovergenceSim {
    private var pocketPairCount = 0;
    private var suitedHoleCardsCount = 0;
    private var pocketPairTrials = 0
    private var suitedHoleCardsTrials = 0

    private fun simpleSimHelper(
        trials: Int,
        condition: (Card, Card) -> Boolean,
        countUpdater: () -> Unit
    ) {
        val deck = PokerDeck()
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

    fun runSuitedHoleCardsSimulation(trials: Int) {
        this.suitedHoleCardsCount = 0
        this.suitedHoleCardsTrials = trials
        simpleSimHelper(trials, { card1, card2 -> card1.suit == card2.suit }) {
            suitedHoleCardsCount++
        }
    }

    fun displayResults() {
        var pocketPairProb: Float = 0.0F;
        var suitedPairProb: Float = 0.0F;

        if (pocketPairTrials > 0) {
            pocketPairProb = pocketPairCount.toFloat() / pocketPairTrials
        }

        if (suitedHoleCardsTrials > 0) {
            suitedPairProb = suitedHoleCardsCount.toFloat() / suitedHoleCardsTrials
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
        return suitedHoleCardsCount;
    }

    fun getSuitedPairTrials(): Int {
        return suitedHoleCardsTrials;
    }

}