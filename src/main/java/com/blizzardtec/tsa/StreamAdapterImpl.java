 /*************************************************************************
 *  2013 BHGAGILE
 *  All Rights Reserved.
 *
 * NOTICE:  All information contained herein is, and remains
 * the property of BHGAGILE.
 */
package com.blizzardtec.tsa;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Barnaby Golden
 *
 */
public final class StreamAdapterImpl implements StreamAdapter {

    /**
     * {@inheritDoc}
     */
    public List<String> splitFile(final InputStream stream)
                                            throws DelegateException {

        final ArrayList<String> list = new ArrayList<String>();

        BufferedReader reader = null;

        try {
            reader = new BufferedReader(
                    new InputStreamReader(stream));

            String line = null;

            while ((line = reader.readLine()) != null) {
                list.add(line);
            }

        } catch (IOException ioe) {
            throw new DelegateException(ioe);
        }

        return list;
    }

    /**
     * {@inheritDoc}
     */
    public String extractFile(final InputStream stream)
                                            throws DelegateException {

        final StringBuffer buffer = new StringBuffer();

        BufferedReader reader = null;

        try {
            reader = new BufferedReader(
                    new InputStreamReader(stream));

            String line = null;

            while ((line = reader.readLine()) != null) {
                buffer.append(line);
            }

        } catch (IOException ioe) {
            throw new DelegateException(ioe);
        }

        return buffer.toString();
    }
}
