 /*************************************************************************
 *  2013 BHGAGILE
 *  All Rights Reserved.
 *
 * NOTICE:  All information contained herein is, and remains
 * the property of BHGAGILE.
 */
package com.blizzardtec.tsa.form;

import java.util.List;

import javax.validation.constraints.Size;

/**
 * @author Barnaby Golden
 *
 */
public final class CreateStrategyForm {

    /**
     * Maximum length of a name in characters.
     */
    private static final int MAX_NAME_LENGTH = 64;

    /**
     * Name for the strategy.
     */
    @Size(min = 2, max = MAX_NAME_LENGTH,
        message = "Strategy name must be between 2 and 64 characters in length")
    private String name;
    /**
     * List of rule names in the strategy.
     */
    private List<String> rules;
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
     * @return the rules
     */
    public List<String> getRules() {
        return rules;
    }
    /**
     * @param rules the rules to set
     */
    public void setRules(final List<String> rules) {
        this.rules = rules;
    }
}
