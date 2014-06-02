 /*************************************************************************
 *  2013 BHGAGILE
 *  All Rights Reserved.
 *
 * NOTICE:  All information contained herein is, and remains
 * the property of BHGAGILE.
 */
package com.blizzardtec.tsa.indicator;

import static org.junit.Assert.fail;

import org.junit.Test;

import com.blizzardtec.tsa.TestBase;
import com.blizzardtec.tsa.indicator.Indicator.IndicatorType;

/**
 * @author Barnaby Golden
 *
 */
public final class IndicatorTypeTest extends TestBase {

    /**
     * Check that all indicator types are included in value or direction sets.
     */
    @Test
    public void checkTypesTest() {

        for (final IndicatorType indicatorType: IndicatorType.values()) {

            if (!Indicator.VALUE_INDICATORS.contains(indicatorType)
                   && !Indicator.DIRECTION_INDICATORS.contains(indicatorType)) {
                fail("Indicator not found: " + indicatorType.name());
            }
        }
    }
}
