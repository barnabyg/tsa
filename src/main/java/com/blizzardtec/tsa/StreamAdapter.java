 /*************************************************************************
 *  2013 BHGAGILE
 *  All Rights Reserved.
 *
 * NOTICE:  All information contained herein is, and remains
 * the property of BHGAGILE.
 */
package com.blizzardtec.tsa;

import java.io.InputStream;
import java.util.List;

/**
 * @author Barnaby Golden
 *
 */
public interface StreamAdapter {

    /**
     * Take a <code>MultipartFile</code> and split it
     * in to a list of strings.
     * @param stream input stream
     * @return list of strings
     * @throws DelegateException thrown
     */
    List<String> splitFile(final InputStream stream)
                                    throws DelegateException;

    /**
     * Extract a file in to a single string.
     * @param stream input stream
     * @return string containing the file contents
     * @throws DelegateException thrown
     */
    String extractFile(final InputStream stream)
                                    throws DelegateException;
}
