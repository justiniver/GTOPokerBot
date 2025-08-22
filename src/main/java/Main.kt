import bots.RandomRaiseBot
import bots.SimpleCheckCallBot
import controller.PokerController
import model.Player
import model.PokerGame
import model.Position
import util.CardStrings

fun main() {
    val playerSB = Player(Position.SMALL_BLIND, 1000)
    val playerBB = Player(Position.BIG_BLIND, 1000)
    val pokerGame = PokerGame(true, 5, 10, playerSB, playerBB)
    val pokerController = PokerController()

    playerSB.strategy = RandomRaiseBot()
    playerBB.strategy = RandomRaiseBot()

    for (i in 1 .. 10) {
        pokerController.playHand(pokerGame)
    }
}
