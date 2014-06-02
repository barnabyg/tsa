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

/**
 * @author Barnaby Golden
 *
 */
@Entity
public final class BalanceSnapshot {

    /**
     * Unique ID.
     */
    @Id
    @GeneratedValue
    private Long id;
    /**
     * Date of the balance.
     */
    private Date date;
    /**
     * Cash balance remaining.
     */
    private float balance;
    /**
     * Count of the current share ownership.
     */
    private int shares;
    /**
     * The price the shares could be sold at at this time point.
     */
    private float sellPrice;

    /**
     * The date of this snapshot.
     *
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
     * Cash held.
     *
     * @return the balance
     */
    public float getBalance() {
        return balance;
    }
    /**
     * @param balance the balance to set
     */
    public void setBalance(final float balance) {
        this.balance = balance;
    }
    /**
     * @return the id
     */
    public Long getId() {
        return id;
    }
    /**
     * @param id the id to set
     */
    public void setId(final Long id) {
        this.id = id;
    }
    /**
     * Number of shares held.
     *
     * @return the shares
     */
    public int getShares() {
        return shares;
    }
    /**
     * @param shares the shares to set
     */
    public void setShares(final int shares) {
        this.shares = shares;
    }
    /**
     * The sell price of the shares at this snapshot time.
     *
     * @return the sellPrice
     */
    public float getSellPrice() {
        return sellPrice;
    }
    /**
     * @param sellPrice the sellPrice to set
     */
    public void setSellPrice(final float sellPrice) {
        this.sellPrice = sellPrice;
    }

    /**
     * Get the current worth given the number of shares held
     * and the cash held.
     * @return current worth
     */
    public float getCurrentWorth() {
        return getBalance() + getShares() * getSellPrice();
    }
}
