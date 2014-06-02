 /*************************************************************************
 *  2013 BHGAGILE
 *  All Rights Reserved.
 *
 * NOTICE:  All information contained herein is, and remains
 * the property of BHGAGILE.
 */
package com.blizzardtec.tsa.indicator;

import com.blizzardtec.tsa.data.FullDataset;
import com.blizzardtec.tsa.model.Dataset;

/**
 * @author Barnaby Golden
 *
 */
public interface IndicatorHelper {

    /**
     * Enrich a list of data points with indicator values.
     * @param dataset list of simple data points
     * @return list of full data points
     * @throws IndicatorException thrown
     */
    FullDataset computeIndicators(Dataset dataset)
                                            throws IndicatorException;
}
