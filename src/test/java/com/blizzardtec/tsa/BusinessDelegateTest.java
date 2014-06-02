/**
 *
 */
package com.blizzardtec.tsa;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;

import com.blizzardtec.tsa.BusinessDelegate.ObjectType;
import com.blizzardtec.tsa.data.DatasetHelper.DataType;

/**
 * @author Barnaby Golden
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
public final class BusinessDelegateTest extends TestBase {

    /**
     * Instance of the delegate.
     */
    @Autowired
    private transient BusinessDelegate delegate;

    /**
     * Save data set test.
     */
    @Test
    public void saveDatasetTest() {

        saveDataset(DATASET_NAME, DATASET_FILE);

        final List<String> list = delegate.listNames(ObjectType.DATASET);

        assertNotNull("Did not return list of dataset names", list);
        assertTrue("Dataset name not returned", list.contains(DATASET_NAME));

        // tidy up
        delegate.delete(DATASET_NAME, ObjectType.DATASET);
    }

    /**
     * Save a dataset.
     * @param name the dataset name
     * @param filename the file containing the dataset
     */
    private void saveDataset(
            final String name, final String filename) {

        final File file = new File(filename);

        try {
            delegate.saveDataSet(
               new FileInputStream(file), name, DataType.STANDARD_CSV);
        } catch (DelegateException de) {
            fail(de.getMessage());
        } catch (FileNotFoundException fe) {
            fail(fe.getMessage());
        }
    }

    /**
     * Save trading strategy test.
     */
    @Test
    public void saveStrategyTest() {

        saveStrategy(STRATEGY_NAME, STRATEGY_FILE);

        final List<String> list = delegate.listNames(ObjectType.STRATEGY);

        assertNotNull("Did not return list of strategy names", list);

        assertTrue("Strategy name not returned", list.contains(STRATEGY_NAME));

        // tidy up
        delegate.delete(STRATEGY_NAME, ObjectType.STRATEGY);
    }

    /**
     * Save a strategy.
     * @param name the name for the strategy
     * @param filename the file containing the strategy
     */
    private void saveStrategy(
            final String name, final String filename) {

        final File file = new File(filename);

        try {
            delegate.saveStrategy(
                 new FileInputStream(file), name);
        } catch (DelegateException de) {
            fail(de.getMessage());
        } catch (FileNotFoundException fe) {
            fail(fe.getMessage());
        }

    }
    /**
     * Test analysis run.
     */
    @Test
    public void runAnalysisTest() {
        saveDataset(DATASET_NAME, DATASET_FILE);
        saveStrategy(STRATEGY_NAME, STRATEGY_FILE);

        String resultsetName = null;

        try {
            resultsetName = delegate.runAnalysis(
                    DATASET_NAME,
                    STRATEGY_NAME,
                    STARTING_FUNDS);
        } catch (DelegateException de) {
            fail(de.getMessage());
        }

        // tidy up
        delegate.delete(DATASET_NAME, ObjectType.DATASET);
        delegate.delete(STRATEGY_NAME, ObjectType.STRATEGY);
        delegate.delete(resultsetName, ObjectType.RESULTSET);
    }

    /**
     * Test the error handling for an empty data set file.
     * @throws DelegateException should be thrown
     */
    @Test (expected = DelegateException.class)
    public void emptyDatasetFileTest() throws DelegateException {

        final File file = new File(EMPTY_DATASET_FILE);

        try {
            delegate.saveDataSet(
                    new FileInputStream(file),
                    DATASET_NAME,
                    DataType.STANDARD_CSV);
        } catch (FileNotFoundException e) {
            fail(e.getMessage());
        }
    }

    /**
     * Test the handling of a badly formated dataset file.
     * @throws DelegateException should be thrown
     */
    @Test (expected = DelegateException.class)
    public void invalidDatasetFileTest() throws DelegateException {

        final File file = new File(INVALID_DATASET_FILE);

        try {
            delegate.saveDataSet(
                    new FileInputStream(file),
                    DATASET_NAME,
                    DataType.STANDARD_CSV);
        } catch (FileNotFoundException e) {
            fail(e.getMessage());
        }
    }

    /**
     * Test the handling of an empty strategy file.
     * @throws DelegateException should be thrown
     */
    @Test (expected = DelegateException.class)
    public void emptyStrategyFileTest() throws DelegateException {

        final File file = new File(EMPTY_STRATEGY_FILE);

        try {
            delegate.saveStrategy(
                    new FileInputStream(file),
                    STRATEGY_NAME);
        } catch (FileNotFoundException e) {
            fail(e.getMessage());
        }
    }

    /**
     * Test the handling of an invalid strategy file.
     * @throws DelegateException should be thrown
     */
    @Test (expected = DelegateException.class)
    public void invalidStrategyFileTest() throws DelegateException {

        final File file = new File(INVALID_STRATEGY_FILE);

        try {
            delegate.saveStrategy(
                    new FileInputStream(file),
                    STRATEGY_NAME);
        } catch (FileNotFoundException e) {
            fail(e.getMessage());
        }
    }
}
