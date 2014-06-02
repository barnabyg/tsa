 /*************************************************************************
 *  2013 BHGAGILE
 *  All Rights Reserved.
 *
 * NOTICE:  All information contained herein is, and remains
 * the property of BHGAGILE.
 */
package com.blizzardtec.tsa.visualisation;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.jfree.chart.JFreeChart;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.blizzardtec.tsa.BusinessDelegate;
import com.blizzardtec.tsa.BusinessDelegate.ObjectType;
import com.blizzardtec.tsa.DelegateException;
import com.blizzardtec.tsa.TestBase;
import com.blizzardtec.tsa.data.DatasetHelper.DataType;
import com.blizzardtec.tsa.visualisation.VisualisationHelper.ChartType;

/**
 * @author Barnaby Golden
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
public final class VisualisationHelperTest extends TestBase {

    /**
     * Instance of the delegate.
     */
    @Autowired
    private transient BusinessDelegate delegate;

    /**
     * Visualisation helper.
     */
    @Autowired
    private transient VisualisationHelper helper;

    /**
     * Show a chart.
     */
    @Test
    public void showChartTest() {

        JFreeChart chart = null;

        // Dataset
        File file = new File(DATASET_FILE);

        try {
            delegate.saveDataSet(
               new FileInputStream(file),
               DATASET_NAME, DataType.STANDARD_CSV);
        } catch (DelegateException de) {
            fail(de.getMessage());
        } catch (FileNotFoundException fe) {
            fail(fe.getMessage());
        }

        // Strategy
        file = new File(STRATEGY_FILE);

        try {
            delegate.saveStrategy(
                 new FileInputStream(file), STRATEGY_NAME);
        } catch (DelegateException de) {
            fail(de.getMessage());
        } catch (FileNotFoundException fe) {
            fail(fe.getMessage());
        }

        // Run the analysis
        String resultsetName = null;

        try {
            resultsetName = delegate.runAnalysis(
                    DATASET_NAME,
                    STRATEGY_NAME,
                    STARTING_FUNDS);
        } catch (DelegateException de) {
            fail(de.getMessage());
        }

        try {
            chart = helper.showChart(ChartType.CASH_BALANCE_XY, resultsetName);
        } catch (IOException e) {
            fail(e.getMessage());
        }

        assertNotNull("Chart not returned", chart);

        try {
            chart = helper.showChart(ChartType.NORMALISED_XY, resultsetName);
        } catch (IOException e) {
            fail(e.getMessage());
        }

        assertNotNull("Chart not returned", chart);

        // tidy up
        delegate.delete(DATASET_NAME, ObjectType.DATASET);
        delegate.delete(STRATEGY_NAME, ObjectType.STRATEGY);
        delegate.delete(resultsetName, ObjectType.RESULTSET);
    }
}
