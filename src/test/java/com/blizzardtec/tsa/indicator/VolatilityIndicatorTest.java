 /*************************************************************************
 *  2013 BHGAGILE
 *  All Rights Reserved.
 *
 * NOTICE:  All information contained herein is, and remains
 * the property of BHGAGILE.
 */
package com.blizzardtec.tsa.indicator;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.blizzardtec.tsa.TestBase;
import com.blizzardtec.tsa.data.FullDataPoint;
import com.blizzardtec.tsa.indicator.Indicator.IndicatorType;
import com.blizzardtec.tsa.model.DataPoint;
import com.blizzardtec.tsa.rules.Rule.OperationType;

/**
 * @author Barnaby Golden
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
public final class VolatilityIndicatorTest extends TestBase {

    /**
     * Data list of dates.
     */
    private static final String[] DATE_LIST = {
        "01-08-2012", "02-08-2012", "03-08-2012", "06-08-2012",
        "07-08-2012"};

    /**
     * Data list of values.
     */
    private static final float[] VAL_LIST = {
        1375.32f, 1365f, 1390.99f, 1394.23f,
        1401.35f};

    /**
     * Test volatility indicator generation.
     */
    @Test
    public void volatilityTest() {

        final Indicator indicator = new FiveDayVolatilityIndicator();

        final List<FullDataPoint> list = getFullData();

        indicator.enrich(list);

        final FullDataPoint firstPoint = list.get(0);

        final float firstVolatility = Float.valueOf(
                firstPoint.getIndicator(IndicatorType.FIVE_DAY_VOLATILITY));

        assertEquals("First volatility value was not zero",
                0, firstVolatility, 0);

        final OperationType firstType = OperationType.valueOf(
                firstPoint.getIndicator(
                        IndicatorType.FIVE_DAY_VOLATILITY_DIRECTION));

        assertEquals("first volatility direction was invalid",
                OperationType.FLAT, firstType);

        final FullDataPoint lastPoint = list.get(VAL_LIST.length - 1);

        final float lastVolatility = Float.valueOf(
                lastPoint.getIndicator(IndicatorType.FIVE_DAY_VOLATILITY));

        assertEquals("Invalid last volatility value",
                0.01096, lastVolatility, 0.0001);

        final OperationType lastType = OperationType.valueOf(
                lastPoint.getIndicator(
                        IndicatorType.FIVE_DAY_VOLATILITY_DIRECTION));

        assertEquals("Last volatility direction was invalid",
                OperationType.FALLING, lastType);
    }

    /**
     * Populate some data points.
     * @return list of full data points
     */
    private List<FullDataPoint> getFullData() {

        final List<FullDataPoint> list = new ArrayList<FullDataPoint>();

        DataPoint point = null;
        FullDataPoint fPoint = null;

        for (int i = 0; i < VAL_LIST.length; i++) {

            point = new DataPoint();

            point.setBuy(VAL_LIST[i]);
            point.setSell(VAL_LIST[i]);
            point.setDate(this.makeDate("dd-MM-yyyy", DATE_LIST[i]));
            point.setInstrument(INSTRUMENT);

            fPoint = new FullDataPoint(point);

            list.add(fPoint);
        }

        return list;
    }
}
