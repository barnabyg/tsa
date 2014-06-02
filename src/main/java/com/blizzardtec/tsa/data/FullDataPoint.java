 /*************************************************************************
 *  2013 BHGAGILE
 *  All Rights Reserved.
 *
 * NOTICE:  All information contained herein is, and remains
 * the property of BHGAGILE.
 */
package com.blizzardtec.tsa.data;

import java.util.HashMap;
import java.util.Map;

import com.blizzardtec.tsa.indicator.Indicator.IndicatorType;
import com.blizzardtec.tsa.model.DataPoint;

/**
 * @author Barnaby Golden
 *
 */
public final class FullDataPoint extends DataPoint {

    /**
     * Map of indicator values at this point.
     */
    private final transient Map<IndicatorType, String> indicatorMap;

    /**
     * Constructor, just to initiate the map.
     * @param dataPoint <code>DataPoint</code> object to initialise from
     */
    public FullDataPoint(final DataPoint dataPoint) {

        super();

        indicatorMap = new HashMap<IndicatorType, String>();

        this.setBuy(dataPoint.getBuy());
        this.setDate(dataPoint.getDate());
        this.setId(dataPoint.getId());
        this.setInstrument(dataPoint.getInstrument());
        this.setSell(dataPoint.getSell());
    }

    /**
     * Put an indicator value in to the map.
     * @param indicator technical indicator
     * @param value value for the indicator at this point
     */
    public void setIndicator(
            final IndicatorType indicator, final String value) {

        indicatorMap.put(indicator, value);
    }

    /**
     * Get the value of the given indicator.
     * @param indicator technical indicator
     * @return returns the value of the indicator at this point
     */
    public String getIndicator(final IndicatorType indicator) {

        return indicatorMap.get(indicator);
    }
}
