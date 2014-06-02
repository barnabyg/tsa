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
 */
public final class ThirtyDayVolatilityIndicator
                extends VolatilityAverageIndicator implements Indicator {

    /**
     * Period.
     */
    private static final int PERIOD = 5;

    /**
     * Constructor.
     */
    public ThirtyDayVolatilityIndicator() {
        super(PERIOD);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void enrich(final List<FullDataPoint> list) {

        enrichIndicator(
                list,
                IndicatorType.THIRTY_DAY_VOLATILITY,
                IndicatorType.THIRTY_DAY_VOLATILITY_DIRECTION);
    }
}
