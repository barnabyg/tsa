 /*************************************************************************
 *  2013 BHGAGILE
 *  All Rights Reserved.
 *
 * NOTICE:  All information contained herein is, and remains
 * the property of BHGAGILE.
 */
package com.blizzardtec.tsa.rules;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.blizzardtec.tsa.analysis.AnalysisBase;
import com.blizzardtec.tsa.indicator.IndicatorException;
import com.blizzardtec.tsa.indicator.IndicatorHelper;
import com.blizzardtec.tsa.indicator.Indicator.IndicatorType;
import com.blizzardtec.tsa.model.DataPoint;
import com.blizzardtec.tsa.model.Dataset;
import com.blizzardtec.tsa.model.Resultset;
import com.blizzardtec.tsa.model.RuleXml;
import com.blizzardtec.tsa.model.Trade.TradeType;
import com.blizzardtec.tsa.strategy.Strategy;
import com.blizzardtec.tsa.strategy.StrategyException;
import com.blizzardtec.tsa.rules.Rule.OperationType;

/**
 * @author Barnaby Golden
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
public final class IndicatorRulesTest extends AnalysisBase {

    /**
     * Rule name.
     */
    private static final String RULE_NAME = "test rule";

    /**
     * Rule helper.
     */
    @Autowired
    private transient RuleHelper ruleHelper;
    /**
     * Injected indicator helper.
     * Used to enrich the data.
     */
    @Autowired
    private transient IndicatorHelper indicatorHelper;

    /**
     * Check various indicator rule trade types.
     */
    @Test
    public void indicatorRulesTest() {

        Rule rule = makeIndicatorDirectionRule(
              IndicatorType.FIVE_DAY_AVERAGE_DIRECTION, OperationType.RISING);

        final List<Rule> rules = new ArrayList<Rule>();

        rule.getTrade().setType(TradeType.BUY);

        rules.add(rule);

        rule = makeIndicatorDirectionRule(
              IndicatorType.FIVE_DAY_AVERAGE_DIRECTION, OperationType.RISING);

        rule.getTrade().setType(TradeType.SELL);

        rules.add(rule);

        rule = makeIndicatorDirectionRule(
              IndicatorType.FIVE_DAY_AVERAGE_DIRECTION, OperationType.RISING);

        rule.getTrade().setType(TradeType.SELLALL);

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

        assertNotNull("Result set was null", resultset);
    }

    /**
     * RuleXml create, retrieve, update and delete test.
     */
    @Test
    public void ruleCrudTest() {

        final Rule rule = makeIndicatorDirectionRule(
                IndicatorType.FIVE_DAY_AVERAGE_DIRECTION, OperationType.RISING);

        rule.setName(RULE_NAME);

        try {
            ruleHelper.persistRule(rule.toXml());
        } catch (RuleException e) {
            fail(e.getMessage());
        }

        final RuleXml ruleXml = ruleHelper.getRule(RULE_NAME);

        assertNotNull("Null rule xml", ruleXml);

        ruleHelper.deleteRule(RULE_NAME);
    }
}
