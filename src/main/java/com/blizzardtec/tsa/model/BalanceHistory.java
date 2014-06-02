 /*************************************************************************
 *  2013 BHGAGILE
 *  All Rights Reserved.
 *
 * NOTICE:  All information contained herein is, and remains
 * the property of BHGAGILE.
 */
package com.blizzardtec.tsa.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;






//import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

/**
 * @author Barnaby Golden
 *
 */
@Entity
public final class BalanceHistory {

    /**
     * Unique id.
     */
    @Id
    @GeneratedValue
    private long id;
    /**
     * Initial balance.
     */
    private float initialBalance;
    /**
     * The nominal quantity of shares that could be bought
     * using the initial balance at the first trade.
     * Used to provide the nominal performance to compare against
     * the performance of the trading strategy.
     */
    private int nominalQuantity;
    /**
     * If the purchase of the nominal quantity leaves any
     * cash remaining from the initial balance it is stored here.
     */
    private float nominalRemainder;

    /**
     * List of balance snapshots.
     */
    @OneToMany(cascade = CascadeType.ALL)
    @LazyCollection(LazyCollectionOption.FALSE)
    @OrderBy("date")
    private List<BalanceSnapshot> snapshots;

    /**
     * Constructor must be provided with an initial balance.
     * @param initialBalance the initial balance
     */
    public BalanceHistory(final float initialBalance) {
        this();
        this.initialBalance = initialBalance;
    }

    /**
     * Default constructor.
     */
    public BalanceHistory() {
        snapshots = new ArrayList<BalanceSnapshot>();
    }

    /**
     * @return the initialBalance
     */
    public float getInitialBalance() {
        return initialBalance;
    }
    /**
     * @return the nominalQuantity
     */
    public int getNominalQuantity() {
        return nominalQuantity;
    }
    /**
     * @param nominalQuantity the nominalQuantity to set
     */
    public void setNominalQuantity(final int nominalQuantity) {
        this.nominalQuantity = nominalQuantity;
    }
    /**
     * @return the nominalRemainder
     */
    public float getNominalRemainder() {
        return nominalRemainder;
    }
    /**
     * @param nominalRemainder the nominalRemainder to set
     */
    public void setNominalRemainder(final float nominalRemainder) {
        this.nominalRemainder = nominalRemainder;
    }
    /**
     * @param initialBalance initial balance
     */
    public void setInitialBalance(final float initialBalance) {
        this.initialBalance = initialBalance;
    }
    /**
     * @return the snapshots
     */
    public List<BalanceSnapshot> getSnapshots() {
        return snapshots;
    }
    /**
     * @param snapshots the snapshots to set
     */
    public void setSnapshots(final List<BalanceSnapshot> snapshots) {
        this.snapshots = snapshots;
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
     * Add a balance snapshot.
     * @param balance balance snapshot
     */
    public void add(final BalanceSnapshot balance) {
        this.snapshots.add(balance);
    }

    /**
     * Given a sell price, what is the current nominal value.
     * The nominal value is the share holding that could have
     * been purchased with the initial funds available.
     * @param sellPrice sell price to use
     * @return nominal worth including shares and remainder cash
     */
    public float getNominalWorth(final float sellPrice) {
        return getNominalQuantity() * sellPrice + getNominalRemainder();
    }

    /**
     * Get the nominal worth for the duration of an entire
     * analysis run. Uses the sell price of the last snapshot.
     * Nominal worth is the net value of holding the maximum
     * quantity of the instrument for the duration of the analysis.
     *
     * @return the nominal worth
     */
    public float getNominalWorth() {

        return this.nominalRemainder
                + getLastSnapshot().getSellPrice() * this.nominalQuantity;
    }

    /**
     * Get the date at the beginning of the analysis.
     * @return start date
     */
    public Date getStartDate() {

        return getFirstSnapshot().getDate();
    }

    /**
     * Get the latest date in the analysis.
     * @return end date
     */
    public Date getEndDate() {

        Date endDate = getEarlyDate();

        // make a very early date

        for (final BalanceSnapshot snapshot: snapshots) {
            if (snapshot.getDate().after(endDate)) {
                endDate = snapshot.getDate();
            }
        }

        return endDate;
    }

    /**
     * Get an date early in history to ensure trade dates
     * are later than it.
     * @return date from the year 1800
     */
    private Date getEarlyDate() {

        Date date;

        try {
            date = new SimpleDateFormat(
                    "yyyy-MM-dd", Locale.UK).parse("1800-01-01");
        } catch (ParseException e) {
            date = Calendar.getInstance().getTime();
        }

        return date;
    }

    /**
     * Get the actual result for the run.
     * This will be the final cash balance plus the value
     * of the shares held at the last sell price.
     * @return actual result
     */
    public float getActualResult() {

        final BalanceSnapshot snapshot = getLastSnapshot();

        return snapshot.getBalance()
                    + snapshot.getSellPrice() * snapshot.getShares();
    }

    /**
     * Return the performance of the strategy as the difference from
     * the nominal result.
     * @return performance value
     */
    public float getPerformance() {
        return getActualResult() - getNominalWorth();
    }

    /**
     * Percentage difference between the performance of the strategy and
     * the nominal result.
     * @return percentage performance
     */
    public float getPerformancePercentage() {
        return (getActualResult() - getNominalWorth()) / getNominalWorth();
    }

    /**
     * Get the last balance snapshot.
     * @return last snapshot
     */
    private BalanceSnapshot getLastSnapshot() {

        Date endDate = getEarlyDate();

        BalanceSnapshot lastSnapshot = null;

        for (final BalanceSnapshot snapshot: snapshots) {
            if (snapshot.getDate().after(endDate)) {
                endDate = snapshot.getDate();
                lastSnapshot = snapshot;
            }
        }

        return lastSnapshot;
    }

    /**
     * Get the first balance snapshot.
     * @return first snapshot
     */
    private BalanceSnapshot getFirstSnapshot() {

        Date startDate = Calendar.getInstance().getTime();

        BalanceSnapshot firstSnapshot = null;

        for (final BalanceSnapshot snapshot: snapshots) {
            if (snapshot.getDate().before(startDate)) {
                startDate = snapshot.getDate();
                firstSnapshot = snapshot;
            }
        }

        return firstSnapshot;
    }
}
