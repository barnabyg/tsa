 /*************************************************************************
 *  2013 BHGAGILE
 *  All Rights Reserved.
 *
 * NOTICE:  All information contained herein is, and remains
 * the property of BHGAGILE.
 */
package com.blizzardtec.tsa.dao;

import java.util.List;

import com.blizzardtec.tsa.model.Resultset;

/**
 * @author Barnaby Golden
 *
 */
public interface ResultsetDao extends GenericDao<Resultset, Long> {

    /**
     * Return a list of all run id.
     * @return list of run id
     */
    List<String> findAllRuns();

    /**
     * Find a result set by its name.
     * @param name the name to use
     * @return a result set
     */
    Resultset findByName(String name);
}
