import simulations.PokerFlopSimulation
import visualization.ProbabilityChart

fun main() {
    val simulation = PokerFlopSimulation()
    val trialsList = (50..10000 step 10).toList()
    val probabilities = mutableListOf<Double>()

    for (trials in trialsList) {
        simulation.run56SuitedSimulation(trials)
        val probability = simulation.getWinCount().toDouble() / simulation.getTrialsCount()
        probabilities.add(probability)
    }

    val chart = ProbabilityChart()
    chart.displayChart(trialsList, probabilities, "56 Suited Win Probability on Flop")
}
