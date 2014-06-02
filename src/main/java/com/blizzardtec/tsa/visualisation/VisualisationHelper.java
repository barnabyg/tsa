 /*************************************************************************
 *  2013 BHGAGILE
 *  All Rights Reserved.
 *
 * NOTICE:  All information contained herein is, and remains
 * the property of BHGAGILE.
 */
package com.blizzardtec.tsa.visualisation;

import java.io.IOException;

import org.jfree.chart.JFreeChart;

/**
 * @author Barnaby Golden
 *
 */
public interface VisualisationHelper {

    /**
     * Chart type for visualisation.
     * @author Barnaby Golden
     *
     */
    public enum ChartType {
        /**
         * s.
         */
        CASH_BALANCE_XY,
        /**
         * k.
         */
        SHARE_HOLDING_XY,
        /**
         * e.
         */
        NORMALISED_XY
    }

    /**
     * Display a chart.
     * @param type chart type
     * @param runId run id
     * @return chart
     * @throws IOException thrown
     */
    JFreeChart showChart(
          ChartType type,
          String runId)
                    throws IOException;
}
