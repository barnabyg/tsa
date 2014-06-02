 /*************************************************************************
 *  2013 BHGAGILE
 *  All Rights Reserved.
 *
 * NOTICE:  All information contained herein is, and remains
 * the property of BHGAGILE.
 */
package com.blizzardtec.tsa.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.blizzardtec.tsa.TestBase;
import com.blizzardtec.tsa.data.StandardDataAdapter;
import com.blizzardtec.tsa.model.DataPoint;
import com.blizzardtec.tsa.model.Dataset;

/**
 * @author Barnaby Golden
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
public final class DatasetDaoTest extends TestBase {

    /**
     *
     */
    private static final float BUY_PRICE2 = 88.7f;
    /**
     *
     */
    private static final String DATE_TIME = "02/09/2013 21:15:03";
    /**
     *
     */
    private static final String INSTRUMENT = "BP.L";
    /**
     *
     */
    private static final String DATASET_NAME = "dataset name";
    /**
     *
     */
    private static final float SELL_PRICE = 43.9f;
    /**
     *
     */
    private static final float BUY_PRICE = 50.5f;
    /**
     * Injected DAO.
     */
    @Autowired
    private transient DatasetDao dao;

    /**
     * Create, retrieve, update, delete for dataset point entity.
     */
    @Test
    public void crudDataPointTest() {

        final DataPoint dPoint = dummyDataPoint();

        final List<DataPoint> list = new ArrayList<DataPoint>();

        list.add(dPoint);

        final Dataset dataset = new Dataset();
        dataset.setDataPoints(list);
        dataset.setDatasetName(DATASET_NAME);

        final long id = dao.create(dataset);

        final Dataset dataset2 = dao.retrieve(id);

        assertNotNull("Dataset retrieve failed", dataset2);

        final DataPoint dPoint2 = dataset2.getDataPoints().get(0);

        assertEquals(
            "Buy prices not equal", BUY_PRICE, dPoint2.getBuy(), 0.0);

        assertEquals(
            "Sell prices not equal", SELL_PRICE, dPoint2.getSell(), 0.0);

        assertEquals(
            "Dataset name not equal",
                    DATASET_NAME, dataset2.getDatasetName());

        assertEquals(
            "Instrument not equal",
                    INSTRUMENT, dPoint2.getInstrument());

        assertEquals(
            "Date not equal", dPoint.getDate(), dPoint2.getDate());

        final float midPrice = (BUY_PRICE + SELL_PRICE) / 2f;

        assertEquals("Invalid midprice", midPrice, dPoint2.midprice(), 0f);

        dPoint2.setBuy(BUY_PRICE2);

        dao.update(dataset2);

        final Dataset dataset3 = dao.retrieve(id);

        final DataPoint dPoint3 = dataset3.getDataPoints().get(0);

        assertEquals(
                "Buy prices not equal", BUY_PRICE2, dPoint3.getBuy(), 0.0);

        assertEquals(
                "Sell prices not equal", SELL_PRICE, dPoint3.getSell(), 0.0);

        assertEquals(
                "Dataset name not equal",
                        DATASET_NAME, dataset2.getDatasetName());

        assertEquals(
                "Instrument not equal",
                        INSTRUMENT, dPoint3.getInstrument());

        assertEquals(
                "Date not equal", dPoint.getDate(), dPoint3.getDate());

       dao.delete(dataset3);

       final Dataset dataset4 = dao.retrieve(id);

       assertNull("Delete did not remove dataset", dataset4);
    }

    /**
     * Create a dummy dataset point object using default values.
     * @return newly created object
     */
    private DataPoint dummyDataPoint() {

        final DataPoint dPoint = new DataPoint();

        dPoint.setBuy(BUY_PRICE);
        dPoint.setSell(SELL_PRICE);
        dPoint.setInstrument(INSTRUMENT);

        dPoint.setDate(
            makeDate(StandardDataAdapter.DATETIME_FORMAT, DATE_TIME));

        return dPoint;
    }

    /**
     * Test finding a dataset by its name.
     */
    @Test
    public void findByDatasetNameTest() {

        final DataPoint dPoint = dummyDataPoint();

        final List<DataPoint> list = new ArrayList<DataPoint>();

        list.add(dPoint);

        final Dataset dataset = new Dataset();
        dataset.setDataPoints(list);
        dataset.setDatasetName(DATASET_NAME);

        dao.create(dataset);

        final Dataset dataset2 = dao.findByDatasetName(DATASET_NAME);

        assertNotNull("Did not find dataset by name", dataset2);

        dao.delete(dataset2);
    }

    /**
     * Test retrieval of a list of dataset names.
     */
    @Test
    public void findAllDatasetNames() {

        final DataPoint dPoint = dummyDataPoint();

        final List<DataPoint> list = new ArrayList<DataPoint>();

        list.add(dPoint);

        final Dataset dataset = new Dataset();
        dataset.setDataPoints(list);
        dataset.setDatasetName(DATASET_NAME);

        final long id = dao.create(dataset);

        final List<String> listNames = dao.findAllDatasetNames();

        assertFalse("Did not find dataset by name", listNames.isEmpty());

        final Dataset dataset2 = dao.retrieve(id);

        dao.delete(dataset2);
    }
}
