 /*************************************************************************
 *  2013 BHGAGILE
 *  All Rights Reserved.
 *
 * NOTICE:  All information contained herein is, and remains
 * the property of BHGAGILE.
 */
package com.blizzardtec.tsa.rules;

import java.util.List;

import com.blizzardtec.tsa.model.RuleXml;

/**
 * @author Barnaby Golden
 *
 */
public interface RuleHelper {

    /**
     * Persist a rule.
     * @param ruleXml rule XML
     */
    void persistRule(RuleXml ruleXml);

    /**
     * Return a list of all rule names.
     * @return list of rule names
     */
    List<String> listRuleNames();

    /**
     * Delete a rule by its rule name.
     * @param name name of the rule
     */
    void deleteRule(String name);

    /**
     * Return a rule for a given name.
     * @param name the name to search on
     * @return rule
     */
    RuleXml getRule(String name);
}
