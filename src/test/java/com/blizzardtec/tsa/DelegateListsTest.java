 /*************************************************************************
 *  2013 BHGAGILE
 *  All Rights Reserved.
 *
 * NOTICE:  All information contained herein is, and remains
 * the property of BHGAGILE.
 */
package com.blizzardtec.tsa;

import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.blizzardtec.tsa.BusinessDelegate.ObjectType;

/**
 * @author Barnaby Golden
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
public final class DelegateListsTest extends TestBase {

    /**
     * Instance of the delegate.
     */
    @Autowired
    private transient BusinessDelegate delegate;

    /**
     * Get a list of rule names.
     */
    @Test
    public void ruleListTest() {

        final List<String> list = delegate.listNames(ObjectType.RULE);

        assertNotNull("Null rule list returned", list);
    }

    /**
     * Get a list of dataset names.
     */
    @Test
    public void datasetListTest() {

        final List<String> list = delegate.listNames(ObjectType.DATASET);

        assertNotNull("Null dataset list returned", list);
    }

    /**
     * Get a list of strategy names.
     */
    @Test
    public void strategyListTest() {

        final List<String> list = delegate.listNames(ObjectType.STRATEGY);

        assertNotNull("Null strategy list returned", list);
    }

    /**
     * Get a list of result set names.
     */
    @Test
    public void resultsetListTest() {

        final List<String> list = delegate.listNames(ObjectType.RESULTSET);

        assertNotNull("Null result set list returned", list);
    }
}
