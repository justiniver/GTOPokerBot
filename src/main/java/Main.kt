import bots.*
import controller.*
import model.*


fun main() {
    val sb = Player(Position.SMALL_BLIND, 1000)
    val bb = Player(Position.BIG_BLIND, 1000)

    sb.strategy = SimpleCheckCallBot()
    bb.strategy = SimpleCheckCallBot()

    val pokerSession = PokerSession(5, 10, sb, bb)
    pokerSession.runNumberOfGamesAutoRebuy(1000)
    pokerSession.printRankAnalytics()

    val chart = HandFrequencyChart()
    chart.displayChart(pokerSession, "Poker HandRank Frequencies")
}

//val sb  = Player(Position.SMALL_BLIND, 1000)
//val bb = Player(Position.BIG_BLIND, 1000)
//
//sb.strategy = SimpleCheckCallBot()
//bb.strategy = SimpleCheckCallBot()
//
//val pokerSession = PokerSession(5, 10, sb, bb)
//pokerSession.runNumberOfGamesAutoRebuy(10)
//pokerSession.printRankAnalytics()