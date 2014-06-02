 /*************************************************************************
 *  2013 BHGAGILE
 *  All Rights Reserved.
 *
 * NOTICE:  All information contained herein is, and remains
 * the property of BHGAGILE.
 */
package com.blizzardtec.tsa.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

/**
 * @author Barnaby Golden
 *
 */
@Entity
public final class Resultset {

    /**
     * Unique ID for the result set.
     */
    @Id
    @GeneratedValue
    private long id;

    /**
     * Result set name.
     * Typically made up of the strategy name,
     * dataset name and the date-time for the run.
     */
    @Column(nullable = false, unique = true)
    private String name;

    /**
     * List of all the trades that took place
     * during the test.
     */
    @OneToMany(cascade = CascadeType.ALL)
    @LazyCollection(LazyCollectionOption.FALSE)
    @OrderBy("date")
    private List<Trade> trades;

    /**
     * History of the account balance during the test.
     */
    @OneToOne(cascade = CascadeType.ALL)
    private BalanceHistory balanceHistory;

    /**
     * Constructor.
     * @param name unique name
     */
    public Resultset(final String name) {
        this();
        this.name = name;
    }

    /**
     * Default constructor.
     */
    public Resultset() {
        this.trades = new ArrayList<Trade>();
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
     * @return the trades
     */
    public List<Trade> getTrades() {
        return trades;
    }

    /**
     * @param trades the trades to set
     */
    public void setTrades(final List<Trade> trades) {
        this.trades = trades;
    }

    /**
     * @return the balanceHistory
     */
    public BalanceHistory getBalanceHistory() {
        return balanceHistory;
    }

    /**
     * @param balanceHistory the balanceHistory to set
     */
    public void setBalanceHistory(final BalanceHistory balanceHistory) {
        this.balanceHistory = balanceHistory;
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
     * Add trade.
     * @param trade trade to add
     */
    public void addTrade(final Trade trade) {
        this.trades.add(trade);
    }
}
