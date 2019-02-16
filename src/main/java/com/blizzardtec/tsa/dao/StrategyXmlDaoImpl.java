 /*************************************************************************
 *  2013 BHGAGILE
 *  All Rights Reserved.
 *
 * NOTICE:  All information contained herein is, and remains
 * the property of BHGAGILE.
 */
package com.blizzardtec.tsa.dao;

import java.util.List;

import com.blizzardtec.tsa.model.StrategyXml;

/**
 * @author Barnaby Golden
 *
 */
public final class StrategyXmlDaoImpl
        extends GenericDaoImpl<StrategyXml, Long>
                            implements StrategyXmlDao {

    /**
     * @param type class type
     */
    public StrategyXmlDaoImpl(final Class<StrategyXml> type) {
        super(type);
    }

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    public StrategyXml findByName(final String strategyName) {

        final List<StrategyXml> list =
             (List<StrategyXml>) getHibernateTemplate().find(
                "from " + StrategyXml.class.getName()
                   + " s where s.name = ?0", strategyName);

        StrategyXml strategy = null;

        if (list != null && !list.isEmpty()) {
            strategy = list.get(0);
        }

        return strategy;
    }

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    public List<String> findAllStrategyNames() {

        List<String> list;

        final String queryString = "select s.name from "
                            + StrategyXml.class.getName() + " s";

        list = (List<String>) getHibernateTemplate().find(queryString);

        return list;
    }
}
