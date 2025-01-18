import simulations.PokerHandSimulation
import visualization.ProbabilityChart

fun main() {
    val simulation = PokerHandSimulation()
    val trialsList = (100..100000 step 100).toList()
    val probabilities = mutableListOf<Double>()

    for (trials in trialsList) {
        simulation.runPocketPairSimulation(trials)
        val probability = simulation.getPocketPairCount().toDouble() / simulation.getPocketPairTrials()
        probabilities.add(probability)
    }

    val chart = ProbabilityChart()
    chart.displayChart(trialsList, probabilities, "Pocket Pair Probability Convergence")
}
