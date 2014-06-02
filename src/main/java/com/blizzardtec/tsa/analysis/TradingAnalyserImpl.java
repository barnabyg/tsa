 /*************************************************************************
 *  2013 BHGAGILE
 *  All Rights Reserved.
 *
 * NOTICE:  All information contained herein is, and remains
 * the property of BHGAGILE.
 */
package com.blizzardtec.tsa.analysis;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.blizzardtec.tsa.data.FullDataPoint;
import com.blizzardtec.tsa.data.FullDataset;
import com.blizzardtec.tsa.model.BalanceHistory;
import com.blizzardtec.tsa.model.BalanceSnapshot;
import com.blizzardtec.tsa.model.Resultset;
import com.blizzardtec.tsa.model.Trade;
import com.blizzardtec.tsa.model.Trade.TradeType;
import com.blizzardtec.tsa.rules.Rule;
import com.blizzardtec.tsa.strategy.Strategy;
import com.blizzardtec.tsa.strategy.StrategyException;

/**
 * @author Barnaby Golden
 *
 */
public final class TradingAnalyserImpl implements TradingAnalyser {

    /**
     * Logger.
     */
    private static final Logger LOG =
            Logger.getLogger(TradingAnalyserImpl.class);

    /**
     * Previous balance tracker.
     */
    private transient float previousBalance;
    /**
     * Previous shares quantity tracker.
     */
    private transient int previousShares;

    /**
     * {@inheritDoc}
     */
    public Resultset runAnalysis(
          final FullDataset fullDataset,
          final Strategy strategy,
          final float startingFunds) throws StrategyException {

        LOG.debug("Running analysis");

        if (LOG.isDebugEnabled()) {
            LOG.debug("Evaluating " + strategy.getRules().size() + " rules");
        }

        final Resultset resultset = new Resultset();

        final BalanceHistory balanceHistory
                        = new BalanceHistory(startingFunds);

        previousBalance = startingFunds;
        previousShares = 0;

        boolean firstPoint = true;

        for (final FullDataPoint point: fullDataset.getDataPoints()) {

            // We want a balance snapshot at every data point, regardless
            // of whether any trades took place
            final BalanceSnapshot balanceSnapshot = new BalanceSnapshot();

            // For the first point we calculate how many shares could have been
            // bought with the starting funds.
            // This gets used when we normalise results - i.e. is the trading
            // strategy better than simply buying the shares at the beginning
            // and selling them at the end
            if (firstPoint) {
                setNominalValue(balanceHistory, point.getBuy(), startingFunds);
                balanceSnapshot.setBalance(startingFunds);
                firstPoint = false;
            } else {
                balanceSnapshot.setBalance(previousBalance);
                balanceSnapshot.setShares(previousShares);
            }

            balanceSnapshot.setDate(point.getDate());
            balanceSnapshot.setSellPrice(point.getSell());

            final List<Trade> trades = new ArrayList<Trade>();

            // Run through the list of rules checking to see if any of
            // them trigger a trade
            for (final Rule rule: strategy.getRules()) {

                final Trade trade = rule.runRule(point);

                if (trade != null) {
                    trades.add(trade);
                }
            }

            if (!trades.isEmpty()) {

                for (final Trade trade: trades) {

                    processTrade(trade, balanceSnapshot, resultset);
                }
            }

            previousBalance = balanceSnapshot.getBalance();
            previousShares = balanceSnapshot.getShares();

            balanceHistory.add(balanceSnapshot);
        }

        resultset.setBalanceHistory(balanceHistory);

        return resultset;
    }

    /**
     * Process a single trade.
     * @param trade trade to process
     * @param balanceSnapshot balance at the time
     * @param resultset result set
     */
    private void processTrade(
                final Trade trade,
                final BalanceSnapshot balanceSnapshot,
                final Resultset resultset) {

        // only process a buy trade if the account
        // has enough funds to make the trade
        if (trade.getType() == TradeType.BUY
             && trade.tradeValue() < balanceSnapshot.getBalance()) {

            resultset.addTrade(trade);

            balanceSnapshot.setBalance(
                    previousBalance - trade.tradeValue());

            balanceSnapshot.setShares(
                    previousShares + trade.getQuantity());
        }

        if (trade.getType() == TradeType.BUYALL) {

            // buy as many shares as we can afford at the buy price
            final int count =
                    (int) (balanceSnapshot.getBalance() / trade.getPrice());

            if (count > 0) {
                trade.setQuantity(count);

                resultset.addTrade(trade);

                balanceSnapshot.setBalance(
                        previousBalance - trade.tradeValue());

                balanceSnapshot.setShares(
                        previousShares + trade.getQuantity());
            }
        }

        // only process a sell trade if the account
        // has enough shares
        if (trade.getType() == TradeType.SELL
             && trade.getQuantity() < balanceSnapshot.getShares()) {

            resultset.addTrade(trade);

            balanceSnapshot.setBalance(
                    previousBalance + trade.tradeValue());

            balanceSnapshot.setShares(
                    previousShares - trade.getQuantity());
        }

        if (trade.getType() == TradeType.SELLALL
             && balanceSnapshot.getShares() > 0) {

            trade.setQuantity(balanceSnapshot.getShares());

            resultset.addTrade(trade);

            balanceSnapshot.setBalance(
                    previousBalance + trade.tradeValue());

            balanceSnapshot.setShares(
                    previousShares - trade.getQuantity());
        }
    }

    /**
     * Set the nominal values for the first point
     * in a data set.
     * @param balanceHistory the history to populate
     * @param buyPrice the buy price of the first point
     * @param startingFunds the starting funds available
     */
    private void setNominalValue(
            final BalanceHistory balanceHistory,
            final float buyPrice,
            final float startingFunds) {

        final int nominalQuantity =
                (int) Math.floor(startingFunds / buyPrice);

        final float nominalRemainder =
                startingFunds - nominalQuantity * buyPrice;

        balanceHistory.setNominalQuantity(nominalQuantity);
        balanceHistory.setNominalRemainder(nominalRemainder);
    }
}
