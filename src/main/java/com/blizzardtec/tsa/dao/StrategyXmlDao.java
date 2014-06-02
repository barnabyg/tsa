 /*************************************************************************
 *  2013 BHGAGILE
 *  All Rights Reserved.
 *
 * NOTICE:  All information contained herein is, and remains
 * the property of BHGAGILE.
 */
package com.blizzardtec.tsa.dao;

import java.util.List;

import com.blizzardtec.tsa.model.StrategyXml;

/**
 * @author Barnaby Golden
 *
 */
public interface StrategyXmlDao extends GenericDao<StrategyXml, Long> {

    /**
     * Find a strategy by its unique name.
     * @param strategyName name
     * @return the matching strategy
     */
    StrategyXml findByName(String strategyName);

    /**
     * Return a list of strategy names.
     * @return list of strategy names
     */
    List<String> findAllStrategyNames();
}
