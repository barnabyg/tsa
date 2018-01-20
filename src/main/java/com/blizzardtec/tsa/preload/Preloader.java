 /*************************************************************************
 *  2013 BHGAGILE
 *  All Rights Reserved.
 *
 * NOTICE:  All information contained herein is, and remains
 * the property of BHGAGILE.
 */
package com.blizzardtec.tsa.preload;

import java.io.InputStream;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

import com.blizzardtec.tsa.BusinessDelegate;
import com.blizzardtec.tsa.BusinessDelegate.ObjectType;
import com.blizzardtec.tsa.data.DatasetHelper.DataType;
import com.blizzardtec.tsa.DelegateException;
import com.blizzardtec.tsa.StreamAdapter;

/**
 * @author Barnaby Golden
 *
 */
public final class Preloader
        implements ApplicationListener<ContextRefreshedEvent> {

    /**
     * Logger.
     */
    private static final Logger LOG =
            Logger.getLogger(Preloader.class);

    /**
     * File containing a list of datasets to preload.
     */
    private static final String PRELOAD_DATASETS_FILE = "preload-datasets.csv";
    /**
     * File containing a list of strategies to preload.
     */
    private static final String PRELOAD_STRATEGIES_FILE =
                                        "preload-strategies.csv";

    /**
     * Delegate.
     */
    @Autowired
    private transient BusinessDelegate delegate;
    /**
     * Stream adapter.
     */
    @Autowired
    private transient StreamAdapter adapter;

    /**
     * Method that is run on startup to preload
     * a number of built-in datasets.
     * @param type object type to preload data for
     */
    private void preload(final ObjectType type) {

        InputStream stream = null;

        String fileName = null;

        if (type == ObjectType.DATASET) {
            fileName = PRELOAD_DATASETS_FILE;
        } else if (type == ObjectType.STRATEGY) {
            fileName = PRELOAD_STRATEGIES_FILE;
        }

        // load a list of all the datasets we want to preload
        stream = Thread.currentThread().getContextClassLoader()
                .getResourceAsStream(fileName);

        final List<String> listNames = delegate.listNames(type);

        try {

            final List<String> strings = adapter.splitFile(stream);

            for (final String line : strings) {

                // Each line of a dataset consists of:
                //   0        1        2
                // <name>,<filename>,<type>
                //
                // Each line of a strategy consists of:
                //    0       1
                // <name>,<filename>

                final String[] fields = line.split(",");

                final String[] trimFields = new String[fields.length];

                for (int i = 0; i < fields.length; i++) {
                    trimFields[i] = fields[i].trim();
                }

                // don't bother loading datasets that are already on the system
                if (listNames.contains(trimFields[0])) {
                    continue;
                }

                stream = Thread.currentThread().getContextClassLoader()
                        .getResourceAsStream(trimFields[1]);

                if (stream == null) {
                    LOG.error("Unable to find " + trimFields[1]);
                } else {
                    if (type == ObjectType.DATASET) {

                        delegate.saveDataSet(
                                stream,
                                trimFields[0],
                                DataType.valueOf(trimFields[2]));

                    } else if (type == ObjectType.STRATEGY) {

                        delegate.saveStrategy(
                                stream,
                                trimFields[0]);
                    }
                }
            }

        } catch (DelegateException e) {
            LOG.error(e.getMessage());
        }
    }

    /**
     * {@inheritDoc}
     */
    public void onApplicationEvent(final ContextRefreshedEvent event) {

        preload(ObjectType.DATASET);
        preload(ObjectType.STRATEGY);
    }
}
