 /*************************************************************************
 *  2013 BHGAGILE
 *  All Rights Reserved.
 *
 * NOTICE:  All information contained herein is, and remains
 * the property of BHGAGILE.
 */
package com.blizzardtec.tsa.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

/**
 * @author Barnaby Golden
 *
 */
@Entity
public final class Dataset {

    /**
     * Unique ID.
     */
    @Id
    @GeneratedValue
    private long id;
    /**
     * List of data points.
     */
    @OneToMany(cascade = CascadeType.ALL)
    @LazyCollection(LazyCollectionOption.FALSE)
    @OrderBy("date")
    private List<DataPoint> dataPoints;
    /**
     * Dataset name, used to identify datasets.
     */
    @Column(nullable = false, unique = true)
    private String datasetName;
    /**
     * A description of the dataset.
     */
    private String description;

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
     * @return the datasetName
     */
    public String getDatasetName() {
        return datasetName;
    }
    /**
     * @param datasetName the datasetName to set
     */
    public void setDatasetName(final String datasetName) {
        this.datasetName = datasetName;
    }
    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }
    /**
     * @param description the description to set
     */
    public void setDescription(final String description) {
        this.description = description;
    }
    /**
     * @return the dataPoints
     */
    public List<DataPoint> getDataPoints() {
        return dataPoints;
    }
    /**
     * @param dataPoints the dataPoints to set
     */
    public void setDataPoints(final List<DataPoint> dataPoints) {
        this.dataPoints = dataPoints;
    }
}
