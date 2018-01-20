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
public final class RuleXmlDaoImpl
            extends GenericDaoImpl<RuleXml, Long> implements RuleXmlDao {

    /**
     * @param type class type
     */
    public RuleXmlDaoImpl(final Class<RuleXml> type) {
        super(type);
    }

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    public RuleXml findByName(final String ruleName) {

        final List<RuleXml> list = (List<RuleXml>) getHibernateTemplate().find(
                "from " + RuleXml.class.getName()
                   + " r where r.name = ?", ruleName);

        RuleXml ruleXml = null;

        if (list != null && !list.isEmpty()) {
            ruleXml = list.get(0);
        }

        return ruleXml;
    }

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    public List<String> findAllRuleNames() {

        List<String> list;

        final String queryString = "select r.name from "
                            + RuleXml.class.getName() + " r";

        list = (List<String>) getHibernateTemplate().find(queryString);

        return list;
    }
}
