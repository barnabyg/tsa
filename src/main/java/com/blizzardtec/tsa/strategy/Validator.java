/*************************************************************************
 *  2013 BHGAGILE
 *  All Rights Reserved.
 *
 * NOTICE:  All information contained herein is, and remains
 * the property of BHGAGILE.
 */
package com.blizzardtec.tsa.strategy;

import java.util.ArrayList;
import java.util.List;

import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * @author Barnaby Golden
 */
public final class Validator extends DefaultHandler {

    /**
     * Flag.
     */
    private transient boolean validationError;

    /**
     * Exception.
     */
    private final transient List<SAXParseException> exceptionList;

    /**
     * Constructor.
     */
    public Validator() {
        super();
        exceptionList = new ArrayList<SAXParseException>();
    }

    /**
     * Return true if there are any errors.
     * @return true on error
     */
    public boolean hasErrors() {
        return validationError;
    }

    /**
     * Return the exceptions.
     * @return exceptions list
     */
    public List<SAXParseException> getExceptions() {
        return exceptionList;
    }

    /**
     *
     * {@inheritDoc}
     */
    public void error(final SAXParseException exception)
                                        throws SAXException {
        validationError = true;
        exceptionList.add(exception);
    }

    /**
     *
     * {@inheritDoc}
     */
    public void fatalError(final SAXParseException exception)
                                        throws SAXException {
        validationError = true;
        exceptionList.add(exception);
    }

    /**
     *
     * {@inheritDoc}
     */
    public void warning(final SAXParseException exception)
            throws SAXException {
        // not interested in warnings
    }
}
