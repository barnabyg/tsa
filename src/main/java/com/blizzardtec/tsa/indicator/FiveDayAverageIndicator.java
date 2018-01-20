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
public final class FiveDayAverageIndicator
                extends AverageIndicator implements Indicator {

    /**
     * Period.
     */
    private static final int PERIOD = 5;

    /**
     * Constructor.
     */
    public FiveDayAverageIndicator() {
        super(PERIOD);
    }

    /**
     * {@inheritDoc}
     */
    public void enrich(final List<FullDataPoint> list) {

        for (final FullDataPoint point: list) {

            addPoint(point.getDate(), point.midprice());

            final float average = average();

            point.setIndicator(
                    IndicatorType.FIVE_DAY_AVERAGE,
                    Float.toString(average));

            point.setIndicator(
                    IndicatorType.FIVE_DAY_AVERAGE_DIRECTION,
                    getMovement().toString());
        }
    }
}
