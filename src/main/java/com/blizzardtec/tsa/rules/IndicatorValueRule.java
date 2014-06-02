 /*************************************************************************
 *  2013 BHGAGILE
 *  All Rights Reserved.
 *
 * NOTICE:  All information contained herein is, and remains
 * the property of BHGAGILE.
 */
package com.blizzardtec.tsa.rules;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.blizzardtec.tsa.data.FullDataPoint;
import com.blizzardtec.tsa.indicator.Indicator.IndicatorType;
import com.blizzardtec.tsa.model.Trade;

/**
 * @author Barnaby Golden
 *
 */
public final class IndicatorValueRule
            extends AbstractIndicatorRule implements Rule {

    /**
     * Precision to apply to floating point equals.
     */
    private static final double PRECISION = 0.001;
    /**
     * Value which the operation will be applied to.
     */
    private final transient float value;

    /**
     * Constructor requires the indicator details.
     * @param indicatorType the type of indicator
     * @param operationType the type of boolean operation to perform
     * @param value the value to apply the operation against
     * @param trade the trade to perform if the operation is true
     */
    public IndicatorValueRule(
            final IndicatorType indicatorType,
            final OperationType operationType,
            final float value,
            final Trade trade) {

        super(indicatorType, operationType, trade);
        this.value = value;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Trade runRule(final FullDataPoint point) {

        Trade ruleTrade = null;

        final float indicatorVal =
                Float.valueOf(point.getIndicator(getIndicatorType()));

        if (getOperationType() == OperationType.EQUAL
                && Math.abs(indicatorVal - value) < PRECISION) {

            ruleTrade = makeTrade(point);
        }

        if (getOperationType() == OperationType.GREATER
                && indicatorVal > value) {

            ruleTrade = makeTrade(point);
        }

        if (getOperationType() == OperationType.LESS
                && indicatorVal < value) {

            ruleTrade = makeTrade(point);
        }

        return ruleTrade;
    }

    /**
     * Create an indicator element from field values.
     * @param doc document
     * @return indicator XML element
     */
    protected Element getIndicatorElement(final Document doc) {

        final Element indicatorElement = doc.createElement("indicator");

        final Element nameElement = doc.createElement("name");
        nameElement.appendChild(doc.createTextNode(getIndicatorType().name()));

        final Element operationElement = doc.createElement("operation");
        operationElement.appendChild(
                doc.createTextNode(getOperationType().name()));

        final Element valueElement = doc.createElement("value");
        valueElement.appendChild(doc.createTextNode(Float.toString(value)));

        indicatorElement.appendChild(nameElement);
        indicatorElement.appendChild(operationElement);
        indicatorElement.appendChild(valueElement);

        return indicatorElement;
    }
}
