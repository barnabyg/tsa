 /*************************************************************************
 *  2013 BHGAGILE
 *  All Rights Reserved.
 *
 * NOTICE:  All information contained herein is, and remains
 * the property of BHGAGILE.
 */
package com.blizzardtec.tsa.strategy;

import java.util.List;

import com.blizzardtec.tsa.rules.Rule;
import com.blizzardtec.tsa.rules.RuleException;

/**
 * @author Barnaby Golden
 *
 */
public final class Strategy {

    /**
     * XML header.
     */
    private static final String XML_HEADER
           = "<?xml version=\"1.0\" encoding=\"ISO-8859-1\"?>";

    /**
     * List of rules.
     */
    private List<Rule> rules;
    /**
     * The name for this strategy.
     */
    private String name;
    /**
     * @return the rules
     */
    public List<Rule> getRules() {
        return rules;
    }
    /**
     * @param rules the rules to set
     */
    public void setRules(final List<Rule> rules) {
        this.rules = rules;
    }
    /**
     * @return the name
     */
    public String getName() {
        return name;
    }
    /**
     * @param name the name to set
     */
    public void setName(final String name) {
        this.name = name;
    }

    /**
     * Convert this strategy to XML.
     * @return the XML as a string
     * @throws StrategyException thrown
     */
    public String toXml() throws StrategyException {

        final StringBuffer buffer = new StringBuffer(128);

        buffer.append(XML_HEADER + "<rules>");

        try {
            for (final Rule rule: rules) {
                buffer.append(rule.toXml().getXml());
            }
        } catch (RuleException re) {
            throw new StrategyException(re);
        }

        buffer.append("</rules>");

        return buffer.toString();
    }
}
