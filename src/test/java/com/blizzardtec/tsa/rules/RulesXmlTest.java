 /*************************************************************************
 *  2013 BHGAGILE
 *  All Rights Reserved.
 *
 * NOTICE:  All information contained herein is, and remains
 * the property of BHGAGILE.
 */
package com.blizzardtec.tsa.rules;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import org.junit.Test;

import com.blizzardtec.tsa.analysis.AnalysisBase;
import com.blizzardtec.tsa.indicator.Indicator.IndicatorType;
import com.blizzardtec.tsa.model.RuleXml;
import com.blizzardtec.tsa.rules.Rule.OperationType;

/**
 * @author Barnaby Golden
 *
 */
public final class RulesXmlTest extends AnalysisBase {

    /**
     * Rule name.
     */
    private static final String RULE_NAME = "rule name";

    /**
     * Rule XML.
     */
    private static final String RULE_XML
    = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
    + "<indicator-value-rule>"
     + "<indicator>"
      + "<name>FIVE_DAY_AVERAGE</name>"
      + "<operation>GREATER</operation>"
      + "<value>80.0</value>"
     + "</indicator>"
     + "<trade>"
      + "<instrument>BP.L1</instrument>"
      + "<type>BUY</type>"
      + "<quantity>1</quantity>"
     + "</trade>"
    + "</indicator-value-rule>";

    /**
     * Test the conversion of a value rule to XML.
     */
    @Test
    public void convertValueRuleToXmlTest() {

        final Rule rule =
            this.makeIndicatorValueRule(
                    IndicatorType.FIVE_DAY_AVERAGE, OperationType.GREATER);

        rule.setName(RULE_NAME);

        RuleXml ruleXml = null;

        try {
            ruleXml = rule.toXml();
        } catch (RuleException e) {
            fail(e.getMessage());
        }

        assertEquals("Rule name did not match", RULE_NAME, ruleXml.getName());
    }

    /**
     * Test the conversion of a value rule to XML.
     */
    @Test
    public void convertDirectionRuleToXmlTest() {

        final Rule rule =
            this.makeIndicatorDirectionRule(
                    IndicatorType.FIVE_DAY_AVERAGE, OperationType.GREATER);

        rule.setName(RULE_NAME);

        RuleXml ruleXml = null;

        try {
            ruleXml = rule.toXml();
        } catch (RuleException e) {
            fail(e.getMessage());
        }

        assertEquals("Rule name did not match", RULE_NAME, ruleXml.getName());
    }

    /**
     * Convert some Rule XML to a <code>Rule</code> object.
     */
    @Test
    public void convertXmlToRuleTest() {

        final RuleXml ruleXml = new RuleXml();
        ruleXml.setName(RULE_NAME);
        ruleXml.setXml(RULE_XML);

        Rule rule = null;

        try {
            rule = ruleXml.toRule();
        } catch (RuleException e) {
            fail(e.getMessage());
        }

        assertNotNull("Rule was null", rule);
    }
}
