 /*************************************************************************
 *  2013 BHGAGILE
 *  All Rights Reserved.
 *
 * NOTICE:  All information contained herein is, and remains
 * the property of BHGAGILE.
 */
package com.blizzardtec.tsa.analysis;

import java.util.List;

import com.blizzardtec.tsa.model.Dataset;
import com.blizzardtec.tsa.model.Resultset;
import com.blizzardtec.tsa.model.StrategyXml;
import com.blizzardtec.tsa.strategy.StrategyException;

/**
 * @author Barnaby Golden
 *
 */
public interface ResultsetHelper {

    /**
     * Run analysis.
     * @param name the name to give the results for this analysis
     * @param dataset dataset to use for analysis
     * @param strategy strategy to test
     * @param startingFunds the initial amount of money available
     * @throws StrategyException thrown
     */
    void runAnalysis(
            String name, Dataset dataset,
            StrategyXml strategy,
            float startingFunds) throws StrategyException;

    /**
     * Get the result set for a given ID.
     * @param runName result set name
     * @return result set
     */
    Resultset getResultset(String runName);

    /**
     * Get a list of all run id.
     * @return list of run id
     */
    List<String> listResultsets();

    /**
     * Delete a resultset.
     * @param name the name of the result set to delete
     */
    void deleteResultset(String name);
}
