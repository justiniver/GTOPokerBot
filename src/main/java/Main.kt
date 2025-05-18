import bots.RuleBasedBot
import model.Player
import model.Position
import model.PokerSession

fun main() {
    val aggressiveBot = RuleBasedBot()
    val conservativeBot = RuleBasedBot()

    val playerSB = Player(Position.SMALL_BLIND, 1000)
    val playerBB = Player(Position.BIG_BLIND, 1000)
    playerSB.strategy = aggressiveBot
    playerBB.strategy = conservativeBot

    val session = PokerSession(5, 10, playerSB, playerBB)


    session.runNumberOfGamesAutoRebuy(100)

    session.printRankAnalytics()

}