 /*************************************************************************
 *  2013 BHGAGILE
 *  All Rights Reserved.
 *
 * NOTICE:  All information contained herein is, and remains
 * the property of BHGAGILE.
 */
package com.blizzardtec.tsa.model;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.blizzardtec.tsa.indicator.Indicator.IndicatorType;
import com.blizzardtec.tsa.model.Trade.TradeType;
import com.blizzardtec.tsa.rules.IndicatorDirectionRule;
import com.blizzardtec.tsa.rules.IndicatorValueRule;
import com.blizzardtec.tsa.rules.Rule;
import com.blizzardtec.tsa.rules.RuleException;
import com.blizzardtec.tsa.rules.Rule.OperationType;
import com.blizzardtec.tsa.rules.Rule.RuleType;

/**
 * @author Barnaby Golden
 *
 */
@Entity
public final class RuleXml {

    /**
     * Maximum size of strategy XML.
     */
    private static final int MAX_XML_SIZE = 16256;
    /**
     * Unique ID.
     */
    @Id
    @GeneratedValue
    private long id;
    /**
     * The unique name for this strategy.
     */
    @Column(nullable = false, unique = true)
    private String name;
    /**
     * The strategy in XML format.
     */
    @Column(length = MAX_XML_SIZE)
    private String xml;
    /**
     * @return the id
     */
    public long getId() {
        return id;
    }
    /**
     * @param id the id to set
     */
    public void setId(final long id) {
        this.id = id;
    }
    /**
     * @return the name
     */
    public String getName() {
        return name;
    }
    /**
     * @param name the name to set
     */
    public void setName(final String name) {
        this.name = name;
    }
    /**
     * @return the xml
     */
    public String getXml() {
        return xml;
    }
    /**
     * @param xml the xml to set
     */
    public void setXml(final String xml) {
        this.xml = xml;
    }

    /**
     * Return a populated <code>Rule</code> object build from the XML.
     * @return rule object
     * @throws RuleException thrown
     */
    public Rule toRule() throws RuleException {

        final List<Rule> list = parseXml(this.getXml());
        return list.get(0);
    }

    /**
     * Parse Rule XML in to a <code>Rule</code> object.
     *
     * @param xml XML to parse
     * @return a list of populated <code>Rule</code> objects
     * @throws RuleException thrown
     */
    public static List<Rule> parseXml(final String xml) throws RuleException {

        final DocumentBuilderFactory dbFactory = DocumentBuilderFactory
                .newInstance();

        DocumentBuilder dBuilder;

        try {
            dBuilder = dbFactory.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            throw new RuleException(e);
        }

        Document doc;

        try {

            doc = dBuilder.parse(
                   new ByteArrayInputStream(xml.getBytes("UTF-8")));

        } catch (UnsupportedEncodingException e) {
            throw new RuleException(e);
        } catch (SAXException e) {
            throw new RuleException(e);
        } catch (IOException e) {
            throw new RuleException(e);
        }

        doc.getDocumentElement().normalize();

        final NodeList idrList =
                doc.getElementsByTagName("indicator-direction-rule");

        List<Rule> allRulesList = null;

        allRulesList =
           parseRules(idrList, RuleType.INDICATOR_DIRECTION);

        final NodeList ivrList =
                doc.getElementsByTagName("indicator-value-rule");

        allRulesList.addAll(
           parseRules(ivrList, RuleType.INDICATOR_VALUE));

        return allRulesList;
    }

    /**
     * Go through a list of indicator direction rules and parse them.
     * @param ruleList list of XML rule nodes to parse
     * @param type the type of the rules to parse
     * @return list of <code>Rule</code> objects
     * @throws RuleException thrown
     */
    private static List<Rule> parseRules(
            final NodeList ruleList, final RuleType type)
                                           throws RuleException {

        final List<Rule> list = new ArrayList<Rule>();

        for (int i = 0; i < ruleList.getLength(); i++) {
            final Node idRule = ruleList.item(i);

            list.add(parseRule(idRule, type));
        }

        return list;
    }

    /**
     * Parse an indicator-direction-rule node in to a rule object.
     * @param node the node to parse
     * @param type type of rule to parse
     * @return rule
     * @throws RuleException thrown
     */
    private static Rule parseRule(
            final Node node, final RuleType type)
                                   throws RuleException {

        // child nodes should consists of an indicator node
        // and a trade node
        final NodeList childNodes = node.getChildNodes();

        if (childNodes.getLength() == 0) {
            throw new RuleException(
                 "No child nodes found in rule node");
        }

        final Node tradeNode = getNode("trade", childNodes);

        final Trade trade = parseTrade(tradeNode);

        final Node indicatorNode = getNode("indicator", childNodes);

        final NodeList indicatorChildren = indicatorNode.getChildNodes();

        final String name = getNode("name", indicatorChildren).getTextContent();

        final OperationType operationType =
                parseOperation(getNode("operation", indicatorChildren));

        Rule rule = null;

        if (type == RuleType.INDICATOR_VALUE) {

            final float value =
                  Float.valueOf(
                         getNode("value", indicatorChildren).getTextContent());

            rule = new IndicatorValueRule(
                    IndicatorType.valueOf(name), operationType, value, trade);
        }

        if (type == RuleType.INDICATOR_DIRECTION) {

            rule = new IndicatorDirectionRule(
                    IndicatorType.valueOf(name), operationType, trade);
        }

        return rule;
    }

    /**
     * Parse a node in to a <code>Trade</code> object.
     * @param node the node to parse
     * @return trade
     */
    private static Trade parseTrade(final Node node) {

        final Trade trade = new Trade();

        final NodeList nodes = node.getChildNodes();

        final String instrument = getNode("instrument", nodes).getTextContent();

        trade.setInstrument(instrument);

        TradeType type = null;

        final String typeStr = getNode("type", nodes).getTextContent();

        type = TradeType.valueOf(typeStr);

        trade.setType(type);

        int quantity = 0;

        // Only buy and sell trade types have a quantity,
        // sell all type has no quantity
        if (type == TradeType.BUY || type == TradeType.SELL) {

            quantity =
               Integer.parseInt(getNode("quantity", nodes).getTextContent());
        }

        trade.setQuantity(quantity);

        return trade;
    }

    /**
     * Get a named node from a list of nodes.
     * @param nodeName name of the node to search for
     * @param list the list of nodes
     * @return null if not found or the node
     */
    private static Node getNode(final String nodeName, final NodeList list) {

        Node foundNode = null;

        for (int i = 0; i < list.getLength(); i++) {

            final Node node = list.item(i);

            if (node.getNodeName().equalsIgnoreCase(nodeName)) {
                foundNode = node;
            }
        }

        return foundNode;
    }

    /**
     * Parse a node in to an <code>Operation</code> object.
     * @param node the node to parse
     * @return operation object
     */
    private static OperationType parseOperation(final Node node) {

        final String text = node.getTextContent();

        return OperationType.valueOf(text);
    }
}
