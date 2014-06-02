 /*************************************************************************
 *  2013 BHGAGILE
 *  All Rights Reserved.
 *
 * NOTICE:  All information contained herein is, and remains
 * the property of BHGAGILE.
 */
package com.blizzardtec.tsa.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * @author Barnaby Golden
 *
 */
@Entity
public final class Trade {

    /**
     * Unique ID.
     */
    @Id
    @GeneratedValue
    private long id;
    /**
     * Instrument name.
     */
    private String instrument;
    /**
     * Quantity of the trade.
     */
    private int quantity;
    /**
     * The price the trade was made at.
     */
    private float price;
    /**
     * The type of the trade: buy or sell.
     */
    private TradeType type;
    /**
     * Date-time that the trade occurred.
     */
    private Date date;

    /**
     * Trade types.
     * @author Barnaby Golden
     *
     */
    public enum TradeType { BUY, SELL, SELLALL, BUYALL }

    /**
     * Copy constructor.
     * @param trade trade to copy
     */
    public Trade(final Trade trade) {
        this();
        this.id = trade.getId();
        this.instrument = trade.getInstrument();
        this.quantity = trade.getQuantity();
        this.price = trade.getPrice();
        this.type = trade.getType();
        this.date = trade.getDate();
    }

    /**
     * Default constructor.
     */
    public Trade() {
        // default constructor for ORM
    }

    /**
     * @return the instrument
     */
    public String getInstrument() {
        return instrument;
    }
    /**
     * @param instrument the instrument to set
     */
    public void setInstrument(final String instrument) {
        this.instrument = instrument;
    }
    /**
     * @return the quantity
     */
    public int getQuantity() {
        return quantity;
    }
    /**
     * @param quantity the quantity to set
     */
    public void setQuantity(final int quantity) {
        this.quantity = quantity;
    }
    /**
     * @return the price
     */
    public float getPrice() {
        return price;
    }
    /**
     * @param price the price to set
     */
    public void setPrice(final float price) {
        this.price = price;
    }
    /**
     * @return the type
     */
    public TradeType getType() {
        return type;
    }
    /**
     * @param type the type to set
     */
    public void setType(final TradeType type) {
        this.type = type;
    }
    /**
     * @return the date
     */
    public Date getDate() {
        return date;
    }
    /**
     * @param date the date to set
     */
    public void setDate(final Date date) {
        this.date = date;
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
     * Return the value of the trade.
     * @return trade value
     */
    public float tradeValue() {
        return this.quantity * this.getPrice();
    }

    /**
     * Turn this object in to an XML element.
     * @param doc document
     * @return Trade XML element
     */
    public Element toXml(final Document doc) {

        final Element tradeElement = doc.createElement("trade");

        final Element instrumentElement = doc.createElement("instrument");
        instrumentElement.appendChild(doc.createTextNode(getInstrument()));

        final Element typeElement = doc.createElement("type");
        typeElement.appendChild(
                doc.createTextNode(getType().toString()));

        final Element quantityElement = doc.createElement("quantity");
        quantityElement.appendChild(
                doc.createTextNode(Integer.toString(getQuantity())));

        tradeElement.appendChild(instrumentElement);
        tradeElement.appendChild(typeElement);
        tradeElement.appendChild(quantityElement);

        return tradeElement;
    }
}
