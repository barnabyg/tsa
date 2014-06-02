 /*************************************************************************
 *  2013 BHGAGILE
 *  All Rights Reserved.
 *
 * NOTICE:  All information contained herein is, and remains
 * the property of BHGAGILE.
 */
package com.blizzardtec.tsa.indicator;

/**
 * @author Barnaby Golden
 *
 */
public final class IndicatorException extends Exception {

    /**
     * Serial UID.
     */
    private static final long serialVersionUID = 8605582424672428175L;

    /**
     * Exception constructor.
     * @param exception ex
     */
    public IndicatorException(final Exception exception) {
        super(exception);
    }

    /**
     * Message constructor.
     * @param message msg
     */
    public IndicatorException(final String message) {
        super(message);
    }
}
