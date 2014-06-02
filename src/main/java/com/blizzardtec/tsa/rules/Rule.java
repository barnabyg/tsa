 /*************************************************************************
 *  2013 BHGAGILE
 *  All Rights Reserved.
 *
 * NOTICE:  All information contained herein is, and remains
 * the property of BHGAGILE.
 */
package com.blizzardtec.tsa.rules;

import java.util.EnumSet;

import com.blizzardtec.tsa.data.FullDataPoint;
import com.blizzardtec.tsa.model.RuleXml;
import com.blizzardtec.tsa.model.Trade;

/**
 * @author Barnaby Golden
 *
 */
public interface Rule {

    /**
     * Set of direction operations.
     */
    EnumSet<OperationType> DIRECTION_OPS = EnumSet.of(
                                            OperationType.RISING,
                                            OperationType.FALLING,
                                            OperationType.FLAT);

    /**
     * Set of value operations.
     */
    EnumSet<OperationType> VALUE_OPS = EnumSet.of(
                                            OperationType.GREATER,
                                            OperationType.LESS,
                                            OperationType.EQUAL);

    /**
     * Types of rule supported.
     * @author Barnaby Golden
     *
     */
    enum RuleType { INDICATOR_VALUE, INDICATOR_DIRECTION }

    /**
     * Rule operation types.
     * @author Barnaby Golden
     *
     */
    public enum OperationType { RISING, FALLING, FLAT, LESS, GREATER, EQUAL }

    /**
     * Run a rule and return a trade if the rule
     * requires one.
     * @param point the data point to process the rule on
     * @return null if the rule does not fire, or a trade if it does
     */
    Trade runRule(FullDataPoint point);

    /**
     * Convert the rule to XML so that it may be persisted.
     * @return rule XML
     * @throws RuleException thrown
     */
    RuleXml toXml() throws RuleException;

    /**
     * Set the name of the rule.
     * @param name the name to use
     */
    void setName(String name);

    /**
     * Get the trade associated with this rule.
     * @return trade
     */
    Trade getTrade();
}
