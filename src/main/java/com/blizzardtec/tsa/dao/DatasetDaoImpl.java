 /*************************************************************************
 *  2013 BHGAGILE
 *  All Rights Reserved.
 *
 * NOTICE:  All information contained herein is, and remains
 * the property of BHGAGILE.
 */
package com.blizzardtec.tsa.dao;

import java.util.List;

import com.blizzardtec.tsa.model.Dataset;

/**
 * @author Barnaby Golden
 *
 */
public final class DatasetDaoImpl
        extends GenericDaoImpl<Dataset, Long> implements DatasetDao {

    /**
     * Constructor that initialises the class type.
     * @param type class type
     */
    public DatasetDaoImpl(final Class<Dataset> type) {
        super(type);
    }

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    @Override
    public Dataset findByDatasetName(final String datasetName) {

        Dataset dataset = null;

        final List<Dataset> list = (List<Dataset>) getHibernateTemplate().find(
                "from " + Dataset.class.getName()
                + " d where d.datasetName = ?", datasetName);

        if (!list.isEmpty()) {
            dataset = list.get(0);
        }

        return dataset;
    }

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<String> findAllDatasetNames() {

        List<String> list;

        // dataset names are on every dataset point
        // so we have to use distinct
        final String queryString =
               "select distinct d.datasetName from "
                           + Dataset.class.getName() + " d";

        list = (List<String>) getHibernateTemplate().find(queryString);

        return list;
    }
}
