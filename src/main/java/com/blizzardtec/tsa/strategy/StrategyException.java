 /*************************************************************************
 *  2013 BHGAGILE
 *  All Rights Reserved.
 *
 * NOTICE:  All information contained herein is, and remains
 * the property of BHGAGILE.
 */
package com.blizzardtec.tsa.strategy;

/**
 * @author Barnaby Golden
 *
 */
public final class StrategyException extends Exception {

    /**
     * Serial UID.
     */
    private static final long serialVersionUID = -1036434726795284689L;

    /**
     * Constructor from an existing exception.
     * @param exception source
     */
    public StrategyException(final Exception exception) {
        super(exception);
    }

    /**
     * Constructor from a string.
     * @param message str
     */
    public StrategyException(final String message) {
        super(message);
    }
}
