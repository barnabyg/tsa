 /*************************************************************************
 *  2013 BHGAGILE
 *  All Rights Reserved.
 *
 * NOTICE:  All information contained herein is, and remains
 * the property of BHGAGILE.
 */
package com.blizzardtec.tsa.analysis;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.blizzardtec.tsa.data.FullDataset;
import com.blizzardtec.tsa.indicator.IndicatorException;
import com.blizzardtec.tsa.indicator.IndicatorHelper;
import com.blizzardtec.tsa.indicator.Indicator.IndicatorType;
import com.blizzardtec.tsa.model.BalanceHistory;
import com.blizzardtec.tsa.model.BalanceSnapshot;
import com.blizzardtec.tsa.model.DataPoint;
import com.blizzardtec.tsa.model.Dataset;
import com.blizzardtec.tsa.model.Resultset;
import com.blizzardtec.tsa.model.Trade;
import com.blizzardtec.tsa.rules.Rule.OperationType;
import com.blizzardtec.tsa.rules.Rule;
import com.blizzardtec.tsa.strategy.Strategy;
import com.blizzardtec.tsa.strategy.StrategyException;

/**
 * @author Barnaby Golden
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
public final class FlatFiveDayAverageTest extends AnalysisBase {

    /**
     * Injected indicator helper.
     * Used to enrich the data.
     */
    @Autowired
    private transient IndicatorHelper indicatorHelper;

    /**
     * Test a simple analysis run.
     * Uses a single rule, which will buy if the five day moving
     * average is rising, which it is designed to be.
     * There are five data points so it should execute a trade on
     * every data point.
     * Each trade should be of the quantity and type specified in the rule.
     * The balance should decrease by the trade value (i.e. quantity multiplied
     * by buy/sell price) each time.
     * The balance at the end should be starting funds minus the sum of all
     * trade values.
     */
    @Test
    public void flatFiveDayTest() {

        final Rule rule = makeIndicatorDirectionRule(
                IndicatorType.FIVE_DAY_AVERAGE_DIRECTION, OperationType.RISING);

        final List<Rule> rules = new ArrayList<Rule>();

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

        FullDataset fullDataset = null;

        try {
            fullDataset = indicatorHelper.computeIndicators(dataset);
        } catch (IndicatorException e1) {
            fail(e1.getMessage());
        }

        fullDataset.setName(DATASET_NAME);

        assertEquals("Dataset name did not match",
                        DATASET_NAME, fullDataset.getName());

        assertFalse("Dataset was empty",
                fullDataset.getDataPoints().isEmpty());

        try {

            resultset =
                 getAnalyser().runAnalysis(
                         fullDataset,
                         strategy, STARTING_FUNDS);

        } catch (StrategyException e) {
            fail(e.getMessage());
        }

        // check the number of trades that happened
        assertEquals("Incorrect number of trades",
                NUM_POINTS - 1, resultset.getTrades().size());

        final BalanceHistory balanceHistory = resultset.getBalanceHistory();

        testBalanceHistory(balanceHistory);

        // Analyse the first snapshot
        final BalanceSnapshot firstSnapshot =
                balanceHistory.getSnapshots().get(0);

        testFirstSnapshot(firstSnapshot);

        // Analyse the second snapshot
        final BalanceSnapshot secondSnapshot =
                balanceHistory.getSnapshots().get(1);

        // The first trade should have happened, so we now own some shares
        assertEquals("Incorrect number of shares",
                        QUANTITY, secondSnapshot.getShares());

        // The cash balance should have gone down by the cost of the fist trade
        assertEquals("Invalid balance",
                STARTING_FUNDS - VALUES[1], secondSnapshot.getBalance(), 0);

        // Analyse the third snapshot
        final BalanceSnapshot thirdSnapshot =
                balanceHistory.getSnapshots().get(2);

        // The number of shares in the third snapshot should have gone up
        // again as there would have been another trade.
        assertEquals("Invalid share count after third trade",
                        QUANTITY * 2, thirdSnapshot.getShares());

        // Analyse the first trade
        // This will have happened on the second data point when
        // the 5-day average was rising for the first time
        final Trade firstTrade = resultset.getTrades().get(0);

        testFirstTrade(firstTrade);

        assertEquals("The initial balance was not correctly set",
                STARTING_FUNDS,
                balanceHistory.getInitialBalance(), 0);
    }

    /**
     * Test first snapshot.
     * @param firstSnapshot first snapshot
     */
    private void testFirstSnapshot(final BalanceSnapshot firstSnapshot) {
        // Cash balance at first point should be unchanged
        // as all averages will be FLAT on the first data point
        assertEquals("Balance snapshot did not have correct value",
              STARTING_FUNDS, firstSnapshot.getBalance(), 0);

        assertEquals("Current worth did not match",
            STARTING_FUNDS, firstSnapshot.getCurrentWorth(), 0);
    }

    /**
     * Test first trade.
     * @param firstTrade the first trade to test
     */
    private void testFirstTrade(final Trade firstTrade) {
        assertEquals("Did not trade at the correct price",
                VALUES[1], firstTrade.getPrice(), 0);

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
