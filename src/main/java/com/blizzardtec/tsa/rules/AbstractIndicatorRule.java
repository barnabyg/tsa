 /*************************************************************************
 *  2013 BHGAGILE
 *  All Rights Reserved.
 *
 * NOTICE:  All information contained herein is, and remains
 * the property of BHGAGILE.
 */
package com.blizzardtec.tsa.rules;

import java.io.StringWriter;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.blizzardtec.tsa.data.FullDataPoint;
import com.blizzardtec.tsa.indicator.Indicator.IndicatorType;
import com.blizzardtec.tsa.model.RuleXml;
import com.blizzardtec.tsa.model.Trade;
import com.blizzardtec.tsa.model.Trade.TradeType;

/**
 * @author Barnaby Golden
 *
 */
public abstract class AbstractIndicatorRule implements Rule {

    /**
     * Indicator type.
     */
    private final transient IndicatorType indicatorType;
    /**
     * Boolean operation.
     */
    private final transient OperationType operationType;
    /**
     * Trade to perform if operation is true.
     */
    private final transient Trade trade;
    /**
     * Name of the rule.
     */
    private String name = "DEFAULT";

    /**
     * {@inheritDoc}
     */
    public abstract Trade runRule(final FullDataPoint point);

    /**
     * Constructor.
     * @param indicatorType indicator type
     * @param operationType operation type
     * @param trade trade to perform
     */
    protected AbstractIndicatorRule(
            final IndicatorType indicatorType,
            final OperationType operationType,
            final Trade trade) {

        this.indicatorType = indicatorType;
        this.operationType = operationType;
        this.trade = trade;
    }

    /**
     * @return the name
     */
    public final String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public final void setName(final String name) {
        this.name = name;
    }

    /**
     * @return the indicatorType
     */
    public final IndicatorType getIndicatorType() {
        return indicatorType;
    }

    /**
     * @return the operation
     */
    public final OperationType getOperationType() {
        return operationType;
    }

    /**
     * @return the trade
     */
    public final Trade getTrade() {
        return trade;
    }


    /**
     * Make a trade up from a data point.
     * @param point data point
     * @return trade or null if none required
     */
    protected final Trade makeTrade(final FullDataPoint point) {

        Trade ruleTrade = null;

        trade.setDate(point.getDate());

        if (trade.getType() == TradeType.BUY) {

            trade.setPrice(point.getBuy());
            ruleTrade = new Trade(getTrade());
        }

        if (trade.getType() == TradeType.BUYALL) {

            trade.setPrice(point.getBuy());
            ruleTrade = new Trade(getTrade());
        }

        if (trade.getType() == TradeType.SELL) {

            trade.setPrice(point.getSell());
            ruleTrade = new Trade(getTrade());

        }

        if (trade.getType() == TradeType.SELLALL) {

            trade.setPrice(point.getSell());
            ruleTrade = new Trade(getTrade());
        }

        return ruleTrade;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final RuleXml toXml() throws RuleException {

        final DocumentBuilderFactory docFactory =
                    DocumentBuilderFactory.newInstance();

        DocumentBuilder docBuilder = null;

        try {
            docBuilder = docFactory.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            throw new RuleException(e);
        }

        final Document doc = docBuilder.newDocument();

        Element rootElement = null;

        switch (getIndicatorType()) {
        case FIVE_DAY_AVERAGE:
        case THIRTY_DAY_AVERAGE:
        case FIVE_DAY_VOLATILITY:
            rootElement = doc.createElement("indicator-value-rule");
            break;
        case FIVE_DAY_AVERAGE_DIRECTION:
        case THIRTY_DAY_AVERAGE_DIRECTION:
        case FIVE_DAY_VOLATILITY_DIRECTION:
            rootElement = doc.createElement("indicator-direction-rule");
            break;
        default:
            throw new RuleException("Invalid indicator type");
        }

        doc.appendChild(rootElement);

        rootElement.appendChild(getIndicatorElement(doc));
        rootElement.appendChild(getTradeElement(doc));

        final TransformerFactory tf = TransformerFactory.newInstance();

        Transformer transformer = null;

        try {
            transformer = tf.newTransformer();
        } catch (TransformerConfigurationException e) {
            throw new RuleException(e);
        }

        final DOMSource domSource = new DOMSource(doc);
        final StringWriter writer = new StringWriter();
        final StreamResult result = new StreamResult(writer);

        try {

            // strip the XML declaration or else it will appear
            // once for every rule added
            transformer.setOutputProperty("omit-xml-declaration", "yes");

            transformer.transform(domSource, result);

        } catch (TransformerException e) {
            throw new RuleException(e);
        }

        final RuleXml ruleXml = new RuleXml();

        ruleXml.setXml(writer.toString());

        ruleXml.setName(getName());

        return ruleXml;
    }

    /**
     * Create an indicator element from field values.
     * @param doc document
     * @return indicator XML element
     */
    protected abstract Element getIndicatorElement(final Document doc);

    /**
     * Create a trade element from field values.
     * @param doc document
     * @return trade XML element
     */
    private Element getTradeElement(final Document doc) {

        return getTrade().toXml(doc);
    }
}
