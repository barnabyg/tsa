 /*************************************************************************
 *  2013 BHGAGILE
 *  All Rights Reserved.
 *
 * NOTICE:  All information contained herein is, and remains
 * the property of BHGAGILE.
 */
package com.blizzardtec.tsa.dao;

import java.util.List;

import com.blizzardtec.tsa.model.RuleXml;

/**
 * @author Barnaby Golden
 *
 */
public interface RuleXmlDao extends GenericDao<RuleXml, Long>  {

    /**
     * Find a rule by its unique name.
     * @param ruleName name
     * @return the matching rule
     */
    RuleXml findByName(String ruleName);

    /**
     * Return a list of rule names.
     * @return list of rule names
     */
    List<String> findAllRuleNames();
}
