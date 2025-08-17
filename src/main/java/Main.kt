import controller.PokerController
import model.Player
import model.PokerGame
import model.Position
import simulations.PokerEquitySim
import util.CardStrings

fun main() {
    val cardStrings = CardStrings()
    val simulator = PokerEquitySim()

    simulator.runProgressiveSimulation(
        startTrials = 50,
        endTrials = 10000,
        step = 10,
        card1 = cardStrings.queenDiamond,
        card2 = cardStrings.tenDiamond,
        chartTitle = "Queen-Ten Suited Equity"
    )
}


var cs = CardStrings()
//    var playerSB = Player(Position.SMALL_BLIND, 1000)
//    var playerBB = Player(Position.BIG_BLIND, 1000)
//    var pokerGame = PokerGame(true, 5, 10, playerSB, playerBB)
//    var pokerController = PokerController()
//
//    pokerController.playHand(pokerGame)