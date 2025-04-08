import model.*
import simulations.PokerFlopSim
import simulations.PokerShowdownSim
import visualization.ProbabilityChart
import util.CardStrings

fun main() {
    val cs = CardStrings()
    val sb  = Player(Position.SMALL_BLIND, HoleCards(cs.sixHeart, cs.sixDiamond), 1000)
    val bb = Player(Position.BIG_BLIND, HoleCards(cs.aceClub, cs.fiveSpade), 1000)
    val pokerGame = PokerGame(true, 5, 10, true)
    pokerGame.dealHoleCards()
}

/**
 *     val cs = CardStrings()
 *     val simulation = PokerShowdownSim()
 *     val trialsList = (50..10000 step 10).toList()
 *     val probabilities = mutableListOf<Double>()
 *
 *     for (trials in trialsList) {
 *         simulation.runShowdownSimulation(trials, cs.queenDiamond, cs.tenDiamond)
 *         val probability = simulation.getWinCount().toDouble() / simulation.getTrialsCount()
 *         probabilities.add(probability)
 *     }
 *
 *     val chart = ProbabilityChart()
 *     chart.displayChart(trialsList, probabilities, "Queen-Ten Suited Probability Showdown")
 */