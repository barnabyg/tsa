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
public interface DatasetDao extends GenericDao<Dataset, Long> {

    /**
     * Get all the data set points for a given data set.
     * @param datasetName the unique name of the data set.
     * @return Dataset
     */
    Dataset findByDatasetName(String datasetName);

    /**
     * Return a list of all stored dataset names.
     * @return list of dataset names
     */
    List<String> findAllDatasetNames();
}
