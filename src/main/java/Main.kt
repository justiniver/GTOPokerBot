import controller.PokerController
import model.Player
import model.PokerGame
import model.Position
import util.CardStrings

fun main() {

    var cs = CardStrings()
    var playerSB = Player(Position.SMALL_BLIND, 1000)
    var playerBB = Player(Position.BIG_BLIND, 1000)
    var pokerGame = PokerGame(true, 5, 10, playerSB, playerBB)
    var pokerController = PokerController()

    pokerController.playHand(pokerGame)
}