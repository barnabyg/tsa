 /*************************************************************************
 *  2013 BHGAGILE
 *  All Rights Reserved.
 *
 * NOTICE:  All information contained herein is, and remains
 * the property of BHGAGILE.
 */
package com.blizzardtec.tsa.rules;

/**
 * @author Barnaby Golden
 *
 */
public final class RuleException extends Exception {

    /**
     * Serial UID.
     */
    private static final long serialVersionUID = 6017648598063300342L;

    /**
     * Constructor from an existing exception.
     * @param exception source
     */
    public RuleException(final Exception exception) {
        super(exception);
    }

    /**
     * Constructor from a string.
     * @param message str
     */
    public RuleException(final String message) {
        super(message);
    }
}
