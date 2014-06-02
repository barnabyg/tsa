 /*************************************************************************
 *  2013 BHGAGILE
 *  All Rights Reserved.
 *
 * NOTICE:  All information contained herein is, and remains
 * the property of BHGAGILE.
 */
package com.blizzardtec.tsa.data;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import com.blizzardtec.tsa.DelegateException;
import com.blizzardtec.tsa.model.DataPoint;

/**
 * @author Barnaby Golden
 *
 */
public final class YahooDataAdapter
                extends BaseAdapter implements DataAdapter {

    /**
     * Number of fields expected in CSV.
     */
    private static final int CSV_FIELDS = 7;
    /**
     * Expected datetime format.
     */
    public static final String DATETIME_FORMAT = "yyyy-MM-dd";

    /**
     * {@inheritDoc}
     */
    @Override
    public DataPoint parseLine(final String line)
                            throws DelegateException {

        final String[] fields = trimStringArray(line.split(","));

        // more sanity
        if (fields.length != CSV_FIELDS) {
            throw new DelegateException(
                 "Unexpected number of csv fields: " + line);
        }

        DataPoint dataPoint = null;

        // make sure this is not a header line,
        // if it is we ignore it and return a null
        if (!fields[0].equalsIgnoreCase("Date")) {

            dataPoint = new DataPoint();

            // Yahoo CSV line:
            // Date,Open,High,Low,Close,Volume,Adj Close
            // 2013-07-17,466.25,467.60,463.25,466.50,15835000,466.50
            // field 0 = date
            // field 1 = open
            // field 2 = high
            // field 3 = low
            // field 4 = close
            // field 5 = volume
            // field 6 = adj close

            dataPoint.setInstrument("DUMMY");

            Date date = null;
            try {

                date =
                 new SimpleDateFormat(
                         DATETIME_FORMAT, Locale.UK).parse(fields[0]);

            } catch (ParseException pe) {
                throw new DelegateException(pe);
            }

            dataPoint.setDate(date);

            Float buy = null;
            buy = Float.parseFloat(fields[1]);
            dataPoint.setBuy(buy);

            Float sell = null;
            sell = Float.parseFloat(fields[1]);
            dataPoint.setSell(sell);
        }

        return dataPoint;
    }
}
