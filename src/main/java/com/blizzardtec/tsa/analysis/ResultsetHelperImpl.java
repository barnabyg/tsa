 /*************************************************************************
 *  2013 BHGAGILE
 *  All Rights Reserved.
 *
 * NOTICE:  All information contained herein is, and remains
 * the property of BHGAGILE.
 */
package com.blizzardtec.tsa.analysis;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.blizzardtec.tsa.dao.ResultsetDao;
import com.blizzardtec.tsa.indicator.IndicatorException;
import com.blizzardtec.tsa.indicator.IndicatorHelper;
import com.blizzardtec.tsa.model.Dataset;
import com.blizzardtec.tsa.model.Resultset;
import com.blizzardtec.tsa.model.StrategyXml;
import com.blizzardtec.tsa.strategy.Strategy;
import com.blizzardtec.tsa.strategy.StrategyException;

/**
 * @author Barnaby Golden
 *
 */
public final class ResultsetHelperImpl implements ResultsetHelper {

    /**
     * Injected trading analyser.
     */
    @Autowired
    private transient TradingAnalyser tradingAnalyser;
    /**
     * Result set DAO.
     */
    @Autowired
    private transient ResultsetDao resultsetDao;
    /**
     * Injected indicator helper.
     * Used to enrich the data.
     */
    @Autowired
    private transient IndicatorHelper indicatorHelper;

    /**
     * {@inheritDoc}
     */
    public void runAnalysis(
         final String name,
         final Dataset dataset,
         final StrategyXml strategyXml,
         final float startingFunds) throws StrategyException {

        final Strategy strategy = strategyXml.toStrategy();

        Resultset resultset = null;

        try {

            resultset = tradingAnalyser.runAnalysis(
               indicatorHelper.computeIndicators(
                       dataset), strategy, startingFunds);

        } catch (IndicatorException e) {
            throw new StrategyException(e);
        }

        resultset.setName(name);

        resultsetDao.create(resultset);
    }

    /**
     * {@inheritDoc}
     */
    public Resultset getResultset(final String runName) {

        return resultsetDao.findByName(runName);
    }

    /**
     * {@inheritDoc}
     */
    public List<String> listResultsets() {

        return resultsetDao.findAllRuns();
    }

    /**
     * {@inheritDoc}
     */
    public void deleteResultset(final String name) {

        final Resultset resultset = getResultset(name);

        resultsetDao.delete(resultset);
    }
}
