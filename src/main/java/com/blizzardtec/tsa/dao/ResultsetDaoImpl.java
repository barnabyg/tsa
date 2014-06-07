 /*************************************************************************
 *  2013 BHGAGILE
 *  All Rights Reserved.
 *
 * NOTICE:  All information contained herein is, and remains
 * the property of BHGAGILE.
 */
package com.blizzardtec.tsa.dao;


import java.util.List;

import com.blizzardtec.tsa.model.Resultset;

/**
 * @author Barnaby Golden
 *
 */
public final class ResultsetDaoImpl
        extends GenericDaoImpl<Resultset, Long>  implements ResultsetDao {

    /**
     * Constructor that sets the class type.
     * @param type param
     */
    public ResultsetDaoImpl(final Class<Resultset> type) {
        super(type);
    }

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<String> findAllRuns() {

        List<String> list;

        final String queryString = "select r.name from "
                            + Resultset.class.getName() + " r";

        list = (List<String>) getHibernateTemplate().find(queryString);

        return list;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Resultset findByName(final String name) {

        return (Resultset) getHibernateTemplate().find(
                "from " + Resultset.class.getName()
                + " r where r.name = ?", name).get(0);
    }
}
