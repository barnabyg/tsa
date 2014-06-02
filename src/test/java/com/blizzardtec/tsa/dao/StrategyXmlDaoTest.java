 /*************************************************************************
 *  2013 BHGAGILE
 *  All Rights Reserved.
 *
 * NOTICE:  All information contained herein is, and remains
 * the property of BHGAGILE.
 */
package com.blizzardtec.tsa.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.blizzardtec.tsa.TestBase;
import com.blizzardtec.tsa.model.StrategyXml;

/**
 * @author Barnaby Golden
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
public final class StrategyXmlDaoTest extends TestBase {

    /**
     *
     */
    private static final String STRATEGY_NAME2 = "strategy name2";
    /**
     *
     */
    private static final String STRATEGY_XML = "<somexml></somexml>";
    /**
     *
     */
    private static final String STRATEGY_NAME = "strategy name";
    /**
     * Injected DAO.
     */
    @Autowired
    private transient StrategyXmlDao dao;

    /**
     * Test create, update, retrieve, delete for Strategy.
     */
    @Test
    public void crudStrategyXmlTest() {

        final StrategyXml strategyXml = new StrategyXml();

        strategyXml.setName(STRATEGY_NAME);
        strategyXml.setXml(STRATEGY_XML);

        final long id = dao.create(strategyXml);

        final StrategyXml strategyXml2 = dao.retrieve(id);

        assertNotNull("Retrieve failed to return strategy", strategyXml2);

        assertEquals(
           "Strategy name did not match",
                   STRATEGY_NAME, strategyXml2.getName());
        assertEquals(
           "Strategy xml did not match",
                   STRATEGY_XML, strategyXml2.getXml());

        strategyXml2.setName(STRATEGY_NAME2);

        dao.update(strategyXml2);

        final StrategyXml strategyXml3 = dao.retrieve(id);

        assertEquals(
            "Strategy name did not match",
                    STRATEGY_NAME2, strategyXml3.getName());

        assertEquals(
            "Strategy xml did not match",
                    STRATEGY_XML, strategyXml3.getXml());

        dao.delete(strategyXml3);

        final StrategyXml strategyXml4 = dao.retrieve(id);

        assertNull("Delete failed to remove strategy", strategyXml4);
    }

    /**
     * Find by name test.
     */
    @Test
    public void findByNameTest() {

        final StrategyXml strategy = new StrategyXml();

        strategy.setName(STRATEGY_NAME);
        strategy.setXml(STRATEGY_XML);

        final long id = dao.create(strategy);

        final StrategyXml strategy2 = dao.findByName(STRATEGY_NAME);

        assertNotNull("Find by name did not find strategy", strategy2);

        final StrategyXml strategy3 = dao.retrieve(id);
        dao.delete(strategy3);
    }
}
