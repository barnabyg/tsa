 /*************************************************************************
 *  2013 BHGAGILE
 *  All Rights Reserved.
 *
 * NOTICE:  All information contained herein is, and remains
 * the property of BHGAGILE.
 */
package com.blizzardtec.tsa.form;

import org.hibernate.validator.constraints.Range;

/**
 * @author Barnaby Golden
 *
 */
public final class AnalysisRunForm {

    /**
     * Maximum starting funds allowed.
     */
    private static final int MAX_FUNDS = 1000000000;
    /**
     * Name of the strategy to test.
     */
    private String strategyName;
    /**
     * Name of the dataset to test against.
     */
    private String datasetName;
    /**
     * Initial amount of cash to start the test run with.
     */
    @Range(min = 1, max = MAX_FUNDS)
    private Float startingFunds;

    /**
     * @return the strategyName
     */
    public String getStrategyName() {
        return strategyName;
    }
    /**
     * @param strategyName the strategyName to set
     */
    public void setStrategyName(final String strategyName) {
        this.strategyName = strategyName;
    }
    /**
     * @return the datasetName
     */
    public String getDatasetName() {
        return datasetName;
    }
    /**
     * @param datasetName the datasetName to set
     */
    public void setDatasetName(final String datasetName) {
        this.datasetName = datasetName;
    }
    /**
     * @return the startingFunds
     */
    public Float getStartingFunds() {
        return startingFunds;
    }
    /**
     * @param startingFunds the startingFunds to set
     */
    public void setStartingFunds(final Float startingFunds) {
        this.startingFunds = startingFunds;
    }
}
