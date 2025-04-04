import model.*
import simulations.PokerFlopSim
import simulations.PokerShowdownSim
import visualization.ProbabilityChart
import util.CardStrings

fun main() {
    var sb  = Player(Position.SMALL_BLIND)
    var bb = Player(Position.BIG_BLIND)


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