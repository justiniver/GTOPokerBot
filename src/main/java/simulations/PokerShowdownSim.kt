package simulations

import model.Card
import model.PokerGame
import model.rules.HandEvaluation
import util.CardStrings

class PokerShowdownSim {
    private val cs = CardStrings()
    private var winCount = 0
    private var trialsCount = 0

    private fun simulateGameHelper(card1: Card, card2: Card) {
        val game = PokerGame(true)
        val eval = HandEvaluation()
        game.dealP1SpecificCards(card1, card2)
        game.dealFlop()
        game.dealTurn()
        game.dealRiver()
        val hand1 = game.getBestFiveCardHand(game.p1, game.board)
        val hand2 = game.getBestFiveCardHand(game.p2, game.board)

        val hand1Win = eval.isHand1Better(hand1, hand2)

        if (hand1Win == true) {
            winCount++
        }
    }

    private fun simulateGameDeuces() {
        simulateGameHelper(cs.twoClub, cs.twoDiamond)
    }

    private fun simulateGameAces() {
        simulateGameHelper(cs.aceHeart, cs.aceClub)
    }

    private fun simulateGame56Suited() {
        simulateGameHelper(cs.fiveClub, cs.sixClub)
    }

    fun runShowdownDeucesSimulation(trials: Int) {
        this.winCount = 0
        this.trialsCount = trials

        for (i in 1..trials) {
            simulateGameDeuces()
        }
    }

    fun runShowdownAcesSimulation(trials: Int) {
        this.winCount = 0
        this.trialsCount = trials

        for (i in 1..trials) {
            simulateGameAces()
        }
    }

    fun runShowdown56SuitedSimulation(trials: Int) {
        this.winCount = 0
        this.trialsCount = trials

        for (i in 1..trials) {
            simulateGame56Suited()
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