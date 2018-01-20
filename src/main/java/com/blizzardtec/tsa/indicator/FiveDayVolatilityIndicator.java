 /*************************************************************************
 *  2013 BHGAGILE
 *  All Rights Reserved.
 *
 * NOTICE:  All information contained herein is, and remains
 * the property of BHGAGILE.
 */
package com.blizzardtec.tsa.indicator;

import java.util.List;

import com.blizzardtec.tsa.data.FullDataPoint;

/**
 * @author Barnaby Golden
 *
 * This uses SAMPLE standard deviation and not population.
 * It is also not annualised so the values will relate to the
 * frequency of the data - e.g. if the data is daily then the
 * calculated standard deviation is 1-day volatility.
 */
public final class FiveDayVolatilityIndicator
            extends VolatilityAverageIndicator implements Indicator {

    /**
     * Period.
     */
    private static final int PERIOD = 5;

    /**
     * Constructor.
     */
    public FiveDayVolatilityIndicator() {
        super(PERIOD);
    }

    /**
     * {@inheritDoc}
     */
    public void enrich(final List<FullDataPoint> list) {

        enrichIndicator(
                list,
                IndicatorType.FIVE_DAY_VOLATILITY,
                IndicatorType.FIVE_DAY_VOLATILITY_DIRECTION);
    }
}
