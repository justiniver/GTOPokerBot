import bots.RuleBasedBot
import bots.SimpleCheckCallBot
import model.Player
import model.Position
import model.PokerSession

fun main() {

    // ^ results in 'Invalid action. Not enough chips to call' -- FIX
    val playerSB = Player(Position.SMALL_BLIND, 1000)
    val playerBB = Player(Position.BIG_BLIND, 1000)

    playerSB.strategy = SimpleCheckCallBot()
    playerBB.strategy = SimpleCheckCallBot()

    val session = PokerSession(5, 10, playerSB, playerBB)

    session.runNumberOfGamesAutoRebuy(1000)
}