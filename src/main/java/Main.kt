import simulations.PokerHandSimulation

fun main() {
    val simulation = PokerHandSimulation()

    simulation.runPocketPairSimulation(10000000)
    simulation.runSuitedPairSimulation(10000000)

    simulation.displayResults()
}
