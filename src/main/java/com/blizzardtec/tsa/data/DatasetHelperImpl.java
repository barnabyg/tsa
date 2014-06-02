 /*************************************************************************
 *  2013 BHGAGILE
 *  All Rights Reserved.
 *
 * NOTICE:  All information contained herein is, and remains
 * the property of BHGAGILE.
 */
package com.blizzardtec.tsa.data;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.blizzardtec.tsa.DelegateException;
import com.blizzardtec.tsa.dao.DatasetDao;
import com.blizzardtec.tsa.model.DataPoint;
import com.blizzardtec.tsa.model.Dataset;

/**
 * @author Barnaby Golden
 *
 */
public final class DatasetHelperImpl implements DatasetHelper {

    /**
     * Dataset DAO.
     */
    @Autowired
    private transient DatasetDao datasetDao;

    /**
     * {@inheritDoc}
     */
    public Dataset parseData(
            final List<String> lines, final String name, final DataType type)
                                              throws DelegateException {

        final List<DataPoint> list = new ArrayList<DataPoint>();

        for (final String str: lines) {
            final DataPoint dPoint = parseLine(str, type);

            if (dPoint != null) {
                list.add(dPoint);
            }
        }

        final Dataset dataset = new Dataset();

        dataset.setDataPoints(list);
        dataset.setDatasetName(name);

        return dataset;
     }

    /**
     * Parse a line from the CSV file in to a DataPoint object.
     * @param line input string
     * @param type data type
     * @return populated <code>DataPoint</code> object
     * @throws DelegateException thrown on any kind of parsing or format error
     */
    private DataPoint parseLine(final String line, final DataType type)
                                throws DelegateException {

        // sanity checks
        if (line == null || line.isEmpty()) {
            throw new DelegateException("Invalid dataset point data line");
        }

        DataAdapter dataAdapter = null;

        switch (type) {
        case STANDARD_CSV:
            dataAdapter = new StandardDataAdapter();
            break;
        case YAHOO_CSV:
            dataAdapter = new YahooDataAdapter();
            break;
        default:
            throw new DelegateException("Unrecognised data type");
        }

        return dataAdapter.parseLine(line);
    }

    /**
     *
     * {@inheritDoc}
     */
    public void persistDataset(final Dataset dataset) {

            datasetDao.create(dataset);
    }

    /**
     * {@inheritDoc}
     */
    public List<String> listDatasetNames() {

        return datasetDao.findAllDatasetNames();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deleteDataset(final String name) {

        final Dataset dataset = datasetDao.findByDatasetName(name);

        datasetDao.delete(dataset);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean datasetExists(final String name) {

        final Dataset dataset = datasetDao.findByDatasetName(name);

        boolean retVal;

        if (dataset == null) {
            retVal = false;
        } else {
            retVal = true;
        }

        return retVal;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Dataset getDataset(final String name) {

        return datasetDao.findByDatasetName(name);
    }
}
