import bots.*
import controller.*
import model.*


fun main() {
    val sb  = Player(Position.SMALL_BLIND, 1000)
    val bb = Player(Position.BIG_BLIND, 1000)

    sb.strategy = SimpleCheckCallBot()
    bb.strategy = SimpleCheckCallBot()

    val pokerSession = PokerSession(5, 10, sb, bb)
    pokerSession.runNumberOfGamesAutoRebuy(100000)
    pokerSession.printRankAnalytics()
}

