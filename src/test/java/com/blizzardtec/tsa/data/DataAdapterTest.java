 /*************************************************************************
 *  2013 BHGAGILE
 *  All Rights Reserved.
 *
 * NOTICE:  All information contained herein is, and remains
 * the property of BHGAGILE.
 */
package com.blizzardtec.tsa.data;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Test;

import com.blizzardtec.tsa.DelegateException;
import com.blizzardtec.tsa.TestBase;
import com.blizzardtec.tsa.model.DataPoint;

/**
 * @author Barnaby Golden
 *
 */
public final class DataAdapterTest extends TestBase {

    /**
     * Open price.
     */
    private static final double OPEN_PRICE = 466.25;
    /**
     * Test a line from a Yahoo CSV.
     */
    private static final String YAHOO_LINE =
           "2013-07-17,466.25,467.60,463.25,466.50,15835000,466.50";
    /**
     * Expected date-time format.
     */
    public static final String DATETIME_FORMAT = "yyyy-MM-dd";

    /**
     * Test the Yahoo data adapter.
     */
    @Test
    public void yahooDataAdapterTest() {

        final DataAdapter adapter = new YahooDataAdapter();

        DataPoint point = null;

        try {
            point =
              adapter.parseLine(YAHOO_LINE);
        } catch (DelegateException e) {
            fail(e.getMessage());
        }

        assertEquals(
           "Open price does not match buy", OPEN_PRICE, point.getBuy(), 0);
        assertEquals(
           "Open price does not match sell", OPEN_PRICE, point.getSell(), 0);

        assertEquals("Dates do not match",
                    makeDate(DATETIME_FORMAT, "2013-07-17"),
                    point.getDate());
    }
}
