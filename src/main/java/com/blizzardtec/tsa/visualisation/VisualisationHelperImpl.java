 /*************************************************************************
 *  2013 BHGAGILE
 *  All Rights Reserved.
 *
 * NOTICE:  All information contained herein is, and remains
 * the property of BHGAGILE.
 */
package com.blizzardtec.tsa.visualisation;

import java.io.IOException;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;

import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.labels.StandardXYToolTipGenerator;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.StandardXYItemRenderer;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.springframework.beans.factory.annotation.Autowired;

import com.blizzardtec.tsa.dao.ResultsetDao;
import com.blizzardtec.tsa.model.BalanceSnapshot;
import com.blizzardtec.tsa.model.Resultset;

/**
 * @author Barnaby Golden
 *
 */
public final class VisualisationHelperImpl implements VisualisationHelper {

    /**
     * Convert pounds in to pence.
     */
    private static final int POUNDS_CONV = 100;
    /**
     * Result set DAO.
     */
    @Autowired
    private transient ResultsetDao resultsetDao;

    /**
     * {@inheritDoc}
     */
    public JFreeChart showChart(
            final ChartType type,
            final String runName) throws IOException {

        final Resultset resultset = resultsetDao.findByName(runName);

        JFreeChart chart = null;

        switch (type) {
        case CASH_BALANCE_XY:
            chart = balanceXY("Cash Balance", resultset);
            break;
        case SHARE_HOLDING_XY:
            chart = shareHoldingXY("Share Holding", resultset);
            break;
        case NORMALISED_XY:
            chart = normalisedXY("Performance Against Nominal", resultset);
            break;
        default:
            break;
        }

        return chart;
    }

    /**
     * Generate a normalised XY plot.
     * @param title the title
     * @param resultset result set
     * @return chart
     * @throws IOException thrown
     */
    private JFreeChart normalisedXY(
            final String title,
            final Resultset resultset) throws IOException {

        final XYSeries dataSeries = new XYSeries("normalisedXY");

        float currentWorth = 0f;
        float nominalWorth = 0f;

        for (final BalanceSnapshot balance
                : resultset.getBalanceHistory().getSnapshots()) {

            currentWorth = balance.getCurrentWorth();

            nominalWorth =
                resultset.getBalanceHistory().getNominalWorth(
                                            balance.getSellPrice());

            dataSeries.add(
                      balance.getDate().getTime(),
                      (currentWorth - nominalWorth) / POUNDS_CONV);
        }

        return buildXYTimeChart(dataSeries, title, "Normalised value (£)");
    }

    /**
     * Generate a cash balance XY plot.
     * @param title the title
     * @param resultset result set
     * @return chart
     * @throws IOException thrown
     */
    private JFreeChart balanceXY(
            final String title,
            final Resultset resultset) throws IOException {

        final XYSeries dataSeries = new XYSeries("balanceXY");

        float actualWorth = 0f;

        for (final BalanceSnapshot balance
                : resultset.getBalanceHistory().getSnapshots()) {

            actualWorth =
                        balance.getBalance()
                            + balance.getShares() * balance.getSellPrice();

            dataSeries.add(
                    balance.getDate().getTime(),
                    actualWorth / POUNDS_CONV);
        }

        return buildXYTimeChart(dataSeries, title, "Cash balance (£)");
    }

    /**
     * Generate a share holding XY plot.
     * @param title the title
     * @param resultset result set
     * @return chart
     * @throws IOException thrown
     */
    private JFreeChart shareHoldingXY(
            final String title,
            final Resultset resultset) throws IOException {

        final XYSeries dataSeries = new XYSeries("shareHoldingXY");

        for (final BalanceSnapshot balance
                : resultset.getBalanceHistory().getSnapshots()) {

            dataSeries.add(balance.getDate().getTime(), balance.getShares());
        }

        return buildXYTimeChart(dataSeries, title, "Share holding");
    }

    /**
     * Build an XY chart.
     * @param dataSeries the data series to use
     * @param title the chart title
     * @param yLabel y-axis label
     * @return chart
     */
    private JFreeChart buildXYTimeChart(
            final XYSeries dataSeries,
            final String title,
            final String yLabel) {

        final DateAxis dateAxis = new DateAxis("date");

        final DateFormat chartFormatter =
                new SimpleDateFormat("yyyy/MM/dd HH:mm", Locale.UK);

        dateAxis.setDateFormatOverride(chartFormatter);

        dateAxis.setAutoTickUnitSelection(true);
        //dateAxis.setTickUnit(scaleXAxis(dataSeries));

        final NumberAxis valueAxis = new NumberAxis(yLabel);

        final XYSeriesCollection xyDataset = new XYSeriesCollection(dataSeries);

        final StandardXYToolTipGenerator ttg = new StandardXYToolTipGenerator(
                "{0}: {2}", chartFormatter, NumberFormat.getInstance());

        final StandardXYItemRenderer renderer = new StandardXYItemRenderer(
                StandardXYItemRenderer.LINES, ttg, null);

        final XYPlot plot =
                new XYPlot(xyDataset, dateAxis, valueAxis, renderer);

        final JFreeChart chart = new JFreeChart(
                title, JFreeChart.DEFAULT_TITLE_FONT, plot, false);

        chart.setBackgroundPaint(java.awt.Color.WHITE);

        return chart;
    }
}
