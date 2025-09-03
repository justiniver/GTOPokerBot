import bots.RandomRaiseBot
import bots.SimpleCheckCallBot
import controller.PokerController
import model.Player
import model.PokerGame
import model.PokerSession
import model.Position
import util.CardStrings

fun main() {
    val playerSB = Player(Position.SMALL_BLIND, 1000)
    val playerBB = Player(Position.BIG_BLIND, 1000)
    val pokerGame = PokerGame(true, 5, 10, playerSB, playerBB)
    val pokerController = PokerController()
    val pokerSession = PokerSession(5, 10, playerSB, playerBB)

    playerSB.strategy = RandomRaiseBot()
    playerBB.strategy = RandomRaiseBot()

    pokerSession.runNumberOfGamesAutoRebuy(10) // SEEMS LIKE ALL IN FLAG IS NOT WORKING CORRECTLY OR BOT NOT ALL-IN-ING CORRECTLY
}
