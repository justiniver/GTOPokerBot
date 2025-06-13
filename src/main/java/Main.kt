import bots.RuleBasedBot
import bots.SimpleCheckCallBot
import model.Player
import model.Position
import model.PokerSession

fun main() {
    val aggressiveBot = RuleBasedBot(0.5, 0.5)
    val conservativeBot = RuleBasedBot(0.5, 0.5)

    // ^ results in 'Invalid action. Not enough chips to call' -- FIX

    val playerSB = Player(Position.SMALL_BLIND, 1000)
    val playerBB = Player(Position.BIG_BLIND, 1000)
    playerSB.strategy = aggressiveBot
    playerBB.strategy = conservativeBot

    val session = PokerSession(5, 10, playerSB, playerBB)

    session.runNumberOfGames(10)
}