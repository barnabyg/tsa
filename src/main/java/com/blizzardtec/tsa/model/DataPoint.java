 /*************************************************************************
 *  2013 BHGAGILE
 *  All Rights Reserved.
 *
 * NOTICE:  All information contained herein is, and remains
 * the property of BHGAGILE.
 */
package com.blizzardtec.tsa.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;


/**
 * @author Barnaby Golden
 *
 */
@Entity
public class DataPoint {

    /**
     * Unique ID.
     */
    @Id
    @GeneratedValue
    private long id;
    /**
     * Date-time of the data point.
     */
    @Column(nullable = false)
    private Date date;
    /**
     * Instrument name.
     */
    @Column(nullable = false)
    private String instrument;
    /**
     * Buy price.
     */
    private float buy;
    /**
     * Sell price.
     */
    private float sell;

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
     * @return the buy
     */
    public float getBuy() {
        return buy;
    }
    /**
     * @param buy the buy to set
     */
    public void setBuy(final float buy) {
        this.buy = buy;
    }
    /**
     * @return the sell
     */
    public float getSell() {
        return sell;
    }
    /**
     * @param sell the sell to set
     */
    public void setSell(final float sell) {
        this.sell = sell;
    }
    /**
     * Return the midprice between buy and sell.
     * @return midprice
     */
    public float midprice() {
        float retVal = 0;

        if (this.buy > 0f && this.sell > 0f) {
            retVal = (this.buy + this.sell) / 2f;
        }

        return retVal;
    }
}
