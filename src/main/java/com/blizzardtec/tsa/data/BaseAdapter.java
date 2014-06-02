 /*************************************************************************
 *  2013 BHGAGILE
 *  All Rights Reserved.
 *
 * NOTICE:  All information contained herein is, and remains
 * the property of BHGAGILE.
 */
package com.blizzardtec.tsa.data;

/**
 * @author Barnaby Golden
 *
 */
public class BaseAdapter {

    /**
     * Trim leading and trailing spaces off of an
     * array of strings.
     * @param fields the array of strings to work on
     * @return trim array of strings
     */
    public final String[] trimStringArray(final String[] fields) {

        final String[] trimFields = new String[fields.length];

        for (int i = 0; i < fields.length; i++) {
            trimFields[i] = fields[i].trim();
        }

        return trimFields;
    }
}
