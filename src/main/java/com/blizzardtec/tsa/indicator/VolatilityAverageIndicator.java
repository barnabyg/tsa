 /*************************************************************************
 *  2013 BHGAGILE
 *  All Rights Reserved.
 *
 * NOTICE:  All information contained herein is, and remains
 * the property of BHGAGILE.
 */
package com.blizzardtec.tsa.indicator;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.math3.stat.descriptive.SummaryStatistics;

import com.blizzardtec.tsa.data.FullDataPoint;
import com.blizzardtec.tsa.indicator.Indicator.IndicatorType;
import com.blizzardtec.tsa.rules.Rule.OperationType;

/**
 * @author Barnaby Golden
 *
 */
public class VolatilityAverageIndicator extends AverageIndicator {

    /**
     * Constructor initialises the moving average duration.
     * @param period moving average duration
     */
    public VolatilityAverageIndicator(final int period) {
        super(period);
    }

    /**
     * Enrich data.
     * @param list list of data points to enrich
     * @param valueType indicator value type
     * @param directionType indicator direction type
     */
    protected final void enrichIndicator(
            final List<FullDataPoint> list,
            final IndicatorType valueType,
            final IndicatorType directionType) {

        boolean first = true;

        float lastPrice = 0;
        float lastStdDev = 0;

        for (final FullDataPoint point: list) {

            if (first) {
                lastPrice = point.midprice();

                point.setIndicator(
                        valueType,
                        Float.toString(0));

                point.setIndicator(
                        directionType,
                        OperationType.FLAT.toString());

                first = false;
            } else {

                final float change = (point.midprice() - lastPrice) / lastPrice;

                addPoint(point.getDate(), change);


                final Set<Float> valueSet = new HashSet<Float>();

                for (final Map.Entry<Date, Float> entry : getMap().entrySet()) {

                    valueSet.add(entry.getValue());
                }

                final float stdDev = getStdDev(valueSet);

                point.setIndicator(
                        valueType,
                        Float.toString(stdDev));

                OperationType type = null;

                if (lastStdDev == 0) {
                    type = OperationType.FLAT;
                } else if (stdDev > lastStdDev) {
                    type = OperationType.RISING;
                } else {
                    type = OperationType.FALLING;
                }

                point.setIndicator(
                        directionType,
                        type.toString());

                lastStdDev = stdDev;
                lastPrice = point.midprice();
            }
        }
    }

    /**
     * Calculate the standard deviation of a set of float values.
     *
     * @param valueSet set of float values
     * @return variance
     */
    protected final float getStdDev(final Set<Float> valueSet) {

        final SummaryStatistics stats = new SummaryStatistics();

        for (final float a: valueSet) {
            stats.addValue(a);
        }

        return (float) stats.getStandardDeviation();
    }
}
