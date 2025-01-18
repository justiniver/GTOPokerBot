package visualization

import org.jfree.chart.ChartFactory
import org.jfree.chart.ChartPanel
import org.jfree.chart.JFreeChart
import org.jfree.data.xy.XYSeries
import org.jfree.data.xy.XYSeriesCollection
import javax.swing.JFrame

class ProbabilityChart : JFrame("Probability Convergence Chart") {

    fun createChart(trials: List<Int>, probabilities: List<Double>, title: String): JFreeChart {
        val series = XYSeries("Probability")
        for (i in trials.indices) {
            series.add(trials[i].toDouble(), probabilities[i])
        }

        val dataset = XYSeriesCollection(series)

        return ChartFactory.createXYLineChart(
            title,
            "Number of Simulations",
            "Probability",
            dataset
        )
    }

    fun displayChart(trials: List<Int>, probabilities: List<Double>, title: String) {
        val chart = createChart(trials, probabilities, title)
        val chartPanel = ChartPanel(chart)
        chartPanel.preferredSize = java.awt.Dimension(800, 600)
        contentPane = chartPanel
        pack()
        isVisible = true
        defaultCloseOperation = EXIT_ON_CLOSE
    }
}
