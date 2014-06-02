 /*************************************************************************
 *  2013 BHGAGILE
 *  All Rights Reserved.
 *
 * NOTICE:  All information contained herein is, and remains
 * the property of BHGAGILE.
 */
package com.blizzardtec.tsa.data;

import java.util.List;

import com.blizzardtec.tsa.DelegateException;
import com.blizzardtec.tsa.model.Dataset;

/**
 * @author Barnaby Golden
 *
 */
public interface DatasetHelper {

    /**
     * Types of data that can be parsed.
     * @author Barnaby Golden
     *
     */
    public enum DataType { STANDARD_CSV, YAHOO_CSV }

    /**
     * Parse a list of strings and turn them in to a dataset.
     * @param lines list of lines to parse
     * @param name dataset name
     * @param type the type of data
     * @return dataset
     * @throws DelegateException thrown
     */
    Dataset parseData(List<String> lines, String name, DataType type)
                                            throws DelegateException;

    /**
     * Persist a list of data points using the preferred
     * persistence layer.
     * @param dataset the dataset to persist
     */
    void persistDataset(Dataset dataset);

    /**
     * Return a list of all dataset names available.
     * @return list of dataset names
     */
    List<String> listDatasetNames();

    /**
     * Delete a dataset.
     *
     * @param name the name of the dataset to delete
     */
    void deleteDataset(String name);

    /**
     * Check to see if a dataset already exists.
     * @param name the name of the dataset to check
     * @return true if it already exists
     */
    boolean datasetExists(String name);

    /**
     * Get the dataset for a given dataset name.
     * @param name the name of the dataset
     * @return dataset
     */
    Dataset getDataset(String name);
}
