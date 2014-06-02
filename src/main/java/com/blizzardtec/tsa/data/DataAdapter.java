 /*************************************************************************
 *  2013 BHGAGILE
 *  All Rights Reserved.
 *
 * NOTICE:  All information contained herein is, and remains
 * the property of BHGAGILE.
 */
package com.blizzardtec.tsa.data;

import com.blizzardtec.tsa.DelegateException;
import com.blizzardtec.tsa.model.DataPoint;

/**
 * @author Barnaby Golden
 *
 */
public interface DataAdapter {

    /**
     * Parse a string and break it down to fields
     * that are used to populate a data point.
     * @param line string to parse
     * @return populated data point
     * @throws DelegateException thrown
     */
    DataPoint parseLine(String line)
            throws DelegateException;
}
