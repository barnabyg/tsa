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
public final class StandardDataAdapter
            extends BaseAdapter implements DataAdapter {

    /**
     * Number of fields expected in CSV.
     */
    private static final int CSV_FIELDS = 4;
    /**
     * Expected datetime format.
     */
    public static final String DATETIME_FORMAT = "dd/MM/yyyy HH:mm:ss";

    /**
     * {@inheritDoc}
     */
    public DataPoint parseLine(final String line) throws DelegateException {

        final String[] fields = trimStringArray(line.split(","));

        // more sanity
        if (fields.length != CSV_FIELDS) {
            throw new DelegateException(
                 "Unexpected number of csv fields: " + line);
        }

        final DataPoint dataPoint = new DataPoint();

        // Example CSV line:
        // "BP.L,02/05/2013 11:15:03,99.1,100.3"
        // field 0 = Instrument name
        // field 1 = date-time
        // field 2 = buy price
        // field 3 = sell price

        dataPoint.setInstrument(fields[0]);

        Date date = null;
        try {
            date =
             new SimpleDateFormat(
                     DATETIME_FORMAT, Locale.UK).parse(fields[1]);
        } catch (ParseException pe) {
            throw new DelegateException(pe);
        }

        dataPoint.setDate(date);

        Float buy = null;
        buy = Float.parseFloat(fields[2]);
        dataPoint.setBuy(buy);

        Float sell = null;
        sell = Float.parseFloat(fields[3]);
        dataPoint.setSell(sell);

        return dataPoint;
    }

}
