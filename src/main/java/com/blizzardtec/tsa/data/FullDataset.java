 /*************************************************************************
 *  2013 BHGAGILE
 *  All Rights Reserved.
 *
 * NOTICE:  All information contained herein is, and remains
 * the property of BHGAGILE.
 */
package com.blizzardtec.tsa.data;

import java.util.List;

/**
 * @author Barnaby Golden
 *
 */
public final class FullDataset {

    /**
     * The name of the dataset.
     */
    private String name;
    /**
     * List of full data points.
     */
    private List<FullDataPoint> dataPoints;

    /**
     * Default constructor.
     */
    public FullDataset() {
        super();
    }

    /**
     * Construct with a points list.
     * @param dataPoints list of full data points.
     */
    public FullDataset(final List<FullDataPoint> dataPoints) {
        this.dataPoints = dataPoints;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }
    /**
     * @param name the name to set
     */
    public void setName(final String name) {
        this.name = name;
    }
    /**
     * @return the dataPoints
     */
    public List<FullDataPoint> getDataPoints() {
        return dataPoints;
    }
    /**
     * @param dataPoints the dataPoints to set
     */
    public void setDataPoints(final List<FullDataPoint> dataPoints) {
        this.dataPoints = dataPoints;
    }
}
