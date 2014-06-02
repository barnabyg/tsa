 /*************************************************************************
 *  2013 BHGAGILE
 *  All Rights Reserved.
 *
 * NOTICE:  All information contained herein is, and remains
 * the property of BHGAGILE.
 */
package com.blizzardtec.tsa.controller;

import java.text.NumberFormat;

import org.springframework.beans.propertyeditors.CustomNumberEditor;

/**
 * @author Barnaby Golden
 *
 */
public final class MyCustomNumberEditor extends CustomNumberEditor {

    /**
     * Constructor.
     * @param numberClass param
     * @param numberFormat param
     * @param allowEmpty param
     */
    public MyCustomNumberEditor(
            final Class<? extends Number> numberClass,
            final NumberFormat numberFormat,
            final boolean allowEmpty) {

        super(numberClass, numberFormat, allowEmpty);
    }

    /**
     * Constructor.
     * @param numberClass param
     * @param allowEmpty param
     */
    public MyCustomNumberEditor(
            final Class<? extends Number> numberClass,
            final boolean allowEmpty) {

        super(numberClass, allowEmpty);
    }

    @Override
    public void setAsText(final String text) {

        String numStr = null;

        if (text.isEmpty()) {
            numStr = "0";
        } else {
            numStr = text;
        }

        super.setAsText(numStr);
    }
}
