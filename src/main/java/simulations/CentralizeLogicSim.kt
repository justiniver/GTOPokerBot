package simulations
import controller.PokerController
import model.*
import simulations.*
import util.CardStrings
import org.jfree.chart.ChartFactory
import org.jfree.chart.ChartPanel
import org.jfree.chart.plot.PlotOrientation
import org.jfree.data.xy.XYSeries
import org.jfree.data.xy.XYSeriesCollection
import java.awt.Dimension
import javax.swing.JFrame

class CentralizeLogicSim {
    private val simulationResults = mutableMapOf<Int, Double>()

    ////////////////////////////////////

    private var suitedHoleCardsCount = 0;
    private var suitedHoleCardsTrials = 0

    fun simpleSimHelper(
        trials: Int,
        condition: (Card, Card) -> Boolean,
        countUpdater: () -> Unit
    ) {
        val deck = PokerDeck()
        for (i in 1..trials) {
            deck.shuffle()
            val card1 = deck.dealCard()
            val card2 = deck.dealCard()
            if (condition(card1, card2)) {
                countUpdater()
            }
            deck.reset()
        }
    }

    private fun runSuitedHoleCardsSimulation(trials: Int) {
        suitedHoleCardsCount = 0
        suitedHoleCardsTrials = trials
        simpleSimHelper(trials, { card1, card2 -> card1.suit == card2.suit }) {
            suitedHoleCardsCount++
        }
    }

    private fun runNSimulations(start: Int, end: Int, step: Int) {
        for (i in start .. end step step) {
            runSuitedHoleCardsSimulation(i)
            val prob = suitedHoleCardsCount.toDouble() / suitedHoleCardsTrials
            simulationResults[i] = prob
        }
    }


    fun displayChart(title: String = "Poker Hand Equity Simulation") {
        val dataset = XYSeriesCollection()
        val series = XYSeries("Probability")

        for ((trials, probability) in simulationResults.entries.sortedBy { it.key }) {
            series.add(trials.toDouble(), probability)
        }
        dataset.addSeries(series)

        val chart = ChartFactory.createXYLineChart(
            title,
            "Number of Simulations",
            "Probability",
            dataset,
            PlotOrientation.VERTICAL,
            true,
            true,
            false
        )

        val plot = chart.xyPlot
        val rangeAxis = plot.rangeAxis
        rangeAxis.range = org.jfree.data.Range(0.0, 1.0)

        val chartPanel = ChartPanel(chart)
        chartPanel.preferredSize = Dimension(800, 500)

        val frame = JFrame("Poker Simulation")
        frame.contentPane.add(chartPanel)
        frame.pack()
        frame.isVisible = true
        frame.defaultCloseOperation = JFrame.EXIT_ON_CLOSE
    }

    // displayChart("Suited Hole Cards Probability Convergence")
}