 /*************************************************************************
 *  2013 BHGAGILE
 *  All Rights Reserved.
 *
 * NOTICE:  All information contained herein is, and remains
 * the property of BHGAGILE.
 */
package com.blizzardtec.tsa.form;

/**
 * @author Barnaby Golden
 *
 */
public final class VisualiseForm {

    /**
     * Run id.
     */
    private String runId;

    /**
     * Type of chart to display.
     */
    private String chartType;

    /**
     * @return the runId
     */
    public String getRunId() {
        return runId;
    }

    /**
     * @param runId the runId to set
     */
    public void setRunId(final String runId) {
        this.runId = runId;
    }

    /**
     * @return the chartType
     */
    public String getChartType() {
        return chartType;
    }

    /**
     * @param chartType the chartType to set
     */
    public void setChartType(final String chartType) {
        this.chartType = chartType;
    }
}
