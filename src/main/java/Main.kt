import bots.*;
import controller.*
import model.*


fun main() {
    val sb  = Player(Position.SMALL_BLIND, 1000)
    val bb = Player(Position.BIG_BLIND, 1000)

    sb.strategy = SimpleCheckCallBot()
    bb.strategy = SimpleCheckFoldBot()

    val pokerSession = PokerSession(5, 10, sb, bb)
    pokerSession.runGames()
}

