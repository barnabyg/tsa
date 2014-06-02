 /*************************************************************************
 *  2013 BHGAGILE
 *  All Rights Reserved.
 *
 * NOTICE:  All information contained herein is, and remains
 * the property of BHGAGILE.
 */
package com.blizzardtec.tsa.analysis;

import com.blizzardtec.tsa.data.FullDataset;
import com.blizzardtec.tsa.model.Resultset;
import com.blizzardtec.tsa.strategy.Strategy;
import com.blizzardtec.tsa.strategy.StrategyException;

/**
 * @author Barnaby Golden
 *
 */
public interface TradingAnalyser {

    /**
     * Run the analysis.
     * @param fullDataset dataset to use
     * @param strategy the strategy containing a list of trading rules
     * @param startingFunds the initial amount of funds available
     * @return result set
     * @throws StrategyException thrown
     */
    Resultset runAnalysis(
            FullDataset fullDataset,
            Strategy strategy,
            float startingFunds) throws StrategyException;
}
