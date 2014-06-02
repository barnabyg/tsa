 /*************************************************************************
 *  2013 BHGAGILE
 *  All Rights Reserved.
 *
 * NOTICE:  All information contained herein is, and remains
 * the property of BHGAGILE.
 */
package com.blizzardtec.tsa.strategy;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.blizzardtec.tsa.DelegateException;
import com.blizzardtec.tsa.StreamAdapter;
import com.blizzardtec.tsa.TestBase;
import com.blizzardtec.tsa.indicator.Indicator.IndicatorType;
import com.blizzardtec.tsa.model.StrategyXml;
import com.blizzardtec.tsa.model.Trade;
import com.blizzardtec.tsa.model.Trade.TradeType;
import com.blizzardtec.tsa.rules.IndicatorDirectionRule;
import com.blizzardtec.tsa.rules.Rule.OperationType;
import com.blizzardtec.tsa.rules.Rule;

/**
 * @author Barnaby Golden
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
public final class StrategyParserTest extends TestBase {

    /**
     * Stream adapter to extract XML from files.
     */
    @Autowired
    private transient StreamAdapter adapter;

    /**
     * Test that an XML string can be parsed in to a
     * valid list of rules.
     */
    @Test
    public void parseStrategyTest() {

        String xmlStr = null;

        try {
            xmlStr = adapter.extractFile(
                    new FileInputStream(STRATEGY_FILE));
        } catch (FileNotFoundException e1) {
            fail(e1.getMessage());
        } catch (DelegateException e1) {
            fail(e1.getMessage());
        }

        List<Rule> rules = null;

        final StrategyXml strategyXml = new StrategyXml();
        strategyXml.setXml(xmlStr);

        try {
            rules = strategyXml.toStrategy().getRules();
        } catch (StrategyException e) {
            fail(e.getMessage());
        }

        assertEquals("Invalid number of rules found", 5, rules.size());

        final IndicatorDirectionRule rule =
                    (IndicatorDirectionRule) rules.get(0);

        assertEquals("Invalid indicator type",
            IndicatorType.FIVE_DAY_AVERAGE_DIRECTION, rule.getIndicatorType());
        assertEquals("Operation does not match",
                OperationType.RISING, rule.getOperationType());

        final Trade trade = rule.getTrade();

        assertNotNull("No trade found", trade);

        assertEquals("Instrument mismatch",
                INSTRUMENT, trade.getInstrument());

        assertEquals("Quantity does not match", 1, trade.getQuantity());

        assertEquals("Trade type does not match",
                        TradeType.BUY, trade.getType());

        Strategy strategy = null;

        String strategyXmlStr = null;

        try {
            strategy = strategyXml.toStrategy();

            strategyXmlStr = strategy.toXml();

        } catch (StrategyException e) {
            fail(e.getMessage());
        }

        assertNotNull("Strategy XML was null", strategyXmlStr);
        assertFalse("Strategy XML was empty", strategyXmlStr.isEmpty());
    }
}
