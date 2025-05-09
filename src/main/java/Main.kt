import simulations.PokerEquitySim
import util.CardStrings


fun main() {
    val cardStrings = CardStrings()
    val simulator = PokerEquitySim()

    simulator.runProgressiveSimulation(
        startTrials = 10,
        endTrials = 1000,
        step = 10,
        card1 = cardStrings.queenDiamond,
        card2 = cardStrings.tenDiamond,
        chartTitle = "Queen-Ten Suited Equity"
    )
}