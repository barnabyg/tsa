/**
 *
 */
package com.blizzardtec.tsa;

/**
 * @author Barnaby Golden
 *
 */
public final class DelegateException extends Exception {

    /**
     * serial uid.
     */
    private static final long serialVersionUID = -2482086246394921672L;

    /**
     * Exception constructor.
     * @param exception ex
     */
    public DelegateException(final Exception exception) {
        super(exception);
    }

    /**
     * Message constructor.
     * @param message msg
     */
    public DelegateException(final String message) {
        super(message);
    }
}
