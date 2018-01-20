 /*************************************************************************
 *  2013 BHGAGILE
 *  All Rights Reserved.
 *
 * NOTICE:  All information contained herein is, and remains
 * the property of BHGAGILE.
 */
package com.blizzardtec.tsa.indicator;

import java.util.ArrayList;
import java.util.List;

import com.blizzardtec.tsa.data.FullDataPoint;
import com.blizzardtec.tsa.data.FullDataset;
import com.blizzardtec.tsa.indicator.Indicator.IndicatorType;
import com.blizzardtec.tsa.model.DataPoint;
import com.blizzardtec.tsa.model.Dataset;

/**
 * @author Barnaby Golden
 *
 */
public final class IndicatorHelperImpl implements IndicatorHelper {

    /**
     * {@inheritDoc}
     */
    public FullDataset computeIndicators(
                        final Dataset dataset)
                                    throws IndicatorException {

        final List<FullDataPoint> list = primeList(dataset.getDataPoints());

        Indicator indicator = null;

        final IndicatorType[] indicators = IndicatorType.values();

        // Loop through the list of known indicator types.
        // Each one should be mapped to a method, but if it is not
        // we want to throw an exception.
        for (int i = 0; i < indicators.length; i++) {

            final String indicatorName = indicators[i].toString();

            switch (indicatorName) {
            case "FIVE_DAY_AVERAGE":
                indicator = new FiveDayAverageIndicator();
                break;
            case "FIVE_DAY_AVERAGE_DIRECTION":
                indicator = new FiveDayAverageIndicator();
                break;
            case "THIRTY_DAY_AVERAGE":
                indicator = new ThirtyDayAverageIndicator();
                break;
            case "THIRTY_DAY_AVERAGE_DIRECTION":
                indicator = new ThirtyDayAverageIndicator();
                break;
            case "FIVE_DAY_VOLATILITY":
                indicator = new FiveDayVolatilityIndicator();
                break;
            case "FIVE_DAY_VOLATILITY_DIRECTION":
                indicator = new FiveDayVolatilityIndicator();
                break;
            case "THIRTY_DAY_VOLATILITY":
                indicator = new ThirtyDayVolatilityIndicator();
                break;
            case "THIRTY_DAY_VOLATILITY_DIRECTION":
                indicator = new ThirtyDayVolatilityIndicator();
                break;
            default:
                throw new IndicatorException("Invalid indicator");
            }

            indicator.enrich(list);
        }

        return new FullDataset(list);
    }

    /**
     * Prime a list of full data points from a list
     * of simple data points.
     * @param dpList list of simple data points
     * @return list of full data points
     */
    private List<FullDataPoint> primeList(final List<DataPoint> dpList) {

        final List<FullDataPoint> list = new ArrayList<FullDataPoint>();

        for (final DataPoint dataPoint: dpList) {
            final FullDataPoint fullPoint = new FullDataPoint(dataPoint);
            list.add(fullPoint);
        }

        return list;
    }
}
