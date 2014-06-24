/*************************************************************************
 *  2013 BHGAGILE
 *  All Rights Reserved.
 *
 * NOTICE:  All information contained herein is, and remains
 * the property of BHGAGILE.
 */
package com.blizzardtec.tsa;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import static org.junit.Assert.fail;

import org.springframework.test.context.ContextConfiguration;

import com.blizzardtec.tsa.model.DataPoint;

/**
 * @author Barnaby Golden
 *
 */
@ContextConfiguration(locations = { "test-context.xml" })
public class TestBase {

    /**
     * Test instrument name.
     */
    public static final String INSTRUMENT = "BP.L";
    /**
     * URL of the Selenium grid.
     */
    public static final String GRID_URL
            = "http://grid.bhgagile.com:4444/wd/hub";
    /**
     * Base URL.
     */
    public static final String BASE_URL = "http://localhost";
    /**
     * Port the application will run on.
     */
    public static final String APP_PORT = "7070";
    /**
     * Test dataset containing CSV data.
     */
    public static final String DATASET_FILE = "src/test/resources/test.csv";

    /**
     * Empty file.
     */
    public static final String EMPTY_DATASET_FILE
            = "src/test/resources/empty.csv";

    /**
     * Badly formated data file.
     */
    public static final String INVALID_DATASET_FILE
            = "src/test/resources/invalid.csv";

    /**
     * Dataset taken from Yahoo Finance website.
     */
    public static final String YAHOO_DATASET_FILE
            = "src/test/resources/yahoo.csv";

    /**
     * Test strategy file containing strategy XML.
     */
    public static final String STRATEGY_FILE
            = "src/test/resources/test-strategy.xml";

    /**
     * Empty strategy file.
     */
    public static final String EMPTY_STRATEGY_FILE
            = "src/test/resources/empty.xml";

    /**
     * A strategy file containing invalid XML.
     */
    public static final String INVALID_STRATEGY_FILE
            = "src/test/resources/invalid.xml";

    /**
     * Test strategy file containing a rising strategy XML.
     */
    public static final String RISING_STRATEGY_FILE
            = "src/test/resources/rising-strategy.xml";

    /**
     * Test strategy file containing a falling strategy XML.
     */
    public static final String FALLING_STRATEGY_FILE
            = "src/test/resources/falling-strategy.xml";

    /**
     * Name for a strategy.
     */
    public static final String STRATEGY_NAME = "strategy1";

    /**
     * Name for a rising strategy.
     */
    public static final String RISING_STRATEGY_NAME = "risingstrategy";

    /**
     * Name for a falling strategy.
     */
    public static final String FALLING_STRATEGY_NAME = "fallingstrategy";

    /**
     * Some mock strategy XML.
     */
    public static final String STRATEGY_XML = "<some><xml></xml></some>";

    /**
     * Dataset name.
     */
    public static final String DATASET_NAME = "dataset1";

    /**
     * Starting funds.
     */
    public static final float STARTING_FUNDS = 50000f;

    /**
     * Delay in ms.
     */
    public static final int DELAY = 200;
    /**
     * Timeout.
     */
    public static final int TIMEOUT = 240;
    /**
     * Time to wait for a chart to load in CI profile. Necessary due to loading
     * in an iFrame.
     */
    public static final int CHART_TIMEOUT_CI = 40000;
    /**
     * Time to wait for a chart to load in local profile. Necessary due to
     * loading in an iFrame.
     */
    public static final int CHART_TIMEOUT_LOCAL = 500;
    /**
     * Standard data type.
     */
    public static final String STD_DATA_TYPE = "STANDARD_CSV";
    /**
     * Standard data type.
     */
    public static final String YAHOO_DATA_TYPE = "YAHOO_CSV";

    /**
     * Rule name.
     */
    public static final String RULE_NAME = "test rule";

    /**
     * Rule value.
     */
    public static final float RULE_VALUE = 24.5f;

    /**
     * Quantity.
     */
    public static final int QUANTITY = 2;

    /**
     * Build the application URL.
     *
     * @return URL
     */
    public static String getUrl() {
        boolean useGrid = false;

        String retVal = BASE_URL + ":" + APP_PORT + "/";

        if (useGrid) {
            retVal = GRID_URL;
        }

        return retVal;
    }

    /**
     * Create a date from a string and given date format. Fails on any parse
     * errors.
     *
     * @param dateFormat
     *            the date format to use
     * @param dateStr
     *            a date in string format
     * @return new date object
     */
    protected final Date makeDate(
            final String dateFormat, final String dateStr) {

        Date date = null;

        try {
            date = new SimpleDateFormat(dateFormat, Locale.UK).parse(dateStr);
        } catch (ParseException e) {
            fail(e.getMessage());
        }

        return date;
    }

    /**
     * Create a data point for a given date.
     *
     * @param dateStr
     *            date to use
     * @param dateFormat
     *            format of the date
     * @param value
     *            the buy/sell value
     * @return data point
     */
    protected final DataPoint makePoint(final String dateStr,
            final String dateFormat, final float value) {

        final DataPoint dPoint = new DataPoint();

        dPoint.setBuy(value);
        dPoint.setSell(value);
        dPoint.setInstrument(INSTRUMENT);
        dPoint.setDate(makeDate(dateFormat, dateStr));

        return dPoint;
    }
}
