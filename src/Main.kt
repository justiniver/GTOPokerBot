import simulations.PokerHandSimulation

fun main() {
    val simulation = PokerHandSimulation()

    simulation.runPocketPairSimulation(1000000)
    simulation.runSuitedPairSimulation(1000000)

    simulation.displayResults()
}
