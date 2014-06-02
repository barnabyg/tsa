 /*************************************************************************
 *  2013 BHGAGILE
 *  All Rights Reserved.
 *
 * NOTICE:  All information contained herein is, and remains
 * the property of BHGAGILE.
 */
package com.blizzardtec.tsa.indicator;

import java.util.EnumSet;
import java.util.List;

import com.blizzardtec.tsa.data.FullDataPoint;

/**
 * @author Barnaby Golden
 *
 */
public interface Indicator {

    /**
     * Set of value indicator types.
     */
    EnumSet<IndicatorType> VALUE_INDICATORS = EnumSet.of(
                                IndicatorType.FIVE_DAY_AVERAGE,
                                IndicatorType.THIRTY_DAY_AVERAGE,
                                IndicatorType.FIVE_DAY_VOLATILITY,
                                IndicatorType.THIRTY_DAY_VOLATILITY);

    /**
     * Set of direction indicator types.
     */
    EnumSet<IndicatorType> DIRECTION_INDICATORS = EnumSet.of(
                                IndicatorType.FIVE_DAY_AVERAGE_DIRECTION,
                                IndicatorType.THIRTY_DAY_AVERAGE_DIRECTION,
                                IndicatorType.FIVE_DAY_VOLATILITY_DIRECTION,
                                IndicatorType.THIRTY_DAY_VOLATILITY_DIRECTION);

    /**
     * Types of indicator.
     * @author Barnaby Golden
     *
     */
    public enum IndicatorType {
        /**
         * Five day moving average of mid price.
         */
        FIVE_DAY_AVERAGE,
        /**
         * The direction of movement of the five day moving
         * average of mid price.
         */
        FIVE_DAY_AVERAGE_DIRECTION,
        /**
         * Thirty day moving average of mid price.
         */
        THIRTY_DAY_AVERAGE,
        /**
         * The direction of movement of the thirty day moving
         * average of mid price.
         */
        THIRTY_DAY_AVERAGE_DIRECTION,
        /**
         * Five day moving average of volatility.
         */
        FIVE_DAY_VOLATILITY,
        /**
         * The direction of movement of the five day moving
         * average of volatility.
         */
        FIVE_DAY_VOLATILITY_DIRECTION,
        /**
         * Thirty day moving average of volatility.
         */
        THIRTY_DAY_VOLATILITY,
        /**
         * The direction of movement of the thirty day moving
         * average of volatility.
         */
        THIRTY_DAY_VOLATILITY_DIRECTION }

    /**
     * Enrich the provided list of data points
     * with the indicator value at all the points.
     * @param list list of data points to enrich
     */
    void enrich(List<FullDataPoint> list);
}
