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
public final class IndicatorDirectionRule
                extends AbstractIndicatorRule {

    /**
     * Constructor requires the indicator type.
     * @param indicatorType the type of indicator
     * @param operationType the type of boolean operation to perform
     * @param trade the trade to perform if the operation is true
     */
    public IndicatorDirectionRule(
            final IndicatorType indicatorType,
            final OperationType operationType,
            final Trade trade) {

        super(indicatorType, operationType, trade);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Trade runRule(final FullDataPoint point) {

        Trade ruleTrade = null;

        final String indicatorVal = point.getIndicator(getIndicatorType());

        if (getOperationType() == OperationType.valueOf(indicatorVal)) {

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

        indicatorElement.appendChild(nameElement);
        indicatorElement.appendChild(operationElement);

        return indicatorElement;
    }
}
