import org.jfree.chart.ChartFactory
import org.jfree.chart.ChartPanel
import org.jfree.chart.JFreeChart
import org.jfree.chart.plot.PlotOrientation
import org.jfree.data.category.DefaultCategoryDataset
import java.awt.Dimension
import javax.swing.JFrame
import model.HandRank
import model.PokerSession

/**
 * Creates a bar chart to display poker hand frequencies from a poker session
 */
class HandFrequencyChart {

    /**
     * Creates and displays a bar chart showing the frequencies of different poker hands
     *
     * @param pokerSession The poker session with hand frequency data
     * @param title The title for the chart
     */
    fun displayChart(pokerSession: PokerSession, title: String) {
        val dataset = createDataset(pokerSession)
        val chart = createChart(dataset, title)

        val chartPanel = ChartPanel(chart)
        chartPanel.preferredSize = Dimension(800, 500)

        val frame = JFrame("Poker Hand Frequencies")
        frame.contentPane.add(chartPanel)
        frame.pack()
        frame.isVisible = true
        frame.defaultCloseOperation = JFrame.EXIT_ON_CLOSE
    }

    /**
     * Creates the dataset from the poker session's winning hand frequencies
     */
    private fun createDataset(pokerSession: PokerSession): DefaultCategoryDataset {
        val dataset = DefaultCategoryDataset()

        val winningRankMap = pokerSession.winningRankMap as Map<HandRank, Int>

        for ((handRank, frequency) in winningRankMap) {
            dataset.addValue(frequency.toDouble(), "Frequency", handRank.toString())
        }

        return dataset
    }

    /**
     * Creates the bar chart with appropriate styling
     */
    private fun createChart(dataset: DefaultCategoryDataset, title: String): JFreeChart {

        val chart = ChartFactory.createBarChart(
            title,
            "Hand Rank",
            "Frequency",
            dataset,
            PlotOrientation.VERTICAL,
            false,
            true,
            false
        )

        return chart
    }
}