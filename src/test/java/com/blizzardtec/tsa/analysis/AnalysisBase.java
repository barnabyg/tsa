 /*************************************************************************
 *  2013 BHGAGILE
 *  All Rights Reserved.
 *
 * NOTICE:  All information contained herein is, and remains
 * the property of BHGAGILE.
 */
package com.blizzardtec.tsa.analysis;

import org.springframework.beans.factory.annotation.Autowired;

import com.blizzardtec.tsa.TestBase;
import com.blizzardtec.tsa.indicator.Indicator.IndicatorType;
import com.blizzardtec.tsa.model.Trade;
import com.blizzardtec.tsa.model.Trade.TradeType;
import com.blizzardtec.tsa.rules.IndicatorDirectionRule;
import com.blizzardtec.tsa.rules.IndicatorValueRule;
import com.blizzardtec.tsa.rules.Rule;
import com.blizzardtec.tsa.rules.Rule.OperationType;

/**
 * @author Barnaby Golden
 *
 */
public class AnalysisBase extends TestBase {

    /**
     * Value to check indicator against.
     */
    private static final float VALUE = 80.0f;
    /**
     * Starting funds.
     */
    protected static final float STARTING_FUNDS = 1002f;
    /**
     * The nominal quantity of shares that could have been bought
     * with the initial funds available.
     */
    protected static final float NOMINAL_QTY = 200f;
    /**
     * The amount of funds that would be left over after the
     * nominal quantity of shares has been bought with the
     * initial funds available.
     */
    protected static final float NOMINAL_REMAINDER = 2f;
    /**
     * Nominal worth is the result of holding the maximum number
     * of shares possible with the initial funds for the duration
     * of the analysis.
     */
    protected static final float NOMINAL_WORTH = 1802f;
    /**
     * Actual result of the run. This is the net worth at the end
     * of the run using the given strategy.
     */
    protected static final float ACTUAL_RESULT = 1008f;
    /**
     * The number of points in the test.
     */
    protected static final int NUM_POINTS = 5;

    /**
     * Format of date-time to use.
     */
    protected static final String DATETIME_FORMAT = "yyyy-MM-dd-HH:mm:ss";
    /**
     * Date-times to populate the data points with.
     */
    protected static final String[] DATETIMES = {
                                    "2012-03-11-17:22:58",
                                    "2012-03-12-17:22:58",
                                    "2012-03-13-17:22:58",
                                    "2012-03-14-17:22:58",
                                    "2012-03-15-17:22:58" };
    /**
     * Float values to populate the data points with.
     */
    protected static final float[] VALUES = {5, 6, 7, 8, 9};
    /**
     * Trade type to use.
     */
    protected static final TradeType TRADE_TYPE = TradeType.BUY;
    /**
     * Quantity of shares to trade.
     */
    protected static final int QUANTITY = 1;

    /**
     * Trading analyser.
     */
    @Autowired
    private transient TradingAnalyser analyser;

    /**
     * Get analyser.
     * @return analyser
     */
    protected final TradingAnalyser getAnalyser() {
        return this.analyser;
    }

    /**
     * Make a populated indicator value rule.
     * @param indicatorType indicator type
     * @param opType operation type
     * @return rule
     */
    protected final Rule makeIndicatorValueRule(
            final IndicatorType indicatorType, final OperationType opType) {

        final Trade trade = new Trade();

        trade.setInstrument(INSTRUMENT);
        trade.setQuantity(QUANTITY);
        trade.setType(TRADE_TYPE);

        final Rule rule = new IndicatorValueRule(
                            indicatorType, opType, VALUE, trade);

        return rule;
    }

    /**
     * Make a populated indicator direction rule.
     * @param indicatorType indicator type
     * @param opType operation type
     * @return rule
     */
    protected final Rule makeIndicatorDirectionRule(
            final IndicatorType indicatorType, final OperationType opType) {

        final Trade trade = new Trade();

        trade.setInstrument(INSTRUMENT);
        trade.setQuantity(QUANTITY);
        trade.setType(TRADE_TYPE);

        final Rule rule = new IndicatorDirectionRule(
                                    indicatorType, opType, trade);

        return rule;
    }
}
