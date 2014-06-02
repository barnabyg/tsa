 /*************************************************************************
 *  2013 BHGAGILE
 *  All Rights Reserved.
 *
 * NOTICE:  All information contained herein is, and remains
 * the property of BHGAGILE.
 */
package com.blizzardtec.tsa.analysis;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.blizzardtec.tsa.indicator.IndicatorException;
import com.blizzardtec.tsa.indicator.IndicatorHelper;
import com.blizzardtec.tsa.indicator.Indicator.IndicatorType;
import com.blizzardtec.tsa.model.BalanceHistory;
import com.blizzardtec.tsa.model.BalanceSnapshot;
import com.blizzardtec.tsa.model.DataPoint;
import com.blizzardtec.tsa.model.Dataset;
import com.blizzardtec.tsa.model.Resultset;
import com.blizzardtec.tsa.model.Trade;
import com.blizzardtec.tsa.model.Trade.TradeType;
import com.blizzardtec.tsa.rules.IndicatorValueRule;
import com.blizzardtec.tsa.rules.Rule;
import com.blizzardtec.tsa.rules.Rule.OperationType;
import com.blizzardtec.tsa.strategy.Strategy;
import com.blizzardtec.tsa.strategy.StrategyException;

/**
 * @author Barnaby Golden
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
public final class SellAllTest extends AnalysisBase {

    /**
     * Value to compare against five day average.
     */
    private static final float VALUE = 6.9f;
    /**
     * Actual result.
     */
    private static final float ACTUAL_RESULT = 1012f;

    /**
     * Injected indicator helper.
     * Used to enrich the data.
     */
    @Autowired
    private transient IndicatorHelper indicatorHelper;

    /**
     * Test.
     */
    @Test
    public void flatFiveDayTest() {

        // Add a rule that sells all shares if the five day average
        // goes above 6.9, which should happen on the fourth point.
        Trade trade = new Trade();

        trade.setInstrument(INSTRUMENT);
        trade.setType(TradeType.SELLALL);

        Rule rule = new IndicatorValueRule(
                IndicatorType.FIVE_DAY_AVERAGE,
                    OperationType.GREATER, VALUE, trade);

        final List<Rule> rules = new ArrayList<Rule>();

        rules.add(rule);

        // Add a rule to buy 1 if the five day average
        // is less than 6.9. This should buy all the time
        // with this dataset up until the 'sellall' is triggered.
        trade = new Trade();

        trade.setInstrument(INSTRUMENT);
        trade.setQuantity(QUANTITY);
        trade.setType(TradeType.BUY);

        rule = new IndicatorValueRule(
             IndicatorType.FIVE_DAY_AVERAGE, OperationType.LESS, VALUE, trade);

        rules.add(rule);

        final Strategy strategy = new Strategy();
        strategy.setRules(rules);

        final List<DataPoint> list = new ArrayList<DataPoint>();

        for (int i = 0; i < NUM_POINTS; i++) {
            list.add(makePoint(DATETIMES[i], DATETIME_FORMAT, VALUES[i]));
        }

        final Dataset dataset = new Dataset();
        dataset.setDataPoints(list);
        dataset.setDatasetName(DATASET_NAME);

        Resultset resultset = null;

        try {

            resultset =
                getAnalyser().runAnalysis(
                        indicatorHelper.computeIndicators(dataset),
                        strategy, STARTING_FUNDS);

        } catch (StrategyException e) {
            fail(e.getMessage());
        } catch (IndicatorException e) {
            fail(e.getMessage());
        }

        // check the number of trades that happened
        assertEquals("Incorrect number of trades",
                NUM_POINTS, resultset.getTrades().size());

        final BalanceHistory balanceHistory = resultset.getBalanceHistory();

        testBalanceHistory(balanceHistory);

        testFirstSnapshot(balanceHistory.getSnapshots().get(0));

        testSecondSnapshot(balanceHistory.getSnapshots().get(1));

        // Analyse the last snapshot
        final BalanceSnapshot lastSnapshot =
                balanceHistory.getSnapshots().get(VALUES.length - 1);

        // The number of shares in the final snapshot should be zero as we
        // have executed a sell all
        assertEquals("Invalid share count after final trade",
                        0, lastSnapshot.getShares());

        testFirstTrade(resultset.getTrades().get(0));

        assertEquals("The initial balance was not correctly set",
                STARTING_FUNDS,
                balanceHistory.getInitialBalance(), 0);
    }

    /**
     * Run tests on first snapshot.
     * @param firstSnapshot snapshot
     */
    private void testFirstSnapshot(final BalanceSnapshot firstSnapshot) {

        // Cash balance at first point should be after one trade at
        // the buy price of the first data point
        assertEquals("Balance snapshot did not have correct value",
              STARTING_FUNDS - VALUES[0], firstSnapshot.getBalance(), 0);

        assertEquals("Current worth did not match",
            STARTING_FUNDS, firstSnapshot.getCurrentWorth(), 0);
    }

    /**
     * Test second snapshot.
     * @param secondSnapshot snapshot
     */
    private void testSecondSnapshot(final BalanceSnapshot secondSnapshot) {

        // The second trade should have happened
        assertEquals("Incorrect number of shares",
                        QUANTITY * 2, secondSnapshot.getShares());

        // The cash balance should have gone down by the cost of the trades
        assertEquals("Invalid balance",
            STARTING_FUNDS - (VALUES[0] + VALUES[1]), //NOPMD
            secondSnapshot.getBalance(), 0);
    }

    /**
     * Test first trade.
     * @param firstTrade first trade
     */
    private void testFirstTrade(final Trade firstTrade) {

        assertEquals("Did not trade at the correct price",
                VALUES[0], firstTrade.getPrice(), 0);

        assertEquals("Did not trade with valid trade type",
                TRADE_TYPE, firstTrade.getType());
    }
    /**
     * Run tests on balance history object.
     * @param balanceHistory param
     */
    private void testBalanceHistory(final BalanceHistory balanceHistory) {

        // check first and last dates
        assertEquals("Start date did not match",
                makeDate(DATETIME_FORMAT, DATETIMES[0]),
                balanceHistory.getStartDate());

        assertEquals("End date did not match",
                makeDate(DATETIME_FORMAT, DATETIMES[4]),
                balanceHistory.getEndDate());

        // first check that the number of snapshots in the history
        assertEquals("Incorrect number of balance history points",
                        NUM_POINTS, balanceHistory.getSnapshots().size());

        // Check snapshot dates
        for (int i = 0; i < NUM_POINTS; i++) {

            final BalanceSnapshot snapshot =
                    balanceHistory.getSnapshots().get(i);

            final Date firstDate = makeDate(DATETIME_FORMAT, DATETIMES[i]);

            assertEquals("Dates did not match",
                        firstDate, snapshot.getDate());
        }

        assertEquals("Nominal quantity was not set correctly",
            NOMINAL_QTY, balanceHistory.getNominalQuantity(), 0);

        assertEquals("Nominal quantity was not set correctly",
            NOMINAL_REMAINDER, balanceHistory.getNominalRemainder(), 0);

        assertEquals("Nominal worth did not match",
                NOMINAL_WORTH, balanceHistory.getNominalWorth(), 0);

        assertEquals("Actual result did not match",
                ACTUAL_RESULT, balanceHistory.getActualResult(), 0);

        assertEquals("Performance against nominal did not match",
             ACTUAL_RESULT - NOMINAL_WORTH, balanceHistory.getPerformance(), 0);

        assertEquals("The nominal worth did not match",
                STARTING_FUNDS, balanceHistory.getNominalWorth(5), 0);
    }
}
