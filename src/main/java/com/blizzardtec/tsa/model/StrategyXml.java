 /*************************************************************************
 *  2013 BHGAGILE
 *  All Rights Reserved.
 *
 * NOTICE:  All information contained herein is, and remains
 * the property of BHGAGILE.
 */
package com.blizzardtec.tsa.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import com.blizzardtec.tsa.rules.Rule;
import com.blizzardtec.tsa.rules.RuleException;
import com.blizzardtec.tsa.strategy.Strategy;
import com.blizzardtec.tsa.strategy.StrategyException;

/**
 * @author Barnaby Golden
 *
 */
@Entity
public final class StrategyXml {

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
     * @return the strategy XML
     */
    public String getXml() {
        return xml;
    }
    /**
     * @param xml the strategy XML to set
     */
    public void setXml(final String xml) {
        this.xml = xml;
    }
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
     * Convert this strategy XML in to a Strategy object.
     * @return strategy object
     * @throws StrategyException thrown
     */
    public Strategy toStrategy() throws StrategyException {

        List<Rule> list = null;

        try {
            list = RuleXml.parseXml(xml);
        } catch (RuleException e) {
            throw new StrategyException(e);
        }

        final Strategy strategy = new Strategy();

        strategy.setRules(list);

        return strategy;
    }
}
