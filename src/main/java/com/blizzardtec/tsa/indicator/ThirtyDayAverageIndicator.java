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
public final class ThirtyDayAverageIndicator
                extends AverageIndicator implements Indicator {

    /**
     * Period.
     */
    private static final int PERIOD = 30;

    /**
     * Constructor.
     */
    public ThirtyDayAverageIndicator() {
        super(PERIOD);
    }

    /**
     * {@inheritDoc}
     */
    public void enrich(final List<FullDataPoint> list) {

        for (final FullDataPoint point: list) {

            addPoint(point.getDate(), point.midprice());

            point.setIndicator(
                    IndicatorType.THIRTY_DAY_AVERAGE,
                    Float.toString(average()));

            point.setIndicator(
                    IndicatorType.THIRTY_DAY_AVERAGE_DIRECTION,
                    getMovement().toString());
        }
    }
}
