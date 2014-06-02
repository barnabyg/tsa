 /*************************************************************************
 *  2013 BHGAGILE
 *  All Rights Reserved.
 *
 * NOTICE:  All information contained herein is, and remains
 * the property of BHGAGILE.
 */
package com.blizzardtec.tsa.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.blizzardtec.tsa.TestBase;
import com.blizzardtec.tsa.model.BalanceHistory;
import com.blizzardtec.tsa.model.BalanceSnapshot;
import com.blizzardtec.tsa.model.Resultset;
import com.blizzardtec.tsa.model.Trade;
import com.blizzardtec.tsa.model.Trade.TradeType;

/**
 * @author Barnaby Golden
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
public final class ResultsetDaoTest extends TestBase {

    /**
     * Nominal remainder.
     */
    private static final float NOMINAL_REMAINDER = 788.3f;
    /**
     * Nominal quantity.
     */
    private static final int NOMINAL_QTY = 234;
    /**
     * Initial balance.
     */
    private static final float INITIAL_BALANCE = 1575f;
    /**
     * Shares.
     */
    private static final int SHARES = 23;
    /**
     * Run ID.
     */
    private static final String RUN_NAME = "run-name";
    /**
     * Quantity.
     */
    private static final int QUANTITY = 3;
    /**
     * Price.
     */
    private static final float PRICE = 1f;
    /**
     * Instrument.
     */
    private static final String INSTRUMENT = "blah";
    /**
     * Trade type.
     */
    private static final TradeType TYPE = TradeType.SELL;
    /**
     * Balance.
     */
    private static final float BALANCE = 1.6f;
    /**
     * Format of date-time to use.
     */
    private static final String DATETIME_FORMAT = "yyyy-MM-dd-HH:mm:ss";
    /**
     * Date-time value.
     */
    private static final String DATETIME = "2012-03-19-17:22:58";
    /**
     * Result set DAO.
     */
    @Autowired
    private transient ResultsetDao resultsetDao;

    /**
     * Create, retrieve and then delete a result set.
     */
    @Test
    public void resultsetTest() {

        final Resultset resultset =
                new Resultset(RUN_NAME);

        resultset.setBalanceHistory(makeBalanceHistory());

        resultset.addTrade(makeTrade());
        resultset.addTrade(makeTrade());

        final long id = resultsetDao.create(resultset);

        final Resultset resultset2 = resultsetDao.retrieve(id);

        final BalanceHistory balanceHistory = resultset.getBalanceHistory();

        assertEquals("initial balance did not match",
                INITIAL_BALANCE, balanceHistory.getInitialBalance(), 0);

        assertEquals("nominal quantity did not match",
                NOMINAL_QTY, balanceHistory.getNominalQuantity());

        assertEquals("nominal remainder did not match",
                NOMINAL_REMAINDER, balanceHistory.getNominalRemainder(), 0);

        assertEquals("did not find 2 trades in resultset",
                2, resultset2.getTrades().size());
        assertEquals("did not find 2 balances in resultset",
                2, resultset2.getBalanceHistory().getSnapshots().size());

        resultsetDao.delete(resultset);

        final Resultset resultset3 = resultsetDao.retrieve(id);

        assertNull("Resultset was not deleted", resultset3);
    }

    /**
     * Populate trade.
     * @return a populated trade
     */
    private Trade makeTrade() {

        final Trade trade = new Trade();

        trade.setDate(makeDate(DATETIME_FORMAT, DATETIME));

        trade.setInstrument(INSTRUMENT);
        trade.setPrice(PRICE);
        trade.setQuantity(QUANTITY);
        trade.setType(TYPE);

        return trade;
    }

    /**
     * Populate a balance history.
     * @return balance history
     */
    private BalanceHistory makeBalanceHistory() {

        final BalanceHistory balanceHistory =
                    new BalanceHistory(INITIAL_BALANCE);

        final BalanceSnapshot balance = new BalanceSnapshot();

        balance.setDate(makeDate(DATETIME_FORMAT, DATETIME));

        balance.setBalance(BALANCE);

        balance.setShares(SHARES);

        balanceHistory.add(balance);

        final BalanceSnapshot balance2 = new BalanceSnapshot();

        balance2.setDate(makeDate(DATETIME_FORMAT, DATETIME));

        balance2.setBalance(BALANCE);

        balance2.setShares(SHARES);

        balanceHistory.add(balance2);

        balanceHistory.setNominalQuantity(NOMINAL_QTY);

        balanceHistory.setNominalRemainder(NOMINAL_REMAINDER);

        return balanceHistory;
    }
}
