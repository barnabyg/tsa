 /*************************************************************************
 *  2013 BHGAGILE
 *  All Rights Reserved.
 *
 * NOTICE:  All information contained herein is, and remains
 * the property of BHGAGILE.
 */
package com.blizzardtec.tsa.strategy;

import java.util.List;

import com.blizzardtec.tsa.model.StrategyXml;

/**
 * @author Barnaby Golden
 *
 */
public interface StrategyHelper {

    /**
     * Persist a strategy from a raw XML string.
     * @param strategyXml strategy
     */
    void persistStrategy(StrategyXml strategyXml);

    /**
     * Validate strategy XML.
     * @param xmlStrategy string containing the XML
     * @return true if valid, else false
     * @throws StrategyException thrown on error
     */
    boolean validateStrategyXml(String xmlStrategy)
                                throws StrategyException;

    /**
     * Return the names of loaded strategies.
     * @return list of strategy names
     */
    List<String> listStrategyNames();

    /**
     * Delete a strategy.
     * @param name the name of the strategy to delete
     */
    void deleteStrategy(String name);

    /**
     * Get the strategy with the given name.
     * @param name the strategy name to find
     * @return the strategy or null if not found
     */
    StrategyXml getStrategy(String name);
}
