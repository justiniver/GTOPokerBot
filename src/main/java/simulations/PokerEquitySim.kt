package simulations

import model.Card
import model.PokerGame
import model.rules.HandEvaluation
import util.CardStrings
import org.jfree.chart.ChartFactory
import org.jfree.chart.ChartPanel
import org.jfree.chart.plot.PlotOrientation
import org.jfree.data.xy.XYSeries
import org.jfree.data.xy.XYSeriesCollection
import java.awt.Dimension
import javax.swing.JFrame

/**
 * Simulates hand equities using a Monte Carlo simulation method (assigns a random hand to opponent).
 * Includes built-in visualization capabilities.
 */
class PokerEquitySim {
    private val cs = CardStrings()
    private var winCount = 0
    private var trialsCount = 0

    private val simulationResults = mutableMapOf<Int, Double>()

    /**
     * Simulates a single poker game and checks if the specified hand wins
     */
    private fun simulateGameHelper(card1: Card, card2: Card) {
        val game = PokerGame(true)
        val eval = HandEvaluation()
        game.dealP1SpecificCards(card1, card2)
        game.dealFlop()
        game.dealTurn()
        game.dealRiver()
        val hand1 = game.getBestFiveCardHand(game.playerSB, game.board)
        val hand2 = game.getBestFiveCardHand(game.playerBB, game.board)

        val hand1Win = eval.isHand1Better(hand1, hand2)

        if (hand1Win == true) {
            winCount++
        }
    }

    /**
     * Runs a single simulation with the specified number of trials
     */
    private fun runShowdownSimulation(trials: Int, card1: Card, card2: Card) {
        this.winCount = 0
        this.trialsCount = trials

        for (i in 1..trials) {
            simulateGameHelper(card1, card2)
        }

        val probability = getEquityProbability()
        simulationResults[trials] = probability
    }

    /**
     * Runs a progressive simulation with increasing trial counts and generates a chart
     */
    fun runProgressiveSimulation(
        startTrials: Int = 50,
        endTrials: Int = 10000,
        step: Int = 10,
        card1: Card,
        card2: Card,
        chartTitle: String = "${card1.rank}${card1.suit}-${card2.rank}${card2.suit} Equity"
    ) {
        simulationResults.clear()

        val trialsList = (startTrials..endTrials step step).toList()
        for (trials in trialsList) {
            runShowdownSimulation(trials, card1, card2)
        }

        displayChart(chartTitle)
    }

    /**
     * Gets the current equity probability
     */
    private fun getEquityProbability(): Double {
        return if (trialsCount > 0) {
            winCount.toDouble() / trialsCount
        } else {
            0.0
        }
    }

    /**
     * Displays the simulation results as text
     */
    fun displayResults(customMessage: String? = null) {
        val probability = getEquityProbability()
        val message = customMessage ?: "Hand Win Probability: $probability"
        println(message)
    }

    /**
     * Creates and displays a chart of the simulation results
     */
    private fun displayChart(title: String = "Poker Hand Equity Simulation") {
        val dataset = XYSeriesCollection()
        val series = XYSeries("Win Probability")

        for ((trials, probability) in simulationResults.entries.sortedBy { it.key }) {
            series.add(trials.toDouble(), probability)
        }
        dataset.addSeries(series)

        val chart = ChartFactory.createXYLineChart(
            title,
            "Number of Trials",
            "Win Probability",
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

        val frame = JFrame("Poker Equity Simulation")
        frame.contentPane.add(chartPanel)
        frame.pack()
        frame.isVisible = true
        frame.defaultCloseOperation = JFrame.EXIT_ON_CLOSE
    }

    /**
     * Gets the current win count
     */
    fun getWinCount(): Int {
        return winCount
    }

    /**
     * Gets the current trials count
     */
    fun getTrialsCount(): Int {
        return trialsCount
    }

    /**
     * Gets all simulation results
     */
    fun getSimulationResults(): Map<Int, Double> {
        return simulationResults.toMap()
    }
}