package simulations

import model.*;
import model.rules.HandEvaluation
import util.*;

class PokerFlopSimulation {
    private val cs = CardStrings()
    private var winCount = 0
    private var trialsCount = 0

    private fun simulateGameDeuces() {
        val game = PokerGame(true)
        val eval = HandEvaluation()
        game.dealP1SpecificCards(cs.twoClub, cs.twoDiamond)
        game.dealFlop()
        val hand1 = game.getBestFiveCardHand(game.p1, game.board)
        val hand2 = game.getBestFiveCardHand(game.p2, game.board)

        val hand1Win = eval.isHand1Better(hand1, hand2)

        if (hand1Win == true) {
            winCount++
        }
    }

    fun runFlopDeucesSimulation(trials: Int) {
        this.winCount = 0
        this.trialsCount = trials

        for (i in 1..trials) {
            simulateGameDeuces()
        }
    }

    fun displayResults() {
        val probability = if (trialsCount > 0) {
            winCount.toDouble() / trialsCount
        } else {
            0.0
        }

        println("Pocket Deuces Win Probability on the Flop: $probability")
    }

    fun getWinCount(): Int {
        return winCount
    }

    fun getTrialsCount(): Int {
        return trialsCount
    }
}