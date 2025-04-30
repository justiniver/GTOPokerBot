import controller.*
import model.*


fun main() {
    val sb  = Player(Position.SMALL_BLIND, 1000)
    val bb = Player(Position.BIG_BLIND, 1000)
    val pokerGame = PokerGame(true, 5, 10, sb, bb)
    val pokerController = PokerController()
    pokerController.playHand(pokerGame)
}

// create "how to use" section for README and add the code below as an example on how to run
// equities

//    val cs = CardStrings()
//    val simulation = PokerShowdownSim()
//    val trialsList = (50..10000 step 10).toList()
//    val probabilities = mutableListOf<Double>()
//
//    for (trials in trialsList) {
//        simulation.runShowdownSimulation(trials, cs.queenDiamond, cs.tenDiamond)
//        val probability = simulation.getWinCount().toDouble() / simulation.getTrialsCount()
//        probabilities.add(probability)
//    }
//
//    val chart = ProbabilityChart()
//    chart.displayChart(trialsList, probabilities, "Queen-Ten Suited Probability Showdown")

