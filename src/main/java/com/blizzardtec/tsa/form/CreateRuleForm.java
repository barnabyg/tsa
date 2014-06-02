 /*************************************************************************
 *  2013 BHGAGILE
 *  All Rights Reserved.
 *
 * NOTICE:  All information contained herein is, and remains
 * the property of BHGAGILE.
 */
package com.blizzardtec.tsa.form;

import javax.validation.constraints.Size;

/**
 * @author Barnaby Golden
 *
 */
public final class CreateRuleForm {

    /**
     * Maximum length of a name in characters.
     */
    private static final int MAX_NAME_LENGTH = 64;
    /**
     * Name for the rule.
     */
    @Size(min = 2, max = MAX_NAME_LENGTH,
        message = "Rule name must be between 2 and 64 characters in length")
    private String name;
    /**
     * Type of rule.
     */
    private String ruleType;
    /**
     * The value indicator it will operate on.
     */
    private String valueIndicatorName;
    /**
     * The direction indicator it will operate on.
     */
    private String directionIndicatorName;
    /**
     * The value operation that will be assessed.
     */
    private String valueOperation;
    /**
     * The direction operation that will be assessed.
     */
    private String directionOperation;
    /**
     * The value that will be checked against if required.
     */
    private Float value;
    /**
     * The instrument to trade on.
     */
    private String instrument = "ANY";
    /**
     * The type of trade to perform if the rule matches.
     */
    private String tradeType;
    /**
     * The quantity of shares to transact if the rule matches.
     */
    private Integer quantity;
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
     * @return the value
     */
    public Float getValue() {
        return value;
    }
    /**
     * @param value the value to set
     */
    public void setValue(final Float value) {
        this.value = value;
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
     * @return the tradeType
     */
    public String getTradeType() {
        return tradeType;
    }
    /**
     * @param tradeType the tradeType to set
     */
    public void setTradeType(final String tradeType) {
        this.tradeType = tradeType;
    }
    /**
     * @return the quantity
     */
    public Integer getQuantity() {
        return quantity;
    }
    /**
     * @param quantity the quantity to set
     */
    public void setQuantity(final Integer quantity) {
        this.quantity = quantity;
    }
    /**
     * @return the ruleType
     */
    public String getRuleType() {
        return ruleType;
    }
    /**
     * @param ruleType the ruleType to set
     */
    public void setRuleType(final String ruleType) {
        this.ruleType = ruleType;
    }
    /**
     * @return the valueIndicatorName
     */
    public String getValueIndicatorName() {
        return valueIndicatorName;
    }
    /**
     * @param valueIndicatorName the valueIndicatorName to set
     */
    public void setValueIndicatorName(final String valueIndicatorName) {
        this.valueIndicatorName = valueIndicatorName;
    }
    /**
     * @return the directionIndicatorName
     */
    public String getDirectionIndicatorName() {
        return directionIndicatorName;
    }
    /**
     * @param directionIndicatorName the directionIndicatorName to set
     */
    public void setDirectionIndicatorName(final String directionIndicatorName) {
        this.directionIndicatorName = directionIndicatorName;
    }
    /**
     * @return the valueOperation
     */
    public String getValueOperation() {
        return valueOperation;
    }
    /**
     * @param valueOperation the valueOperation to set
     */
    public void setValueOperation(final String valueOperation) {
        this.valueOperation = valueOperation;
    }
    /**
     * @return the directionOperation
     */
    public String getDirectionOperation() {
        return directionOperation;
    }
    /**
     * @param directionOperation the directionOperation to set
     */
    public void setDirectionOperation(final String directionOperation) {
        this.directionOperation = directionOperation;
    }
}
