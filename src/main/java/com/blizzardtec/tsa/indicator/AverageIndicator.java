 /*************************************************************************
 *  2013 BHGAGILE
 *  All Rights Reserved.
 *
 * NOTICE:  All information contained herein is, and remains
 * the property of BHGAGILE.
 */
package com.blizzardtec.tsa.indicator;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.blizzardtec.tsa.rules.Rule.OperationType;

/**
 * @author Barnaby Golden
 *
 */
public class AverageIndicator {

    /**
     * Period in days of the indicator average.
     */
    private final transient int period;

    /**
     * Map of instrument values within n-days of the last data point.
     */
    private final transient Map<Date, Float> map;

    /**
     * Keep a note of the last average so that we can
     * tell if the average is rising, falling or flat.
     */
    private transient float lastAverage;
    /**
     * Flag to indicate what the movement direction of the indicator
     * is: rising, falling or flat.
     */
    private transient OperationType movement;

    /**
     * Constructor initialises the map.
     * @param period number of days over which average calculated
     */
    public AverageIndicator(final int period) {
        this.period = period;
        map = new HashMap<Date, Float>();
    }

    /**
     * Get the movement direction.
     * @return movement direction
     */
    protected final OperationType getMovement() {
        return this.movement;
    }

    /**
     * Add a new data point.
     * @param date the date of the point
     * @param value of the indicator at the given point
     */
    protected final void addPoint(
                final Date date, final float value) {

        map.put(date, value);

        final Calendar cal = new GregorianCalendar();
        cal.setTime(date);

        // We are going back in time so we add a negative
        // number of days
        cal.add(Calendar.DAY_OF_YEAR, -1 * period);

        final Iterator<Date> it = map.keySet().iterator();

        // remove any dates older than n-days
        while (it.hasNext()) {
            final Date pointDate = it.next();

            if (pointDate.before(cal.getTime())) {
                it.remove();
            }
        }
    }

    /**
     * Find the average of the five day data.
     * @return n-day average
     */
    protected final float average() {

        float average = 0;
        float total = 0f;
        int count = 0;

        for (final Date date: map.keySet()) {
            total += map.get(date);
            count++;
        }

        if (count > 0) {
            average = total / (float) count;
        }

        if (this.lastAverage == 0) {
            this.movement = OperationType.FLAT;
        } else {
            if (average > lastAverage) {
                this.movement = OperationType.RISING;
            } else if (average < lastAverage) {
                this.movement = OperationType.FALLING;
            } else {
                this.movement = OperationType.FLAT;
            }
        }

        lastAverage = average;

        return average;
    }

    /**
     * Return the map of values.
     * @return map
     */
    protected final Map<Date, Float> getMap() {
        return this.map;
    }
}
